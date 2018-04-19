package com.jsut.ecoder.interceptor;

import com.jsut.ecoder.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserPrivateInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger =  LoggerFactory.getLogger(UserInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info(" [*** request userPrivate interceptor ***] <- operate method[preHandle] <- class["+this.getClass().getName()+"] ");
        User user = (User)request.getSession().getAttribute("identity");
        System.out.println(user);
//        if(user == null){
//            response.sendError(999,"Not Login !");
//            return false;
//        }
        return true;
    }



}
