package cc.mrbird.febs.server.system.controller;

import cc.mrbird.febs.common.entity.FebsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
public class TestController {
    @GetMapping("info")
    public String test() {
        return "febs-server-system";
    }

    @GetMapping("currentUser")
    public Principal currentUser(Principal principal) {
        return principal;
    }

    @GetMapping("hello")
    public FebsResponse hello(String name) {
        log.info("/hello服务被调用");
        return new FebsResponse().message("hello" + name);
    }
}
