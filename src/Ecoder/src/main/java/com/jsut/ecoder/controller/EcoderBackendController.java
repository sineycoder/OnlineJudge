package com.jsut.ecoder.controller;

import com.alibaba.fastjson.JSON;
import com.jsut.ecoder.po.*;
import com.jsut.ecoder.service.BackendService;
import com.jsut.ecoder.tools.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/backend")
public class EcoderBackendController {

    @Autowired
    private Tools tools;

    @Autowired
    private BackendService backendService;

    @RequestMapping("/upload")
    public Map<String,Object> upload(@RequestParam("file") MultipartFile file, HttpSession session)throws Exception{
        Map<String,Object> map = new HashMap<>();
        User user = (User)session.getAttribute("identity");
        if(user != null){
            ProblemRecords records = new ProblemRecords();
            tools.unZipAndGetCase(file,"/temp/",map,records);
            if(map.get("error") == null){
                session.setAttribute("record",records);
                System.out.println(records);
            }
        }
        return map;
    }

    @RequestMapping("/createProblem")
    public Map<String,Object> createProblem(ProblemRecords records,HttpSession session) throws Exception {
        Map<String,Object> map = new HashMap<>();
        User user = (User)session.getAttribute("identity");
        if(user != null){
            ProblemRecords record = (ProblemRecords) session.getAttribute("record");
            records.setProblemInput(record.getProblemInput());
            records.setProblemOutput(record.getProblemOutput());
            records.setProblemCreateTime(new Date());
            records.setProblemAuthor(user.getUserName());
            backendService.addNewProblem(records);
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
            map.put("success","create problem successfully !");
            System.out.println(records);
        }else{
            map.put("error","create error !");
        }
        return map;
    }

}
