package cc.mrbird.febs.auth;

import cc.mrbird.febs.common.annotaion.EnableFebsAuthExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableFebsAuthExceptionHandler
@EnableDiscoveryClient
@SpringBootApplication
public class FebsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(FebsAuthApplication.class, args);
    }

}
