package com.jsut.ecoder.interceptor;

import com.jsut.ecoder.po.User;
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
public class BackendInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger =  LoggerFactory.getLogger(BackendInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info(" [*** request backend interceptor ***] <- operate method[preHandle] <- class["+this.getClass().getName()+"] ");
        User user = (User)request.getSession().getAttribute("identity");
        if(user != null && user.getUserGrant().equals("1")){
            return true;
        }
        response.sendError(999,"not login or not admin");
        return false;
    }

}
