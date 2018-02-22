package cn.jsut.coder.controller;

import cn.jsut.coder.service.ScoderService;
import cn.jsut.coder.po.User;
import cn.jsut.coder.judger.Judger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author:
 * @date:2018/1/20 16:40
 * @version:
 * @copyright:
 */
@RestController
public class ScoderPageController {

    private final Logger logger =  LoggerFactory.getLogger(ScoderPageController.class);

    @Autowired
    ScoderService scoderService;

    @Autowired
    Judger judger;

    @RequestMapping("/index")
    public String index() throws Exception {
        System.out.println("进入");
        User user = new User();
        user.setUserId(UUID.randomUUID().toString().replace("-",""));
        user.setUserName("tom");
        user.setUserPwd("3333");
        user.setUserGrant("0");
        user.setUserEmail("222@qq.com");
        System.out.println(user);
        scoderService.addNewUser(user);
        return "index";
    }

    @RequestMapping("/judge")
    public @ResponseBody String judge() throws Exception {

//        judger.judge("nihao");

        logger.info("?????????aaaaaaaaaaaaaaaaa");

        return "successful";
    }

}
