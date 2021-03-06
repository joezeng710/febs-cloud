package cc.mrbird.febs.server.system;

import cc.mrbird.febs.common.annotaion.EnableFebsAuthExceptionHandler;
import cc.mrbird.febs.common.annotaion.EnableFebsServerProtect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableFebsAuthExceptionHandler
@EnableFebsServerProtect
@EnableTransactionManagement
@MapperScan("cc.mrbird.febs.server.system.mapper")
public class FebsServerSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FebsServerSystemApplication.class, args);
    }

}
