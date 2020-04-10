package cc.mrbird.febs.gateway.filter;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.gateway.properties.FebsGatewatProperties;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;

@Slf4j
@Component
public class FebsGatewayRequestFilter implements GlobalFilter {
    @Autowired
    private FebsGatewatProperties properties;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 自定义逻辑
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 禁止客户端的访问资源逻辑
        Mono<Void> checkForbidUriResult = checkForbidUri(request, response);
        if ( checkForbidUriResult != null) {
            return checkForbidUriResult;
        }

        //日志打印
        printLog(exchange);

        // 添加网关Token "GATEWAY_TOKEN_HEADER" : "GATEWAY_TOKEN_VALUE"
        byte[] token = Base64Utils.encode(FebsConstant.GATEWAY_TOKEN_VALUE.getBytes());
        ServerHttpRequest build = request.mutate().header(FebsConstant.GATEWAY_TOKEN_HEADER, new String(token)).build();
        ServerWebExchange newExchange = exchange.mutate().request(build).build();
        return chain.filter(newExchange);
    }

    private Mono<Void> checkForbidUri(ServerHttpRequest request, ServerHttpResponse response) {
        String uri = request.getPath().toString();
        boolean shouldForward = true;
        String forbidRequestUri = properties.getForbidRequestUri();
        String[] forbidUris = StringUtils.splitByWholeSeparatorPreserveAllTokens(forbidRequestUri,",");
        if (forbidUris != null && ArrayUtils.isNotEmpty(forbidUris)) {
            for (String i : forbidUris) {
                if (pathMatcher.match(i, uri)) {
                    shouldForward = false;
                }
            }
        }
        if ( !shouldForward) {
            FebsResponse febsResponse = new FebsResponse().message("該URI不允許外部訪問");
            return makeResponse(response, febsResponse);
        }
        return null;
    }

    private Mono<Void> makeResponse(ServerHttpResponse response, FebsResponse febsResponse){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSONObject.toJSONString(febsResponse).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }

    private void printLog(ServerWebExchange exchange) {
        URI url = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        LinkedHashSet<URI> uris = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        URI originUri = null;
        if ( uris != null) {
            originUri = uris.stream().findFirst().orElse(null);
        }
        if ( url != null && route != null && originUri != null) {
            log.info("转发请求：{}://{}{} --> 目标服务：{}，目标地址：{}://{}{}，转发时间：{}",
                    originUri.getScheme(), originUri.getAuthority(), originUri.getPath(),
                    route.getId(), url.getScheme(), url.getAuthority(), url.getPath(), LocalDateTime.now()
            );
        }
    }
}
