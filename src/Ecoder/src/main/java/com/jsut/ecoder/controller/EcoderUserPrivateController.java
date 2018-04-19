package com.jsut.ecoder.controller;

import com.alibaba.fastjson.JSON;
import com.jsut.ecoder.judger.Judger;
import com.jsut.ecoder.po.*;
import com.jsut.ecoder.service.CommonService;
import com.jsut.ecoder.service.UserService;
import com.jsut.ecoder.tools.JudgerHelper;
import com.jsut.ecoder.tools.RabbitmqSender;
import com.jsut.ecoder.tools.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/userPrivate")
public class EcoderUserPrivateController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private JudgerHelper judgerHelper;

    @Autowired
    private RabbitmqSender rabbitmqClient;

    @Autowired
    private Tools tools;

    @RequestMapping(value = "/chart/{id}", method = RequestMethod.GET)
    public Map<String,Object> submit(@PathVariable("id") int id)throws Exception{
        Map<String,Object> map = new HashMap<>();
        int ac = userService.querySubmissionByResult(id,"AC");
        int wa = userService.querySubmissionByResult(id,"WA");
        map.put("ac",ac);
        map.put("wa",wa);
        return map;
    }

    @RequestMapping(value = "/submit/{userName}/{page}",method = RequestMethod.GET)
    public Map<String,Object> getSubmits(@PathVariable("page")int page,@PathVariable("userName")String userName) throws Exception {
        Map<String,Object> map = new HashMap<>();
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(page);
        pagination.setTotal(commonService.querySubmitsCounts(userName));
        List<SubmitsRecords> submitsRecords = commonService.querySubmitsTotals(pagination, userName);//args:null 查询出来所有结果
        map.put("pagination",pagination);
        map.put("submits",submitsRecords);
        return map;
    }

    @RequestMapping(value = "/judge/{id}",method = RequestMethod.POST)
    public String test(@PathVariable("id")Integer id,
                       @RequestParam("language")String language,
                       @RequestParam("content")String content,
                       HttpSession session) throws Exception {
        User user = (User)session.getAttribute("identity");
        if(user != null){
            ProblemRecords problemRecords = userService.queryJudgeProblemByIdAndLanguage(id,language);
            JudgeBody judgeBody = judgerHelper.returnJudgeBody(problemRecords, language, content);
            SubmitsRecords records = new SubmitsRecords();
            records.setCodes(judgeBody.getCodes());
            records.setUserId(user.getUserId());
            records.setSubmitDate(new Date());
            records.setLanguage(language);
            records.setProblemId(id);
            records.setResult("Pending");
            userService.addJudgeSubmit(records);//select first record and return primary key(id);
            judgeBody.setSubmitId(records.getId());
            rabbitmqClient.sendMessage(JSON.toJSONString(judgeBody));//after returning, start judging
            System.out.println("返回的主键为："+records.getId());
        }
        return "yes";
    }

    @RequestMapping(value = "/upload")
    public String uploadImage(@RequestParam("image") MultipartFile image,HttpSession session,HttpServletResponse response) throws Exception {
        User user = (User)session.getAttribute("identity");
        if(user != null){
            String parentPath = Tools.IMAGE_REP_PATH+"/"+user.getUserName();
            File file = new File(parentPath);
            if(file.exists())judgerHelper.deleteAll(file);
            file.mkdirs();
            String filename = UUID.randomUUID().toString()
                    .replace("-","")+
                    tools.getFilenameSuffix(image.getOriginalFilename());
            String imagePath = parentPath+"/"+filename;
            file = new File(imagePath);
            session.setAttribute("image",filename);
            System.out.println(image.getSize());
            System.out.println(image.getName());
            image.transferTo(new File(imagePath));
        }
        return "ok";
    }

    @RequestMapping(value = "/save")
    public String saveImg(HttpSession session,Image img)throws Exception{

        User user = (User)session.getAttribute("identity");
        String image = (String)session.getAttribute("image");
        if(user != null && image != null){
            String targetPath = Tools.IMAGE_USE_PATH+"/"+user.getUserName();
            String source_file = Tools.IMAGE_REP_PATH+"/"+user.getUserName()+"/"+image;
            String target_file = targetPath + "/" + image;
            File file = new File(targetPath);
            if(file.exists())judgerHelper.deleteAll(file);
            file.mkdirs();
            Tools.cutImage(source_file,target_file,img.getX(),img.getY(),img.getW(),img.getH());
        }
        return "success";

    }

    @RequestMapping(value = "/saveUsername")
    public Map<String,Object> saveUsername(@RequestParam("username")String username,HttpSession session)throws Exception{
        Map<String,Object> map = new HashMap<>();
        User user = (User)session.getAttribute("identity");
        if(user != null){
            Integer result = userService.queryUserByUserName(username);
            if(result == 0){
                user.setIsChange(1);
                user.setUserName(username);
                System.out.println(user);
                session.setAttribute("identity",user);
                userService.modifyUserChange(user);
                map.put("success","modify your username success !");
            }else{
                map.put("error","the username is used by other person");
            }
        }else{
            map.put("error","can't identify your identification");
        }
        return map;
    }

    @RequestMapping(value = "/exit")
    public Map<String,Object> exit(HttpSession session){
        Map<String,Object> map = new HashMap<>();
        User user = (User)session.getAttribute("identity");
        if(user != null){
            session.removeAttribute("identity");
            map.put("success","exit success !");
        }else{
            map.put("error","can't identify your identification");
        }
        return map;
    }


}
