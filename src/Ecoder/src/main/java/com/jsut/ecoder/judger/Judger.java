package com.jsut.ecoder.judger;

import com.alibaba.fastjson.JSON;
import com.jsut.ecoder.controller.EcoderCommonController;
import com.jsut.ecoder.judger.lang.Lang;
import com.jsut.ecoder.po.JudgeBody;
import com.jsut.ecoder.po.JudgeResult;
import com.jsut.ecoder.service.UserService;
import com.jsut.ecoder.tools.JudgerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author:
 * @date:2018/1/20 14:15
 * @version:
 * @copyright:
 */
@Component
public class Judger {

    private final Logger logger =  LoggerFactory.getLogger(EcoderCommonController.class);
    private static final String JUDGE_PATH = System.getenv("JUDGE_PATH");
    private static final String JUDGER_PATH = JUDGE_PATH + "/judger";
    private static final String JUDGE_FILE_PATH = JUDGE_PATH + "/judgeFile";
    private static final String JUDGE_LOGS = JUDGE_PATH + "/error.log";
    private static final String COMPILE_FILE = JUDGE_FILE_PATH + "/compile";
    private static final String JUDGER_FILE = JUDGE_FILE_PATH + "/judge";
    @Autowired
    private UserService userService;

    public Judger(){
        logger.info("JUDGE_PATH [->] "+JUDGE_PATH);
        logger.info("JUDGER_PATH [->] "+JUDGER_PATH);
        logger.info("JUDGE_FILE_PATH [->] "+JUDGE_FILE_PATH);
        logger.info("JUDGE_LOGS [->] "+JUDGE_LOGS);
        logger.info("COMPILE_FILE [->] "+COMPILE_FILE);
        logger.info("JUDGER_FILE [->] "+JUDGER_FILE);
    }

    static{
        /**
         * create judge directory,if not exist,create all.
         */
        File file = new File(JUDGE_PATH);
        if (!file.exists()) file.mkdirs();
        file = new File(JUDGER_PATH);
        if (!file.exists()) file.mkdirs();
        file = new File(JUDGE_FILE_PATH);
        if (!file.exists()) file.mkdirs();
        JudgerHelper.getHelper().initJudge(COMPILE_FILE,"/compile");
        JudgerHelper.getHelper().initJudge(JUDGER_FILE,"/judge");
        JudgerHelper.getHelper().createNewFile(new File(JUDGE_LOGS));
    }

    public JudgeResult work(String obj) throws Exception {
        if(JUDGE_PATH == null){
            logger.error("your judger path is not correct !");
            return null;
        }else{
            JudgeBody body = JSON.parseObject(obj,JudgeBody.class);
            logger.info("**************[ "+ body.getJudgeId() +" is starting judge ]**************");
            Thread.sleep(5000);
            userService.modifyJudgeSubmitResult(body.getSubmitId(),"Compiling");
            JudgeResult result = compile(body);
            if(result != null){
                JudgerHelper.getHelper().deleteAll(new File(JUDGER_PATH + "/" + body.getJudgeId()));
                logger.info("**************[ "+ body.getJudgeId() +" compile error & delete ]**************");
                System.out.println(result);
                userService.modifyJudgeSubmit(body.getSubmitId(),result);
                return result;//return compile result
            }
            Thread.sleep(5000);
            userService.modifyJudgeSubmitResult(body.getSubmitId(),"Judging");
            result = judge(body);
            Thread.sleep(5000);
            System.out.println(result);
            userService.modifyJudgeSubmit(body.getSubmitId(),result);
            return result;//return result
        }
    }

    /**
     * @param content JudgeBody
     * @return CompileTest Result
     */
    public String compileJudge(String content){
        JudgeBody body = JSON.parseObject(content,JudgeBody.class);
        JudgeResult result = compile(body);
        File file = new File(JUDGER_PATH + "/" + body.getJudgeId());
        if(result != null)return JSON.toJSONString(new JudgeResult[]{result});
        return null;
    }

    /**
     *
     * @return a JudgeResult object.if success,return null; or ,return this obj.
     */
    private JudgeResult compile(JudgeBody body){
        //创建目标id的文件夹目录
        File file = new File(JUDGER_PATH + "/" + body.getJudgeId());
        JudgeResult result = null;
        try {
            if (file.exists()) JudgerHelper.getHelper().deleteAll(file);
            JudgerHelper.getHelper().mkdir(file);

            Lang lang = JudgerHelper.getHelper().judgeLanguage(body,file);//创建对应语言的类
//			System.out.println(lang);

            result = JudgerHelper.getHelper().compiling(lang,COMPILE_FILE,file,"compiling");//如果没错误，则返回null
//			System.out.println("compiling result = "+result);
        } catch (Exception e) {
            logger.error("class[ "+ this.getClass().getName() +" ], function[ compiling -> "+e.getMessage()+" ]");
        }
        return result;
    }

    public JudgeResult judge(JudgeBody body){
        JudgeResult result = null;
        File file = null;
        try {
            //创建目标id的文件夹目录
            file = new File(JUDGER_PATH + "/" + body.getJudgeId());
            Lang lang = null;
            int time = 0, memory = 0;
            if(!body.isSpecialJudge()){
                int length = body.getInput().size();
                lang = JudgerHelper.getHelper().judgeLanguage(body,file);//创建对应语言的类
                //start judging
                for(int i = 0;i<length;i++){
                    JudgerHelper.getHelper().writeCase(file,body,i);
                    result = JudgerHelper.getHelper().judging(lang,JUDGER_FILE,file,body,i,"judging");
                    if(!"AC".equals(result.getJudge_result())){
                        return result;
                    }
                    time += result.getCpu_time();
                    memory += result.getMemory();
                }
                result.setCpu_time(time / length);
                result.setMemory(memory / length);
            }else{
                //special judge

            }
        }catch (Exception e){
            logger.error("class[ "+ this.getClass().getName() +" ], function[ judging -> "+e.getMessage()+" ]");
        }finally {
            if(file != null){
                JudgerHelper.getHelper().deleteAll(file);
            }
        }

        return result;
    }

}
