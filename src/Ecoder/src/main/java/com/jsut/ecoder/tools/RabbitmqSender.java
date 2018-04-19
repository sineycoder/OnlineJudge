package com.jsut.ecoder.tools;

import com.rabbitmq.client.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqSender {

    private static final String QUEUE_NAME = "judge_queue";

    @Autowired
    Channel channel;

    public void sendMessage(String message)throws Exception{
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
    }

}
