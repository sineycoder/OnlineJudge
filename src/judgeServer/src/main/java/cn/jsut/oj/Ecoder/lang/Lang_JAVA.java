package cn.jsut.oj.Ecoder.lang;

import cn.jsut.oj.Ecoder.po.JudgeBody;
import com.sun.org.apache.bcel.internal.generic.LAND;

public class Lang_JAVA extends Lang{

//compiling  {compileFile} {command(src)} {command} {error Filename}
    //judging {judgeFile} {start seccomp？1(start):0} {cpu(second)} {cpu(ms)} {size of memories} {command(src)} {command} {stdin path} {stdout path} {error Filename}

    public Lang_JAVA(String path){
        super.setCompileCommand(Lang.getPath("JAVA")+"/javac javac "+path+"/Main.java -d "+path+" "+path+"/error.txt");
    }

    @Override
    public void setExecCommand(String JUDGE_PATH, String path, JudgeBody body, int index) {
        StringBuffer execCommand = new StringBuffer();
        long memory = body.getRam_limit()/1024;
        execCommand.append(
                JUDGE_PATH+" "+
                String.valueOf(0)+" "+
                String.valueOf(body.getCpu_limit()/1000)+" "+
                String.valueOf(body.getCpu_limit()%1000)+" "+
                String.valueOf(-1)+" "+
                Lang.getPath("JAVA")+"/java"+" "+
                "java -Xmx" + memory+"m -Xms"+memory+"m -cp "+path+" Main"+" "+
                path+"/"+index+".in"+" "+
                path+"/"+index+".out"+" "+
                path+"/"+"error.txt"
        );

        super.setExecCommand(execCommand.toString());
    }

    @Override
    public String getCompileCommand() {
        return super.getCompileCommand();
    }

    @Override
    public String getExecCommand() {
        return super.getExecCommand();
    }

}
