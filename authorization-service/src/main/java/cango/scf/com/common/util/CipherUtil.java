package cango.scf.com.common.util;

import org.springframework.util.DigestUtils;

import java.util.UUID;


/**
 * 密文共通类
 * @author cango
 */
public class CipherUtil {



    /**
     *  MD5加密字符串
     * @param target
     * @return
     */
    public static String md5DigestAsHex(String target) {
        if(StringUtil.isEmpty(target)){
            return StringUtil.EMPTY;
        }
        return DigestUtils.md5DigestAsHex(target.getBytes());
    }

    /**
     * 生成ApiId
     * @param initial
     * @return
     */
    public static String creatApiId(String initial){
        return initial.concat(UUID.randomUUID().toString().replace("-", ""));
    }

}

