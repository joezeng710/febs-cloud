package cc.mrbird.febs.gateway.filter;

import cc.mrbird.febs.common.entity.FebsConstant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class FebsGatewayRequestFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 6;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        String serviceId = (String) ctx.get(FilterConstants.SERVICE_ID_KEY);
        HttpServletRequest request = ctx.getRequest();
        String remoteHost = request.getRemoteHost();
        String method = request.getMethod();
        String requestURI = request.getRequestURI();

        log.info("请求URI: {}, HTTP Method: {}, 请求IP: {}, ServiceId: {}", requestURI, method, remoteHost, serviceId);

        byte[] zuulToken = Base64Utils.encode(FebsConstant.ZUUL_TOKEN_VALUE.getBytes());
        ctx.addZuulRequestHeader(FebsConstant.ZUUL_TOKEN_HEADER, String.valueOf(zuulToken));
        return null;
    }
}
