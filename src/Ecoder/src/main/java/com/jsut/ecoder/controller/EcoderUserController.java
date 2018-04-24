package com.jsut.ecoder.controller;

import com.jsut.ecoder.po.User;
import com.jsut.ecoder.service.impl.UserServiceImp;
import com.jsut.ecoder.tools.Tools;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
//@RequestMapping("/user")
@RequestMapping("/api/user")
public class EcoderUserController {

    private final Logger logger =  LoggerFactory.getLogger(EcoderUserController.class);

    @Autowired
    private UserServiceImp userService;

    @Autowired
    private Tools tools;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Map<String, Object> addUser(User user,HttpSession session) throws Exception{
        Map<String,Object> map = new HashMap<>();
        int res = userService.isExsitUser(user.getUserName());
        String captcha = (String)session.getAttribute("captcha");
        if(captcha != null && captcha.equals(user.getCaptcha())){
            if(res == 0){
                user.setUserPwd(DigestUtils.sha1Hex(user.getUserPwd()));
                Date d = new Date();
                user.setCreateTime(d);
                user.setLastLogin(d);
                userService.addUser(user);
                map.put("success","注册成功!");
            }else{
                map.put("error","用户名已经存在!");
            }
        }else{
            map.put("error","验证码错误!");
        }

        return map;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String,Object> login(User user, HttpSession session)throws Exception{
        Map<String,Object> map = new HashMap<>();
        user.setUserPwd(DigestUtils.sha1Hex(user.getUserPwd()));
        User res = userService.queryUserByuserNameAndPwd(user);
        String captcha = (String)session.getAttribute("captcha");
        System.out.println(res+" "+captcha);
        if(res == null){
            map.put("error","用户名或密码错误!");
        }else if(captcha != null && captcha.equals(user.getCaptcha())){
            session.setAttribute("identity",res);
            res.setLastLogin(new Date());
            userService.modifyLastLoginTime(res);
            map.put("success","登录成功!");
            map.put("user",res);
        }else{
            map.put("error","验证码输入有误!");
        }
        logger.info("class["+this.getClass().getName()+"] operate method[login] -> user login!");
        return map;
    }

    @RequestMapping(value = "/forget",method = RequestMethod.POST)
    public Map<String,Object> forget(@RequestParam("userEmail")String mail,
                         @RequestParam("captcha")String captcha,
                         @RequestParam("option")String option,HttpSession session) throws Exception {
        Map<String,Object> map = new HashMap<>();
        String cap = (String)session.getAttribute("captcha");
        if(cap.equals(captcha)){
            int res = userService.isExistEmail(mail);
            if(res == 0){
                map.put("error","没有用户使用该邮箱，发送失败!");
            }else{
                if("send".equals(option)){
                    tools.sendEmail(mail,"你的密码是<span style='color:blue;'>"+123123+"</span>");
                    map.put("success","您的密码已经发送至你的邮箱，请登录查看!");
                }else{
                    map.put("success","请在30分钟之内登录邮箱进行密码重置");
                }
            }
        }else{
            map.put("error","验证码输入有误!");
        }

        return map;
    }



}
