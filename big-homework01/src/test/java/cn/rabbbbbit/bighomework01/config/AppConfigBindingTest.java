package cn.rabbbbbit.bighomework01.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("dev")
class AppConfigBindingTest {

    @Autowired
    private AppConfig appConfig;

    @Test
    void shouldBindAppConfigFromYaml() {
        assertThat(appConfig.getName()).isEqualTo("Spring Boot 第03周大作业");
        assertThat(appConfig.getVersion()).isEqualTo("1.0.0");
        assertThat(appConfig.getDescription()).isEqualTo("配置管理与多环境管理综合练习项目");
        assertThat(appConfig.getUploadDir()).isEqualTo("uploads");
        assertThat(appConfig.getDatabase().getHost()).isEqualTo("localhost");
        assertThat(appConfig.getDatabase().getUsername()).isEqualTo("study_user");
    }
}
