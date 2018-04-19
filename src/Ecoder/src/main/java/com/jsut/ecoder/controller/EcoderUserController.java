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
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
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
        if(res == 0 && captcha != null && captcha.equals(user.getCaptcha())){
            user.setUserPwd(DigestUtils.sha1Hex(user.getUserPwd()));
            userService.addUser(user);
            map.put("success","register success!");
        }else{
            map.put("error","your username is exist!");
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
            map.put("error","your username or password error !");
        }else if(captcha != null && captcha.equals(user.getCaptcha())){
            session.setAttribute("identity",res);
            map.put("success","login success !");
            map.put("user",res);
        }else{
            map.put("error","your captcha is error !");
        }
        logger.info("class["+this.getClass().getName()+"] operate method[login] -> user login!");
        return map;
    }

    @RequestMapping(value = "/forget")
    public Map<String,Object> forget(@RequestParam("userEmail")String mail,
                         @RequestParam("captcha")String captcha,
                         @RequestParam("option")String option,HttpSession session) throws Exception {
        Map<String,Object> map = new HashMap<>();
        String cap = (String)session.getAttribute("captcha");
        if(cap.equals(captcha)){
            //        sendEmail();
            int res = userService.isExistEmail(mail);
            if(res == 0){
                map.put("error","not exist this Email !");
            }else{
                if("send".equals(option)){
                    map.put("success","your password is sending your Email, check it !");
                }else{
                    map.put("success","login your Email and reset your password in 30 minutes");
                }
            }
        }else{
            map.put("error","your captcha is not match !");
        }

        return map;
    }



}
