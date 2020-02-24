package cc.mrbird.febs.server.test.service.fallback;

import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.server.test.service.IHelloService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloServiceFallbackFactory implements FallbackFactory<IHelloService> {
    @Override
    public IHelloService create(Throwable cause) {
        return new IHelloService() {
            @Override
            public FebsResponse hello(String name) {
                log.error("调用febs-server-system服务出错", cause);
                return new FebsResponse().message("调用错误");
            }
        };
    }
}
