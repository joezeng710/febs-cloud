package cc.mrbird.febs.gateway.controller;

import cc.mrbird.febs.common.entity.FebsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FabllbackController {
    @RequestMapping("/fallback/{name}")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<FebsResponse> systemFallback(@PathVariable String name) {
        String message = String.format("访问%s超时,服务不可用", name);
        return Mono.just(new FebsResponse().message(message));
    }
}
