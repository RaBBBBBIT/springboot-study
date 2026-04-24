package cn.rabbbbbit.week05.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI week05OpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Week05 MyBatis API")
                        .description("Spring Boot 3 + MyBatis + MySQL + OpenAPI 示例")
                        .version("1.0.0"));
    }
}
