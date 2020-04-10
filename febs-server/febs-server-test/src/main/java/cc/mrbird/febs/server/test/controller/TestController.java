package cc.mrbird.febs.server.test.controller;

import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.system.TradeLog;

import cc.mrbird.febs.server.test.service.IHelloService;
import cc.mrbird.febs.server.test.service.ITradeLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
public class TestController {

    @Autowired
    private IHelloService helloService;
    @Autowired
    private ITradeLogService tradeLogService;

    @GetMapping("hello")
    public FebsResponse hello(String name, String lastName){
        log.info("Feign调用febs-server-system的/hello服务");
        return this.helloService.hello(" " + name + " "+lastName);
    }

    @GetMapping("test1")
    @PreAuthorize("hasAnyAuthority('user:add')")
    public String test1(){
        return "拥有'user:add'权限";
    }

    @GetMapping("test2")
    @PreAuthorize("hasAnyAuthority('user:update')")
    public String test2(){
        return "拥有'user:update'权限";
    }

    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }

    @PostMapping("package/send")
    public String packageAndSend(@RequestBody TradeLog tradeLog) {
        tradeLogService.packageAndSend(tradeLog);
        return "Success send";
    }

}
