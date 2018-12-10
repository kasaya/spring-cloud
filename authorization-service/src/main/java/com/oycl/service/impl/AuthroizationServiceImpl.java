package com.oycl.service.impl;

import com.oycl.common.base.BaseInput;

import com.oycl.common.definitions.Constants;
import com.oycl.common.exception.CangoAplException;
import com.oycl.common.util.*;
import com.oycl.input.GetTokenInput;
import com.oycl.input.RegisterInput;
import com.oycl.input.VerifyTokenInput;

import com.oycl.orm.mapper.TAuthBaseDao;
import com.oycl.orm.model.TAuthBase;

import com.oycl.output.GetTokenOutput;
import com.oycl.output.RegisterOutput;
import com.oycl.output.VerifyTokenOutput;
import com.oycl.service.AuthroizationService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 注册逻辑实现类
 *
 * @author cango
 */
@Service
public class AuthroizationServiceImpl implements AuthroizationService {

    private Logger logger = LoggerFactory.getLogger(AuthroizationServiceImpl.class);

    //token 有效时间默认2小时
    @Value("${token.activeTime:7200}")
    private int tokenActiveTime;

    @Autowired
    TAuthBaseDao baseDao;

    private AESUtil aesUtil = AESUtil.getInstance();

    @Override
    public RegisterOutput register(RegisterInput input) throws Exception {
        //校验数据
        if (!validateSign(input)) {
            throw new CangoAplException("数据有效性验证失败！");
        }
        RegisterOutput result = new RegisterOutput();
        TAuthBase param = new TAuthBase();
        param.setIpAddress(input.getIpAddress());
        TAuthBase target = baseDao.selectOne(param);
        if (target != null) {
            result.setApiId(target.getApiId());
            result.setApiSecret(aesUtil.encryptCBC(target.getSecret()));
        } else {
            //生成APIID 与 ApiSecret
            String apiId = CipherUtil.creatApiId("kasaya");

            param.setApiId(apiId);
            param.setSecret(StringUtil.getUUID());
            param.setPrincipal(input.getPrincipal());
            //param.setCreateDateTime(D);
            baseDao.insert(param);
            result.setApiId(apiId);
            String secret = aesUtil.encryptCBC(param.getSecret());
            result.setApiSecret(secret);
        }
        return result;
    }

    @Override
    public GetTokenOutput getToken(GetTokenInput input) throws Exception {
        //校验数据
        if (!validateSign(input)) {
            throw new CangoAplException("数据有效性验证失败！");
        }
        //检验APIID与secret是否有效
        TAuthBase param = new TAuthBase();
        param.setApiId(input.getApiId());
        param.setSecret(aesUtil.decryptCBC(input.getSecret()));
        //解密失败
        if(StringUtil.isEmpty(param.getSecret())){
            throw new CangoAplException("apiId与secret无效!");
        }
        TAuthBase target = baseDao.selectOne(param);
        if(target == null){
            throw new CangoAplException("apiId与secret无效!");
        }

        //生成TOKEN

        AuthInfo authInfo = new AuthInfo();
        authInfo.setToken(StringUtil.getUUID());
        authInfo.setInfo(target);

        long after2hours = DateUtil.addTimeStampbyHours(System.currentTimeMillis(),2);
        authInfo.setExpireTime(after2hours);
        //将生成的token放在redis中 2h内有效
        RedisUtil.set(Constants.REDIS_KEY_HEAD_TOKEN+":"+authInfo.getToken(), JSONObject.toJSONString(authInfo), tokenActiveTime);
        GetTokenOutput result = new GetTokenOutput();
        result.setToken(aesUtil.encryptCBC(authInfo.getToken()));
        return result;
    }

    @Override
    public VerifyTokenOutput verifyToken(VerifyTokenInput input) throws Exception {
        //校验数据
        if (!validateSign(input)) {
            throw new CangoAplException("数据有效性验证失败！");
        }
        VerifyTokenOutput result = new VerifyTokenOutput();
        //验证token
        String token = aesUtil.decryptCBC(input.getToken());
        if(StringUtil.isEmpty(token)){
            throw new CangoAplException("token有效性验证失败！");
        }
        //验证token是否过期
        String authInfo = RedisUtil.get(Constants.REDIS_KEY_HEAD_TOKEN+":"+token);
        if(StringUtil.isNotEmpty(authInfo)){
            result.setVerifyResult(true);
            AuthInfo info = JSONObject.parseObject(authInfo,AuthInfo.class);
            result.setIpAddress(info.getInfo().getIpAddress());
            result.setExpireTime(info.getExpireTime());
        }else{
            result.setVerifyResult(false);
            result.setExpireTime(0);
        }
        return result;
    }

    /**
     * 验证数据是否被篡改
     * 参数+固定文言<b>“&cangoScfAuthroization”<b/> 进行MD5加密，与传来的md5 sign进行比对
     *
     * @param input
     * @return 相同 true 不同 false
     */
    private boolean validateSign(BaseInput input) {
        Class a = input.getClass();
        Field[] fields = a.getDeclaredFields();
        StringBuilder paramBuilder = new StringBuilder();
        if (fields != null) {
            for (Field field : fields) {
                try {
                    Method method = a.getDeclaredMethod("get" + StringUtil.upperCase(field.getName()));
                    String value = (String) method.invoke(input);
                    if (!StringUtil.isEmpty(value)
                            && (!"sign".equals(field.getName()))){
                        //sample: cityFrom=上海&
                        paramBuilder.append(field.getName()).append("=").append(value).append("&");
                    }
                } catch (NoSuchMethodException e) {
                    logger.error("签名生成异常", e);
                } catch (IllegalAccessException e) {
                    logger.error("签名生成异常", e);
                } catch (InvocationTargetException e) {
                    logger.error("签名生成异常", e);
                }
            }
            paramBuilder.append("kasayaSlayers");
        }
        return input.getSign().equals(CipherUtil.md5DigestAsHex(paramBuilder.toString()));
    }

    private static class AuthInfo{
        private String token ;

        private TAuthBase info;

        private long expireTime;

        public long getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(long expireTime) {
            this.expireTime = expireTime;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public TAuthBase getInfo() {
            return info;
        }

        public void setInfo(TAuthBase info) {
            this.info = info;
        }
    }
}
