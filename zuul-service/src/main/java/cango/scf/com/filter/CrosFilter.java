package cango.scf.com.filter;


import cango.scf.com.api.service.MainService;
import cango.scf.com.constants.ResultCode;
import cango.scf.com.qo.GetTokenQO;
import cango.scf.com.vo.BaseVO;
import cango.scf.com.vo.LoginVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

@Component
public class CrosFilter extends ZuulFilter {


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
        return -1; //// 优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
//        //是否执行该过滤器，此处为true，说明需要过滤
//        RequestContext ctx = RequestContext.getCurrentContext();
//        HttpServletRequest request = ctx.getRequest();
//        String url = request.getServletPath();
//        if(request.getMethod().equals(RequestMethod.OPTIONS.name())){
//            return true;
//        }
        return false;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response =  ctx.getResponse();
        response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Content-type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        ctx.setResponseStatusCode(200);
        ctx.setSendZuulResponse(false);
        return null;
    }

}
