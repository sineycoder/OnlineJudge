package cn.jsut.coder.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author:
 * @date:2018/1/20 15:48
 * @version:
 * @copyright:
 */
@Configuration
public class HikariConfig {
    @Bean
    @ConfigurationProperties("spring.dataSource.hikari")
    public HikariDataSource hikariDataSource(){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setMaximumPoolSize(Runtime.getRuntime().availableProcessors());
        return hikariDataSource;
    }
}
