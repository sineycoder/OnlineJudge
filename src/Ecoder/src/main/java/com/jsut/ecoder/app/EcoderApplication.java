package com.jsut.ecoder.app;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages = "com.jsut.ecoder")
@MapperScan("com.jsut.ecoder.dao")
@EnableAutoConfiguration
@EnableTransactionManagement
@SpringBootApplication
public class EcoderApplication {

    public static void main(String[] args) {
       SpringApplication.run(EcoderApplication.class, args);
    }
}
