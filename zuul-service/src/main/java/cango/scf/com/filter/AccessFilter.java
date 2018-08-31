package cango.scf.com.filter;


import cango.scf.com.entity.UserInfoDetail;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import com.oycl.base.BaseOutput;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

@Component
public class AccessFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(CrosPostFilter.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public String filterType() {
        /*
        pre：可以在请求被路由之前调用
        route：在路由请求时候被调用
        post：在route和error过滤器之后被调用
        error：处理请求时发生错误时被调用
        * */
        return FilterConstants.PRE_TYPE; // 前置过滤器
    }

    @Override
    public int filterOrder() {
        return 0; //// 优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        //是否执行该过滤器，此处为true，说明需要过滤
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String url = request.getServletPath();

        if(request.getMethod().equals(RequestMethod.OPTIONS.name())){
            return false;
        }
        if (url.indexOf("/login") >= 0 || url.indexOf("/infc") >= 0) {
            setBaseAuthorization(ctx);
            return false;
        }

        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication && null != authentication.getPrincipal()) {

            setBaseAuthorization(ctx);
            //取得认证后的用户信息
            UserInfoDetail userInfo = (UserInfoDetail) authentication.getPrincipal();

            try {
                RequestContext context = RequestContext.getCurrentContext();


                InputStream in = (InputStream) context.get("requestEntity");
                if (in == null) {
                    in = context.getRequest().getInputStream();
                }
                String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));


                JSONObject object = JSON.parseObject(body);

                if(object == null) {object = new JSONObject();}

                object.put("userInfo", userInfo);
                body = object.toJSONString();
                byte[] bytes = body.getBytes("UTF-8");
                context.setRequest(new HttpServletRequestWrapper(RequestContext.getCurrentContext().getRequest()) {
                    @Override
                    public ServletInputStream getInputStream() throws IOException {
                        return new ServletInputStreamWrapper(bytes);
                    }

                    @Override
                    public int getContentLength() {
                        return bytes.length;
                    }

                    @Override
                    public long getContentLengthLong() {
                        return bytes.length;
                    }


                });
            } catch (IOException e) {
                rethrowRuntimeException(e);
            }
        }

        return null;
    }

    private String getBase64Credentials(String username, String password) {
        String plainCreds = username + ":" + password;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        return new String(base64CredsBytes);
    }

    /**
     * 设定httpBasic  检测头
     * @param ctx
     */
    private void setBaseAuthorization(RequestContext ctx){
        List<ServiceInstance> instanceInfoList = discoveryClient.getInstances("eureka-service");
        if(!CollectionUtils.isEmpty(instanceInfoList)){
            Map<String,String> metadata = instanceInfoList.get(0).getMetadata();
            if(metadata != null){
                ctx.addZuulRequestHeader("Authorization", "Basic " + getBase64Credentials(metadata.get("username"), metadata.get("password")));
            }
        }
    }

}
