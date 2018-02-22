package cn.jsut.oj.Ecoder.Tools;


import cn.jsut.oj.Ecoder.lang.Lang;
import cn.jsut.oj.Ecoder.lang.Lang_C;
import cn.jsut.oj.Ecoder.lang.Lang_CPP;
import cn.jsut.oj.Ecoder.lang.Lang_JAVA;
import cn.jsut.oj.Ecoder.po.JudgeBody;
import cn.jsut.oj.Ecoder.po.JudgeResult;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Helper {
    private static final String JUDGE_LOGS = System.getenv("JUDGE_LOGS");
    private static final String JUDGE_QUEUE_LOGS = System.getenv("JUDGE_QUEUE_LOGS");
    private static final Helper helper = new Helper();

    public static Helper getHelper(){
        return helper;
    }

    public void initJudge(String path,String filename){
        try{
            InputStream compile = Helper.class.getResourceAsStream(filename);
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
            outputLogs("func:initJudge -> "+e.getMessage());
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
            outputLogs("func:createNewFile -> "+e.getMessage());
        }
    }

    public void outputLogs(String content){
        String path = content.contains("[ OK ]")?JUDGE_QUEUE_LOGS:JUDGE_LOGS;//包含OK为queue日志
        BufferedWriter OUT = null;
        SimpleDateFormat sdf = null;
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            OUT = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path),true),"UTF-8"));
            String now = sdf.format(new Date());
            OUT.write(now + "  :  "+content+"\n");
            OUT.close();
        } catch (Exception e) {
            e.printStackTrace();
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
            outputLogs("func:deleteAll -> "+e.getMessage());
        }
    }

    public Lang judgeLanguage(JudgeBody body,File f) throws Exception{
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
            outputLogs("func:createMain -> " + e.getMessage());
        }
    }

    public JudgeResult compiling(Lang lang,String COMPILE_FILE, File file, String type) {
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
                result.setCpu_time(Double.parseDouble(map.get("cpu_time")));
                result.setReal_time(Double.parseDouble(map.get("real_time")));
                return result;
            }

        }catch (Exception e){
            outputLogs(e.getMessage());
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
            outputLogs("func:inputLogs -> " + e.getMessage());
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
            outputLogs("func:writeCase -> "+e.getMessage());
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
            result.setJudge_type(type);
            result.setSignal(signal);
            result.setStatus(status);
            result.setCpu_time(cpu_time);
            result.setReal_time(real_time);
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
            outputLogs("func:judging -> " + e.getMessage());
        }
        return result;
    }
}
