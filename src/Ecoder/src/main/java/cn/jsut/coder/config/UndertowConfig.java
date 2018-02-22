package cn.jsut.coder.config;

import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author:
 * @date:2018/1/20 15:50
 * @version:
 * @copyright:
 */
@Configuration
public class UndertowConfig {

    @Bean
    @ConfigurationProperties(prefix = "server.undertow")
    public UndertowEmbeddedServletContainerFactory undertowEmbeddedServletContainerFactory(){
        UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();
        return factory;
    }
}
