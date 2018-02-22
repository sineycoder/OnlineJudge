package cn.jsut.coder.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages = "cn.jsut.coder")
@MapperScan("cn.jsut.coder.dao")
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@EnableTransactionManagement
@EnableCaching
@SpringBootApplication
public class ScoderApplication {
	public static void main(String[] args) {
		SpringApplication.run(ScoderApplication.class, args);
	}

}
