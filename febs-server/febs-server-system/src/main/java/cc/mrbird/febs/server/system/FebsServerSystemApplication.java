package cc.mrbird.febs.server.system;

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
@EnableFebsOauth2FeignClient
@SpringBootApplication
@EnableFebsServerProtect
@EnableFebsAuthExceptionHandler
@EnableTransactionManagement
@EnableDistributedTransaction
@MapperScan("cc.mrbird.febs.server.system.mapper")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class FebsServerSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FebsServerSystemApplication.class, args);
    }

}
