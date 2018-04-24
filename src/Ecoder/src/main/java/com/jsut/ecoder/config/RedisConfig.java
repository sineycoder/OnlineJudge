package com.jsut.ecoder.config;

import com.jsut.ecoder.controller.EcoderCommonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author:
 * @date:2018/1/20 15:51
 * @version:
 * @copyright:
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    private final Logger logger =  LoggerFactory.getLogger(RedisConfig.class);

    private int minIdle = Runtime.getRuntime().availableProcessors();

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.pool.maxWaitMillis}")
    private int maxWaitMillis;

    @Bean
    public JedisPool jedisPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        logger.info(" [*** loading jedis pool ***] <- operate method[jedisPool] <- class["+this.getClass().getName()+"] ");
        return new JedisPool(jedisPoolConfig,host,port,timeout);
    }

}
