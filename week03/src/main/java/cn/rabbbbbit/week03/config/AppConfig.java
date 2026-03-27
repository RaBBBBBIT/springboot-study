package cn.rabbbbbit.week03.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String appName;
    private String appVersion;
    private String appDescription;
    private Author author;
    private List<String> features;

    @Data
    private static class Author {
        private String name;
    }
}
