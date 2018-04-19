package com.jsut.ecoder.controller;

import com.alibaba.fastjson.JSON;
import com.jsut.ecoder.judger.Judger;
import com.jsut.ecoder.po.*;
import com.jsut.ecoder.service.CommonService;
import com.jsut.ecoder.service.UserService;
import com.jsut.ecoder.tools.ImgUtils;
import com.jsut.ecoder.tools.RabbitmqSender;
import com.jsut.ecoder.tools.Tools;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

/**
 * @author:
 * @date:2018/1/20 16:40
 * @version:
 * @copyright:
 */
@RestController
@RequestMapping("/common")
public class EcoderCommonController {

    private final Logger logger =  LoggerFactory.getLogger(EcoderCommonController.class);

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserService userService;

    @Autowired
    private Tools tools;

    @Autowired
    private RabbitmqSender rabbitmqClient;

    @RequestMapping(value = "/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response)throws Exception{
        try{
            HttpSession session = request.getSession();
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            String text = new ImgUtils().drawImg().output(response.getOutputStream()).getText();
            session.setAttribute("captcha",text);
            System.out.println(text);
        }catch (Exception e){
            throw new Exception("get captcha failed");
        }
    }

    @RequestMapping(value = "/notices",method = RequestMethod.GET)
    public List<Notice> queryNotices(HttpSession session) throws Exception {
        System.out.println(session.getId());
        List<Notice> notices = commonService.queryVisibleNotices();
        return notices;
    }

    @RequestMapping(value = "/verify",method = RequestMethod.GET)
    public Map<String,Object> verifyLogin(HttpSession session){
        Map<String,Object> map = new HashMap<>();
        User user = (User)session.getAttribute("identity");
        System.out.println(user);
        map.put("user",user);
        logger.info(" [*** user[-- "+user+" --] verify ***] <- operate method[verifyLogin] <- class["+this.getClass().getName()+"] ");
        return map;
    }

    @RequestMapping(value = "/submit/{page}",method = RequestMethod.GET)
    public Map<String,Object> getSubmits(@PathVariable("page")int page) throws Exception {
        Map<String,Object> map = new HashMap<>();
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(page);
        pagination.setTotal(commonService.querySubmitsCounts(null));
        List<SubmitsRecords> submitsRecords = commonService.querySubmitsTotals(pagination, null);//args:null 查询出来所有结果
        map.put("pagination",pagination);
        map.put("submits",submitsRecords);
        return map;
    }

    @RequestMapping(value = "/problems/{page}",method = RequestMethod.GET)
    public Map<String,Object> getProblems(@PathVariable("page")int page,HttpSession session) throws Exception {
        User user = (User)session.getAttribute("identity");
        Map<String,Object> map = new HashMap<>();
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(page);
        pagination.setTotal(commonService.queryProblemsCounts());
        List<ProblemRecords> problemsRecords = commonService.queryProblemsTotals(pagination);
        List<Tag> tags = commonService.queryProblemsTags(pagination);
        if(user != null){
            String userName = user.getUserName();
            List<Result> ac = userService.queryUserProblemsTotals(pagination, userName, "AC");
            map.put("ac",ac);
            List<Result> wa = userService.queryUserProblemsTotals(pagination, userName, "WA");
            map.put("wa",wa);
        }
        map.put("pagination",pagination);
        map.put("problems",problemsRecords);
        map.put("tags",tags);
        return map;
    }

    @RequestMapping(value = "/rank/{page}",method = RequestMethod.GET)
    public Map<String,Object> getRankUsers(@PathVariable("page")int page) throws Exception {
        Map<String,Object> map = new HashMap<>();
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(page);
        pagination.setTotal(userService.queryUsers());
        List<RankUsers> rankUsers = commonService.queryRankResults(pagination, null);
        map.put("pagination",pagination);
        map.put("rankList",rankUsers);
        return map;
    }

    @RequestMapping(value = "/contest/{page}",method = RequestMethod.GET)
    public Map<String,Object> getContestResult(@PathVariable("page")int page)throws Exception{
        Map<String,Object> map = new HashMap<>();
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(page);
        pagination.setTotal(commonService.queryContestTotals());
        List<Contest> contests = commonService.queryContestResult(pagination);
        map.put("pagination", pagination);
        map.put("contestList", contests);
        return map;
    }

    @RequestMapping(value = "/coding/{id}",method = RequestMethod.GET)
    public Map<String,Object> getProblemCoding(@PathVariable("id")int id)throws Exception {
        Map<String,Object> map = new HashMap<>();
        ProblemRecords problemsRecords = commonService.queryProblemCodingById(id);
        List<ProblemParams> problemParams = commonService.queryProblemParamsById(id);
        problemsRecords.setParams(problemParams);
        System.out.println(problemsRecords);
        map.put("problem",problemsRecords);
        return map;
    }

    @RequestMapping("/showRepImage/{username}")
    public void showRepImage(@PathVariable("username")String username, HttpSession session,HttpServletResponse response)throws Exception{
        String path = Tools.IMAGE_REP_PATH+"/"+username;
        File[] file = new File(path).listFiles();
        if(file != null && file.length == 1){
            tools.writeImage(response,file[0].getAbsolutePath());
        }else{
            tools.writeImage(response,Tools.IMAGE_USE_PATH+"/default_portrait.jpg");
        }
    }

    @RequestMapping("/showUserImage/{username}")
    public void showUserImage(@PathVariable("username")String username, HttpSession session,HttpServletResponse response)throws Exception{
        String path = Tools.IMAGE_USE_PATH+"/"+username;
        File[] file = new File(path).listFiles();
        if(file != null && file.length == 1){
            tools.writeImage(response,file[0].getAbsolutePath());
        }else{
            tools.writeImage(response,Tools.IMAGE_USE_PATH+"/default_portrait.jpg");
        }
    }



    /*@Autowired
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

    @RequestMapping("/test")
    public @ResponseBody Date test(Date date) throws Exception {

//        judger.judge("nihao");
        System.out.println(date);
        return date;
    }*/

}
