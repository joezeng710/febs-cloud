package cc.mrbird.febs.gateway.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:febs-gateway.properties"})
@ConfigurationProperties(prefix = "febs.gateway")
public class FebsGatewatProperties {
    /**
     * 禁止外部訪問的URL
     **/
    private String forbidRequestUri;
}
