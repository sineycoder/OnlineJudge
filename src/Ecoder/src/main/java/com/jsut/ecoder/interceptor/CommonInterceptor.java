package com.jsut.ecoder.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author:
 * @date:2018/1/20 16:01
 * @version:
 * @copyright:
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger =  LoggerFactory.getLogger(CommonInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info(" [*** request common interceptor ***] <- operate method[preHandle] <- class["+this.getClass().getName()+"] ");
//       response.sendError(999,"you not login");
        return true;
    }
}
