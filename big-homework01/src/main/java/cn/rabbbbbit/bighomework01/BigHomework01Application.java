package cn.rabbbbbit.bighomework01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BigHomework01Application {

    public static void main(String[] args) {
        SpringApplication.run(BigHomework01Application.class, args);
    }
}
