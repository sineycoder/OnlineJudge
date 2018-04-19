package com.jsut.ecoder.config;

import com.alibaba.fastjson.JSON;
import com.jsut.ecoder.controller.EcoderCommonController;
import com.jsut.ecoder.judger.Judger;
import com.jsut.ecoder.po.JudgeBody;
import com.jsut.ecoder.po.JudgeResult;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;


@Configuration
public class RabbitmqConfig {

    private final Logger logger =  LoggerFactory.getLogger(EcoderCommonController.class);

    @Autowired
    private Judger judger;

    private static final String QUEUE_NAME = "judge_queue";

    @Value("${rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.port}")
    private int port;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.size}")
    private int size;

    @Bean
    Channel channel() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setHost(host);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        for(int i = 0; i < size;i++){
            int a = i;
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    JudgeBody judgeBody = JSON.parseObject(message,JudgeBody.class);
                    System.out.println(" ["+a+"] Received '"+"" + judgeBody + "'");
                    try {
                        JudgeResult result = judger.work(message);
                    } catch (Exception e){
                        e.printStackTrace();
                    } finally{
                        System.out.println(" [x] Done");
                    }
                }
            };
            channel.basicConsume(QUEUE_NAME, true, consumer);//true 是否启动确认机制，就是消费者失败，会重回队列
        }
        System.out.println(factory.getHost());
        logger.info(" [*** loading rabbitmq ***] <- operate method[channel] <- class["+this.getClass().getName()+"] ");
        return channel;
    }

}
