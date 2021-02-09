package cn.luotuoyulang.myspringbootstarter;

import cn.luotuoyulang.myspringbootstarter.annotation.EnableSensitive;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSensitive(packages = "cn.luotuoyulang")
@SpringBootApplication
public class MySpringbootStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySpringbootStarterApplication.class, args);
    }

}
