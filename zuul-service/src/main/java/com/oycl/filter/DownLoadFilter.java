package com.oycl.filter;


import com.oycl.constants.ResultCode;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.oycl.base.BaseOutput;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 防盗链路由器
 * @author cango
 */
@Component
public class DownLoadFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(DownLoadFilter.class);

    @Override
    public String filterType() {
        /*
        pre：可以在请求被路由之前调用
        route：在路由请求时候被调用
        post：在route和error过滤器之后被调用
        error：处理请求时发生错误时被调用
        * */
        // 前置过滤器
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //// 优先级为0，数字越大，优先级越低
        return 1;
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
        //对下载路由进行路由
        if (url.indexOf("ByteOutput") >= 0) {
            return true;
        }
        return false;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String url = request.getServletPath();
        String referer =  request.getHeader("referer");
        logger.debug("******referer:"+referer);
        //没有referer， 或者不是从指定域名来的请求
        boolean isReferer = StringUtils.isEmpty(referer) || !referer.trim().contains("XXXX");
        if (isReferer) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(200);
            BaseOutput result = new BaseOutput();
            result.setResultCode(ResultCode.AUTH_ERR);
            result.setResultMessage("非法请求！");
            // 输出最终结果
            ctx.setResponseBody(JSONObject.toJSONString(result));
            return null;
        }
        ctx.setSendZuulResponse(true);
        return null;
    }
}
