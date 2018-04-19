package com.jsut.ecoder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class JavaMailConfig {

    @Bean
    public JavaMailSenderImpl javaMailSenderImpl(){
        JavaMailSenderImpl mail = new JavaMailSenderImpl();
        mail.setDefaultEncoding("UTF-8");
        mail.setHost("smtp.163.com");
        mail.setUsername("sineyer@163.com");
        mail.setPassword("a398653206");
        mail.setPort(25);
        mail.setProtocol("smtp");
        return mail;
    }

}
