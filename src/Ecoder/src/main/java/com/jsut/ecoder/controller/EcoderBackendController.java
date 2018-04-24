package com.jsut.ecoder.controller;

import com.alibaba.fastjson.JSON;
import com.jsut.ecoder.po.*;
import com.jsut.ecoder.service.BackendService;
import com.jsut.ecoder.service.CommonService;
import com.jsut.ecoder.service.UserService;
import com.jsut.ecoder.tools.OSUtils;
import com.jsut.ecoder.tools.Tools;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
//@RequestMapping("/backend")
@RequestMapping("/api/backend")
public class EcoderBackendController {

    @Autowired
    private Tools tools;

    @Autowired
    private BackendService backendService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @RequestMapping("/upload")
    public Map<String,Object> upload(@RequestParam("file") MultipartFile file, HttpSession session)throws Exception{
        Map<String,Object> map = new HashMap<>();
        ProblemRecords records = new ProblemRecords();
        tools.unZipAndGetCase(file,"/temp/",map,records);
        if(map.get("error") == null){
            session.setAttribute("record",records);
            System.out.println(records);
        }
        return map;
    }

    @RequestMapping("/addToProblems/{problemId}")
    public Map<String,Object> addToProblems(@PathVariable("problemId")Integer problemId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        backendService.addToProblems(problemId);
        map.put("success","添加至题库成功");
        return map;
    }

    @RequestMapping("/contest/{contestId}/createProblem")
    public Map<String,Object> createProblemByContestId(@PathVariable("contestId")Integer contestId,
                                                       ProblemRecords problemRecords,
                                                       HttpSession session)throws Exception{
        Map<String, Object> map = new HashMap<>();
        problemRecords.setProblemContestId(contestId);
        createProblem(problemRecords,session);
        map.put("success","添加题目成功");
        return map;
    }

    @RequestMapping("/contest/{contestId}/problemList/{page}")
    public Map<String,Object> getContestProblemList(@PathVariable("contestId")Integer contestId,
                                                    @PathVariable("page")Integer page)throws Exception{
        Map<String, Object> map = new HashMap<>();
        ProblemRecords record = new ProblemRecords();
        record.setProblemContestId(contestId);
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(page);
        pagination.setTotal(commonService.queryProblemsCounts(record));
        List<ProblemRecords> records = backendService.queryProblemsByContestId(pagination,contestId);
        map.put("problemList",records);
        map.put("pagination",pagination);
        map.put("success","ok");
        return map;
    }

    @RequestMapping("/changeShowContest")
    public Map<String,Object> changeShowContest(@RequestParam("id")Integer id,
                                                @RequestParam("contestVisible")Boolean contestVisible)throws Exception {
        Map<String, Object> map = new HashMap<>();
        Contest contest = new Contest();
        contest.setId(id);
        contest.setContestVisible(contestVisible);
        backendService.modifyContest(contest);
        map.put("success","ok");
        return map;
    }

    @RequestMapping(value = "/deleteContest/{id}")
    public Map<String,Object> deletePContest(@PathVariable("id")Integer id)throws Exception {
        Map<String, Object> map = new HashMap<>();//TODO:这里肯定还需要改善
        backendService.dropContestByContestId(id);
        map.put("success","删除成功");
        return map;
    }

    @RequestMapping("/editContest")
    public Map<String,Object> editContest(Contest contest,HttpSession session)throws Exception {
        Map<String, Object> map = new HashMap<>();
        backendService.dropContestByContestId(contest.getId());
        addContest(contest,session);
        map.put("success","修改比赛成功");
        return map;

    }

    @RequestMapping("/getEditContest/{id}")
    public Map<String,Object> getEditContest(@PathVariable("id")Integer id)throws Exception {
        Map<String, Object> map = new HashMap<>();
        Contest contest = backendService.queryContestById(id);
        map.put("result",contest);
        map.put("success","ok");
        return map;
    }

    @RequestMapping("/contestList/{page}")
    public Map<String,Object> contestList(@PathVariable("page")Integer page)throws Exception {
        Map<String, Object> map = new HashMap<>();
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(page);
        pagination.setTotal(commonService.queryContestTotals(null));
        List<Contest> contests = backendService.queryContests(pagination);
        map.put("pagination",pagination);
        map.put("contests",contests);
        map.put("success","ok");
        return map;
    }

    @RequestMapping("/getEditProblem/{id}")
    public Map<String,Object> getEditProblem(@PathVariable("id")Integer id,HttpSession session)throws Exception{
        Map<String,Object> map = new HashMap<>();
        ProblemRecords records = backendService.queryProblemById(id);
        List<ProblemParams> params = backendService.querytProblemParamsById(id);
        List<Tag> tags = backendService.queryProblemTagsById(id);
        session.setAttribute("record",null);//这里消除了创建文件后又修改文件的冲突
        map.put("records",records);
        map.put("problemParams",params);
        map.put("tag",tags);
        map.put("success","ok");
        return map;
    }

    @RequestMapping(value = "/deleteProblem/{id}")
    public Map<String,Object> deleteProblem(@PathVariable("id")Integer id)throws Exception {
        Map<String, Object> map = new HashMap<>();
        backendService.dropProblemById(id);//删除本题目
        backendService.dropProblemLanguageByProblemId(id);//删除题目相关参数
        backendService.dropProblemTagByProblemId(id);//删除题目标签
        backendService.dropProblemSubmitByProblemId(id);//删除提交记录
        map.put("success","删除成功");
        return map;
    }

    @RequestMapping("/editProblem")
    public Map<String,Object> editProblem(ProblemRecords records, HttpSession session)throws Exception {
        Map<String, Object> map = new HashMap<>();
        ProblemRecords record = (ProblemRecords)session.getAttribute("record");
        if(record == null){
            record = new ProblemRecords();
            record.setProblemInput(records.getProblemInput());
            record.setProblemOutput(records.getProblemOutput());
            session.setAttribute("record",record);
        }
        backendService.dropProblemById(records.getProblemId());
        backendService.dropProblemLanguageByProblemId(records.getProblemId());
        backendService.dropProblemTagByProblemId(records.getProblemId());
        createProblem(records,session);
        map.put("success","修改成功");
        return map;
    }

    @RequestMapping("/changeShowProblem")
    public Map<String,Object> changeShowProblem(@RequestParam("problemId")Integer problemId,
                                               @RequestParam("problemIsShow")Boolean problemIsShow)throws Exception {
        Map<String, Object> map = new HashMap<>();
        ProblemRecords records = new ProblemRecords();
        records.setProblemId(problemId);
        records.setProblemIsShow(problemIsShow);
        backendService.modifyProblem(records);
        map.put("success","ok");
        return map;
    }

    @RequestMapping("/problemList/{page}")
    public Map<String,Object> problemList(@PathVariable("page")Integer page)throws Exception{
        Map<String,Object> map = new HashMap<>();
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(page);
        pagination.setTotal(commonService.queryProblemsCounts(new ProblemRecords()));
        List<ProblemRecords> records = backendService.queryProblems(pagination);
        map.put("problemList",records);
        map.put("pagination",pagination);
        map.put("success","ok");
        return map;
    }

    @RequestMapping("/createProblem")
    public Map<String,Object> createProblem(ProblemRecords records,HttpSession session) throws Exception {
        Map<String,Object> map = new HashMap<>();
        User user = (User)session.getAttribute("identity");
        ProblemRecords record = (ProblemRecords) session.getAttribute("record");
        records.setProblemInput(record.getProblemInput());
        records.setProblemOutput(record.getProblemOutput());
        records.setProblemCreateTime(new Date());
        records.setProblemAuthor(user.getUserName());
        backendService.addNewProblem(records);
        System.out.println("插入的新题目ID为"+records.getProblemId());
        //insert tag
        String[] tags = records.getTag();
        for(int i = 0;i < tags.length;i++){
            Integer id = backendService.queryIdByTagName(tags[i]);
            Tag tag = new Tag();
            tag.setCreateTime(records.getProblemCreateTime());
            tag.setTagName(tags[i]);
            tag.setTagId(id);
            if(id == null){
                backendService.addTag(tag);
            }
            backendService.addTagProblem(tag.getTagId(),records.getProblemId());
        }
        //insert language & cpu & memory,default C/C++:1s,64kb.JAVA:2s,64*1024=>64mb
        String problemParams = records.getProblemParams();
        List<ProblemParams> params = JSON.parseArray(problemParams,ProblemParams.class);
        map = new HashMap<>();
        List<Language> languages = backendService.queryLanguages();
        for(Language item : languages){
            map.put(item.getLanguageName(),item.getLanguageId());
        }
        for(int i = 0;i < params.size();i++){
            params.get(i).setProblemId(records.getProblemId());
            params.get(i).setLanguageId((Integer) map.get(params.get(i).getLanguageName()));
        }
        backendService.addLanguagesParam(params);
        map.clear();
        map.put("success","题目创建成功!");
        System.out.println(records);
        return map;
    }

    @RequestMapping(value = "/getWebsite")
    public Map<String, Object> getWebsite() throws Exception {
        Map<String,Object> map = new HashMap<>();
        BackendConfig config = backendService.queryBackendConfig();
        map.put("name",config.getName());
        map.put("permission",config.isRegisterPermission());
        map.put("footer",config.getFooter());
        map.put("success","保存成功");
        return map;
    }

    @RequestMapping(value = "/saveWebsite")
    public Map<String, Object> saveWebsite(@RequestParam("name")String name,
                                           @RequestParam("registerPermission")boolean permission,
                                           @RequestParam("footer")String footer) throws Exception {
        Map<String,Object> map = new HashMap<>();
        BackendConfig config = new BackendConfig();
        config.setName(name);
        config.setFooter(footer);
        config.setRegisterPermission(permission);
        backendService.modifyBackendConfig(config);
        map.put("success","保存成功");
        return map;
    }

    @RequestMapping(value = "/getServer")
    public Map<String, Object> getServer()throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("status","正常运行");
        map.put("hostname", OSUtils.getHostName());
        map.put("cpu",Runtime.getRuntime().availableProcessors());
        map.put("cpuUsage",OSUtils.cpuUsage());
        map.put("memoryUsage",OSUtils.memoryUsage());
        map.put("judgingTask",commonService.querySubmitsCountsByResult("Pending"));
        map.put("judgedTask",commonService.querySubmitsCounts(null));
        map.put("success","获取服务器信息成功");
        return map;
    }

    @RequestMapping(value = "getSmtp")
    public Map<String,Object> getSmtp(){
        Map<String,Object> map = new HashMap<>();
        map.put("email",javaMailSender.getUsername());
        map.put("host",javaMailSender.getHost());
        map.put("success","获取smtp信息成功");
        return map;
    }

    @RequestMapping(value = "saveSmtp")
    public Map<String,Object> saveSmtp(@RequestParam("email")String email,
                                       @RequestParam("emailPass")String password,
                                       @RequestParam("host")String host) throws Exception {
        Map<String,Object> map = new HashMap<>();
        BackendConfig config = new BackendConfig();
        config.setEmail(email);
        config.setEmailPass(password);
        config.setHost(host);
        javaMailSender.setHost(host);
        javaMailSender.setPassword(password);
        javaMailSender.setUsername(email);
        backendService.modifyBackendConfig(config);
        map.put("success","保存smtp信息成功");
        return map;
    }

    @RequestMapping(value = "/user/{page}",method = RequestMethod.GET)
    public Map<String,Object> findUsers(@PathVariable("page")int page) throws Exception {
        Map<String,Object> map = new HashMap<>();
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(page);
        pagination.setTotal(userService.queryUsers());
        List<User> users = backendService.queryUsers(pagination);
        map.put("pagination",pagination);
        map.put("users",users);
        return map;
    }

    @RequestMapping(value = "/saveUser",method = RequestMethod.POST)
    public Map<String,Object> saveUser(@RequestParam("userId")Integer userId,
                                       @RequestParam("userGrant")String userGrant,
                                       HttpSession session) throws Exception {
        Map<String,Object> map = new HashMap<>();
        User u = new User();
        u.setUserId(userId);
        u.setUserGrant(userGrant);
        userService.modifyUser(u);
        map.put("success","修改成功");
        return map;
    }

    @RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
    public Map<String,Object> deleteUser(@RequestParam("userId")Integer userId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        System.out.println(userId);
        backendService.dropUserByUserId(userId);
        map.put("success","删除成功");
        return map;
    }


    @RequestMapping(value = "/notice/{page}")
    public Map<String,Object> findNotices(@PathVariable("page")Integer page)throws Exception{
        Map<String,Object> map = new HashMap<>();
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(page);
        pagination.setTotal(backendService.queryNoticeCount());
        List<Notice> notices = backendService.queryNotices(pagination);
        map.put("pagination",pagination);
        map.put("notices",notices);
        map.put("success","获取公告成功");
        return map;
    }

    @RequestMapping(value = "/addNotice")
    public Map<String,Object> findNotices(Notice notice,HttpSession session)throws Exception {
        Map<String,Object> map = new HashMap<>();
        User user = (User)session.getAttribute("identity");
        Date date = new Date();
        notice.setCreateTime(date);
        notice.setCurrentUpdateTime(date);
        notice.setIsShow(true);
        notice.setCreateUserId(user.getUserId());
        backendService.addNotice(notice);
//        System.out.println(notice);
        map.put("success","添加成功");
        return map;
    }

    @RequestMapping(value = "/changeShowNotice")
    public Map<String,Object> changeShowNotice(@RequestParam("noticeId")Integer noticeId,
                                          @RequestParam("show")Boolean show)throws Exception {
        Map<String, Object> map = new HashMap<>();
        Notice notice = new Notice();
        notice.setIsShow(show);
        notice.setNoticeId(noticeId);
        backendService.modifyNotice(notice);
        map.put("success","ok");
        return map;
    }

    @RequestMapping(value = "/saveNotice")
    public Map<String,Object> saveNotices(Notice notice)throws Exception {
        Map<String, Object> map = new HashMap<>();
        notice.setCurrentUpdateTime(new Date());
        backendService.modifyNotice(notice);
        map.put("success","ok");
        return map;

    }

    @RequestMapping(value = "/deleteNotice")
    public Map<String,Object> deleteNotices(@RequestParam("id")Integer id)throws Exception {
        Map<String, Object> map = new HashMap<>();
        backendService.dropNotice(id);
        map.put("success","已成功删除"+id+"号公告");
        return map;
    }

    @RequestMapping(value = "/addContest")
    public Map<String,Object> addContest(Contest contest,HttpSession session)throws Exception{
        Map<String, Object> map = new HashMap<>();
        User user = (User)session.getAttribute("identity");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String start = df.format(contest.getStartTime());
        String now =  df.format(date);
        if(start.compareTo(now) > 0){
            if(contest.getContestCreator() == null)
                contest.setContestCreator(user.getUserName());
            if(contest.getCreateTime() == null)
                contest.setCreateTime(date);
            backendService.addContest(contest);
            map.put("success","添加新比赛成功！");
        }else{
            map.put("error","比赛开始日期错误，在当前日期之前！");
        }
        System.out.println(contest);
        return map;
    }



}
