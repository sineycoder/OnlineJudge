package cn.jsut.coder.exception;

import org.springframework.ui.Model;
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
        result.put("code",500);
        result.put("message",e.getMessage()+"亲，系统错误请稍后重试...");
        //返回json格式
        return result;
    }
}
