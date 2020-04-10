package cc.mrbird.febs.server.system.controller;

import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.system.TradeLog;
import cc.mrbird.febs.server.system.service.ITradeLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
public class TestController {

    @Autowired
    private ITradeLogService tradeLogService;

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

    @GetMapping("pay")
    public void orderAndPay(TradeLog tradeLog) {
        this.tradeLogService.orderAndPay(tradeLog);
    }

}
