package com.jsut.ecoder.config;

import com.jsut.ecoder.controller.EcoderCommonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author:
 * @date:2018/3/10 20:05
 * @version:
 * @copyright:
 */
@Configuration
public class UndertowConfig {

    private final Logger logger =  LoggerFactory.getLogger(EcoderCommonController.class);

    @Bean
    @ConfigurationProperties(prefix = "server.undertow")
    UndertowServletWebServerFactory undertowServletWebServerFactory() {
        logger.info(" [*** loading undertow container ***] <- operate method[undertowServletWebServerFactory] <- class["+this.getClass().getName()+"] ");
        UndertowServletWebServerFactory undertow = new UndertowServletWebServerFactory();
        return undertow;
    }
}
