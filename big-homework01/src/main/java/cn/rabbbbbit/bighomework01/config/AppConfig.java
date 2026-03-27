package cn.rabbbbbit.bighomework01.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    @NotBlank(message = "app.name 不能为空")
    private String name;

    private String version;

    private String description;

    private String uploadDir;

    private Database database = new Database();

    @Data
    public static class Database {
        private String host;
        private String username;
        private String password;
    }
}
