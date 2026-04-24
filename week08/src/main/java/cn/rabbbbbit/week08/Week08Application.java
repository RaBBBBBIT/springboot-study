package cn.rabbbbbit.week08;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class    Week08Application {

    public static void main(String[] args) {
        SpringApplication.run(Week08Application.class, args);
    }
}
