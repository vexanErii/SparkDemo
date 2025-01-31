package com.example.echarts;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.example.echarts.dao")
//@ComponentScan("com.example.echarts")
public class EchartsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EchartsApplication.class, args);
    }

}
