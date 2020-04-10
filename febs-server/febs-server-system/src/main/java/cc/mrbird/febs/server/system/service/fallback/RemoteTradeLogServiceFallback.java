package cc.mrbird.febs.server.system.service.fallback;

import cc.mrbird.febs.common.entity.system.TradeLog;
import cc.mrbird.febs.server.system.service.IRemoteTradeService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author JOE
 * @create 2020/4/10 15:21
 */
@Slf4j
@Component
public class RemoteTradeLogServiceFallback implements FallbackFactory<IRemoteTradeService> {
    @Override
    public IRemoteTradeService create(Throwable cause) {
        return new IRemoteTradeService() {
            @Override
            public void packageAndSend(TradeLog tradeLog) {
                log.info("调用失败", cause);
            }
        };
    }
}
