package cn.rabbbbbit.bighomework01.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

class AppConfigValidationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(
                    ConfigurationPropertiesAutoConfiguration.class,
                    ValidationAutoConfiguration.class
            ))
            .withUserConfiguration(TestConfiguration.class);

    @Test
    void shouldFailWhenAppNameIsBlank() {
        contextRunner
                .withPropertyValues(
                        "app.name=",
                        "app.version=1.0.0",
                        "app.description=test",
                        "app.upload-dir=uploads",
                        "app.database.host=localhost"
                )
                .run(context -> {
                    assertThat(context).hasFailed();
                    assertThat(context.getStartupFailure()).hasStackTraceContaining("app.name 不能为空");
                });
    }

    @Configuration(proxyBeanMethods = false)
    @EnableConfigurationProperties(AppConfig.class)
    static class TestConfiguration {
    }
}
