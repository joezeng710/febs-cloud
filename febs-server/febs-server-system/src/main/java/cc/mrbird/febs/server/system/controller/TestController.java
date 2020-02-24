package cc.mrbird.febs.server.system.controller;

import cc.mrbird.febs.common.entity.FebsResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestController {
    @GetMapping("info")
    public String test() {
        return "febs-server-system";
    }

    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }

    @GetMapping("hello")
    public FebsResponse hello(String name) {
        return new FebsResponse().message("hello" + name);
    }
}
