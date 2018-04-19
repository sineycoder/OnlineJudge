package com.jsut.ecoder.tools;

import com.alibaba.fastjson.JSON;
import com.jsut.ecoder.controller.EcoderCommonController;
import com.jsut.ecoder.judger.lang.Lang;
import com.jsut.ecoder.judger.lang.Lang_C;
import com.jsut.ecoder.judger.lang.Lang_CPP;
import com.jsut.ecoder.judger.lang.Lang_JAVA;
import com.jsut.ecoder.po.JudgeBody;
import com.jsut.ecoder.po.JudgeResult;
import com.jsut.ecoder.po.ProblemRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class JudgerHelper {

    private final Logger logger =  LoggerFactory.getLogger(EcoderCommonController.class);
    private static final String JUDGE_LOGS = System.getenv("JUDGE_LOGS");
    private static final String JUDGE_QUEUE_LOGS = System.getenv("JUDGE_QUEUE_LOGS");
    private static final JudgerHelper helper = new JudgerHelper();

    public static JudgerHelper getHelper(){
        return helper;
    }

    public void initJudge(String path,String filename){
        try{
            InputStream compile = JudgerHelper.class.getResourceAsStream(filename);
            FileOutputStream out = new FileOutputStream(path);
            byte[] by = new byte[1024];
            int index = -1;
            while((index = compile.read(by))!=-1){
                out.write(by,0,index);
            }
            compile.close();
            out.close();
            Runtime.getRuntime().exec("chmod +x "+path);
        }catch (Exception e){
            logger.error("class[ "+ this.getClass().getName() +" ], function[ initJudge -> "+e.getMessage()+" ]");
        }

    }
    public void mkdir(File f){
        if(!f.exists())f.mkdirs();
    }

    public void createNewFile(File f) {
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
        }catch(Exception e){
            logger.error("class[ "+ this.getClass().getName() +" ], function[ createNewFile -> "+e.getMessage()+" ]");
        }
    }

    public void deleteAll(File file) {
        try{
            File[] listFiles = file.listFiles();
            for(int i = 0;i<listFiles.length;i++){
                if(listFiles[i].isDirectory())
                    deleteAll(listFiles[i]);
                listFiles[i].delete();
            }
            file.delete();
        }catch(Exception e){
            logger.error("class[ "+ this.getClass().getName() +" ], function[ deleteAll -> "+e.getMessage()+" ]");
        }
    }

    public Lang judgeLanguage(JudgeBody body, File f) throws Exception{
        Lang lang = null;
        switch (body.getJudgeLanguage()){
            case "C":lang = new Lang_C(f.toString());createMain(body,new File(f.toString()+"/main.c"));break;
            case "C++":lang = new Lang_CPP(f.toString());createMain(body,new File(f.toString()+"/main.cpp"));break;
            case "JAVA": lang = new Lang_JAVA(f.toString());createMain(body,new File(f.toString()+"/Main.java"));break;
            default:throw new Exception("func:judgeLanguage -> not this Language");
        }
        return lang;
    }


    public void createMain(JudgeBody body, File file) {
        BufferedWriter OUT = null;
        try {
//            System.out.println(file.toString());
            OUT = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
            OUT.write(body.getCodes());
            OUT.close();
        } catch (Exception e) {
            logger.error("class[ "+ this.getClass().getName() +" ], function[ createMain -> "+e.getMessage()+" ]");
        }
    }

    public JudgeResult compiling(Lang lang, String COMPILE_FILE, File file, String type) {
        try{

            //compiling
            String compiling = COMPILE_FILE + " "+lang.getCompileCommand();
//			System.out.println(compiling);
            Process process = Runtime.getRuntime().exec(compiling);
            int status = process.waitFor();
//			System.out.println("process:"+status);
            if(status != 0) throw new Exception("func:compile -> Compiling Error");

            //return result
            BufferedReader rd = new BufferedReader(new InputStreamReader(process.getInputStream()));
            Map<String,String> map = new HashMap<>();

            String line = null;
            while((line = rd.readLine())!=null){
                String[] param = line.trim().split("=");
                map.put(param[0],param[1]);
            }
            rd.close();
            status = Integer.parseInt(map.get("status"));
            int signal = Integer.parseInt(map.get("signal"));
            if(status != 0 || signal != 0){
                String logs = inputLogs(new File(file.toString()+"/error.txt"));
                if(logs==null)throw new Exception("func:compiling -> logs obj is null");
                JudgeResult result = new JudgeResult();
                result.setLogs(logs);
                result.setJudge_type(type);
                result.setJudge_result("CE");//compiling error
                result.setSignal(signal);
                result.setStatus(status);
                result.setCpu_time((int) Double.parseDouble(map.get("cpu_time")));
                result.setReal_time((int) Double.parseDouble(map.get("real_time")));
                result.setMemory((int) (Double.parseDouble(map.get("memories"))/1024));
                return result;
            }
        }catch (Exception e){
            logger.error("class[ "+ this.getClass().getName() +" ], function[ compiling -> "+e.getMessage()+" ]");
        }
        return null;
    }

    private String inputLogs(File file) {
        BufferedReader IN = null;
        try {
            IN = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            String s = null;
            StringBuffer SB = new StringBuffer();
            while((s = IN.readLine()) != null)SB.append(s+"\n");
            IN.close();
            return SB.toString();
        } catch (Exception e) {
            logger.error("class[ "+ this.getClass().getName() +" ], function[ inputLogs -> "+e.getMessage()+" ]");
        }
        return null;
    }

    public void writeCase(File file, JudgeBody body, int index) {
        BufferedWriter OUT = null;
        try{
            OUT = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                                new File(file.toString()+"/"+"1.in")),"UTF-8"));
            OUT.write(body.getInput().get(index));
            OUT.close();
        }catch (Exception e){
            logger.error("class[ "+ this.getClass().getName() +" ], function[ writeCase -> "+e.getMessage()+" ]");
        }
    }


    public JudgeResult judging(Lang lang, String JUDGE_PATH, File file, JudgeBody body, int index,String type) {
        JudgeResult result = null;
        try {
            //judging
            int ind = 1;//只有1.in和1.out
            lang.setExecCommand(JUDGE_PATH,file.toString(),body,ind);
            String command = lang.getExecCommand();
//            System.out.println("juding command="+command);
            Process process = Runtime.getRuntime().exec(command);
            int status = process.waitFor();
            if(status != 0) throw new Exception("func:judging -> Judging Error");

            //return result
            BufferedReader rd = new BufferedReader(new InputStreamReader(process.getInputStream()));
            Map<String,String> map = new HashMap<>();
            result = new JudgeResult();
            String line = null;
            while((line = rd.readLine()) != null){
                String param[] = line.trim().split("=");
                map.put(param[0],param[1]);
//                System.out.println(param[0]+" "+param[1]);
            }
            rd.close();

            status = Integer.parseInt(map.get("status"));
            int signal = Integer.parseInt(map.get("signal"));
            double cpu_time = Double.parseDouble(map.get("cpu_time"));
            double real_time = Double.parseDouble(map.get("real_time"));
            double memory = Double.parseDouble(map.get("memories"));
            result.setJudge_type(type);
            result.setSignal(signal);
            result.setStatus(status);
            result.setCpu_time((int) cpu_time);
            result.setReal_time((int) real_time);
            result.setMemory((int) (memory/1024));
            if(status == 0){
                if(signal == 11){
                    //内存溢出 Memory Limit Exceeded
                    result.setJudge_result("MLE");
                }else if(signal == 26 || signal == 14){
                    //超时 Time Limit Exceeded
                    result.setJudge_result("TLE");
                }else if(signal > 0){
                    //其他情况运行错误 Runtime Error
                    result.setJudge_result("RE");
                }else{
                    //完全正常
                    rd = new BufferedReader(new InputStreamReader(
                            new FileInputStream(file.toString()+"/"+ind+".out"),"UTF-8"));
                    String out = null;
                    StringBuffer SB = new StringBuffer();
                    while((out = rd.readLine()) != null){
                        SB.append(out+"\n");
                    }
                    rd.close();
                    out = body.getOutput().get(index);
                    String ans = SB.toString().trim();
                    if(out.equals(ans)){
                        result.setJudge_result("AC");
                    }else{
                        result.setJudge_result("WA");
                        result.setOutput_user(ans);
                        result.setOutput_ans(out);
                    }
                }
            }else{
                //判题系统出错 System Error
                rd = new BufferedReader(new InputStreamReader(
                        new FileInputStream(file.toString()+"/"+"error.txt"),"UTF-8"));
                String out = null;
                StringBuffer SB = new StringBuffer();
                while((out = rd.readLine()) != null){
                    SB.append(out+"\n");
                }
                rd.close();
                if(SB.length()>0){
                    if(SB.toString().contains("OutOfMemoryError"))
                        result.setJudge_result("MLE");
                    else{
                        result.setJudge_result("SE");
                    }
                    result.setLogs(SB.toString());
                }else{
                    result.setJudge_result("SE");
                }
            }
//            System.out.println("after judging = "+result);
        } catch (Exception e) {
            logger.error("class[ "+ this.getClass().getName() +" ], function[ judging -> "+e.getMessage()+" ]");
        }
        return result;
    }

    public JudgeBody returnJudgeBody(ProblemRecords problemRecords,
                                     String language,
                                     String content)throws Exception{
        List<String> in = JSON.parseObject(problemRecords.getProblemInput(), List.class);
        List<String> out = JSON.parseObject(problemRecords.getProblemOutput(),List.class);
        JudgeBody judgeBody = new JudgeBody();
        judgeBody.setInput(in);
        judgeBody.setOutput(out);
        judgeBody.setSpecialJudge(false);
        judgeBody.setJudgeLanguage(language);
        judgeBody.setCodes(content);
        judgeBody.setCpu_limit(problemRecords.getParams().get(0).getProblemCpuTime());
        judgeBody.setRam_limit(problemRecords.getParams().get(0).getProblemMemory());
        return judgeBody;
    }


}
