package cc.mrbird.febs.server.test;

import cc.mrbird.febs.common.annotaion.EnableFebsAuthExceptionHandler;
import cc.mrbird.febs.common.annotaion.EnableFebsOauth2FeignClient;
import cc.mrbird.febs.common.annotaion.EnableFebsServerProtect;
import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
@EnableFebsAuthExceptionHandler
@EnableFebsOauth2FeignClient
@EnableDistributedTransaction
@EnableFebsServerProtect
@EnableTransactionManagement
@MapperScan("cc.mrbird.febs.server.test.mapper")
public class FebsServerTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(FebsServerTestApplication.class, args);
    }

}
