package cc.mrbird.febs.server.system.service;

import cc.mrbird.febs.common.entity.FebsServerConstant;
import cc.mrbird.febs.common.entity.system.TradeLog;
import cc.mrbird.febs.server.system.service.fallback.RemoteTradeLogServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author JOE
 * @create 2020/4/10 15:19
 */
@FeignClient(value= FebsServerConstant.FEBS_SERVER_TEST, contextId = "tradeLogServiceClient", fallbackFactory = RemoteTradeLogServiceFallback.class)
public interface IRemoteTradeService {

    @PostMapping("package/send")
    void packageAndSend(@RequestBody TradeLog tradeLog);
}
