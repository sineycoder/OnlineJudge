package cn.jsut.oj.Ecoder.lang;

import cn.jsut.oj.Ecoder.Tools.Helper;
import cn.jsut.oj.Ecoder.po.JudgeBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Lang {
    private static final String C_PATH = setPath("gcc");
    private static final String CPP_PATH = setPath("g++");
    private static final String JAVA_PATH = setPath("java");


    private static String setPath(String lang){
        try {
            Process process = Runtime.getRuntime().exec("which "+lang);
            BufferedReader rd = new BufferedReader(new InputStreamReader(process.getInputStream(),"UTF-8"));
            String s = null;
            if((s = rd.readLine()) != null){
                rd.close();
                if(process.isAlive())process.destroy();
                return s.substring(0,s.lastIndexOf("/"));
            }
        } catch (IOException e) {
            Helper.getHelper().outputLogs("setLanguagePath Error -> "+e.getMessage());
        }
        return null;
    }
    public static String getPath(String lang){
        switch (lang){
            case "C":return C_PATH;
            case "C++":return CPP_PATH;
            case "JAVA":return JAVA_PATH;
            default:return null;
        }
    }
    private String compileCommand;
    private String execCommand;
    public String getCompileCommand() {
        return compileCommand;
    }

    public void setCompileCommand(String compileCommand) {
        this.compileCommand = compileCommand;
    }

    public String getExecCommand() {
        return execCommand;
    }

    public void setExecCommand(String execCommand) {
        this.execCommand = execCommand;
    }

    public void setExecCommand(String JUDGE_PATH,String path, JudgeBody body, int index){

        StringBuffer execCommand = new StringBuffer();
        execCommand.append(
                JUDGE_PATH+" "+
                1+" "+
                body.getCpu_limit()/1000+" "+
                body.getCpu_limit()%1000+" "+
                body.getRam_limit()*1024+" "+
                path+"/main "+path+"/main"+" "+
                path+"/"+index+".in"+" "+
                path+"/"+index+".out"+" "+
                path+"/"+"error.txt"
        );
        this.execCommand = execCommand.toString();
    }
    @Override
    public String toString() {
        return "Lang{" +
                "compileCommand='" + compileCommand + '\'' +
                ", execCommand='" + execCommand + '\'' +
                '}';
    }
}
