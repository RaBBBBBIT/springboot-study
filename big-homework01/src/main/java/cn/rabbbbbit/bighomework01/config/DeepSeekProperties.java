package cn.rabbbbbit.bighomework01.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "deepseek")
public class DeepSeekProperties {
    private String baseUrl;
    private String apiKey;
    private String model;
}
