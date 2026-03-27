package cn.rabbbbbit.week03.config;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppConfigTest {

    @Resource
    private AppConfig appConfig;

    @Test
    void getAppConfig() {
        System.out.println("AppConfig: " + appConfig);
    }

    @Test
    void getAppName() {
    }

    @Test
    void getAppVersion() {
    }

    @Test
    void getAppDescription() {
    }

    @Test
    void getAuthor() {
    }

    @Test
    void getFeatures() {
    }
}