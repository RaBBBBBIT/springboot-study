package cn.rabbbbbit.week03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Week03Application {

    public static void main(String[] args) {
//        SpringApplication.run(Week03Application.class, args);
        int i = 5;
        switch (i) {
            default: System.out.println("default");
            case 4: System.out.println("four");
            case 5: System.out.println("five"); break;
            case 6: System.out.println("six");
        }
    }

}
