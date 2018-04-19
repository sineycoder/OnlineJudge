package com.jsut.ecoder.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:
 * @date:2018/1/16 10:10
 * @version:
 * @copyright:
 */
@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String,Object> exceptionHandler(Exception e){
        HashMap<String, Object> result = new HashMap<>();
        e.printStackTrace();
        result.put("code",998);
        result.put("message",e.getMessage());
        //返回json格式
        return result;
    }
}
