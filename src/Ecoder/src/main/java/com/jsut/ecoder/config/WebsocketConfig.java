
package com.jsut.ecoder.config;

import com.jsut.ecoder.controller.EcoderCommonController;
import io.undertow.websockets.jsr.ServerWebSocketContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.server.ServerContainer;


/**
 * @author:
 * @date:2018/1/20 16:04
 * @version:
 * @copyright:
 */

@Configuration
public class WebsocketConfig {

    private final Logger logger =  LoggerFactory.getLogger(EcoderCommonController.class);

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        logger.info(" [*** loading websocket ***] <- operate method[serverEndpointExporter] <- class["+this.getClass().getName()+"] ");
        ServerEndpointExporter server = new ServerEndpointExporter();
        return server;
    }

}

