package edu.neu.absorb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("edu.neu.absorb.mapper")
public class AbsorbApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbsorbApplication.class, args);
    }

}
