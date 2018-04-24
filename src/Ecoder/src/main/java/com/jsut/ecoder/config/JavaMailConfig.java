package com.jsut.ecoder.config;

import com.jsut.ecoder.po.BackendConfig;
import com.jsut.ecoder.service.BackendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class JavaMailConfig {

    @Autowired
    private BackendService backendService;

    @Bean
    public JavaMailSenderImpl javaMailSenderImpl() throws Exception {
        JavaMailSenderImpl mail = new JavaMailSenderImpl();
        BackendConfig config = backendService.queryBackendConfig();
        System.out.println("查询出来的Email "+config);
        mail.setDefaultEncoding("UTF-8");
        mail.setHost(config.getHost());
        mail.setUsername(config.getEmail());
        mail.setPassword(config.getEmailPass());
        mail.setPort(25);
        mail.setProtocol("smtp");
        return mail;
    }

}
