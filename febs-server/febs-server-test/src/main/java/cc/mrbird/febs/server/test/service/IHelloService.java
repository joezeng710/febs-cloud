package cc.mrbird.febs.server.test.service;

import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.FebsServerConstant;
import cc.mrbird.febs.server.test.service.fallback.HelloServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = FebsServerConstant.FEBS_SERVER_SYSTEM, contextId = "helloServiceClient", fallbackFactory = HelloServiceFallbackFactory.class)
public interface IHelloService {
    @GetMapping("hello")
    FebsResponse hello(@RequestParam("name") String name);
}