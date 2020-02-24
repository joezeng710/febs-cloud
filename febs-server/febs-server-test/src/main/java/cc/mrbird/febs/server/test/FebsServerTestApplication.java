package cc.mrbird.febs.server.test;

import cc.mrbird.febs.common.annotaion.EnableFebsAuthExceptionHandler;
import cc.mrbird.febs.common.annotaion.EnableFebsOauth2FeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableDiscoveryClient
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
@EnableFebsAuthExceptionHandler
@EnableFebsOauth2FeignClient
public class FebsServerTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(FebsServerTestApplication.class, args);
    }

}
