package cn.jsut.oj.Ecoder.lang;

import cn.jsut.oj.Ecoder.po.JudgeBody;

public class Lang_CPP extends Lang{

//compiling  {compileFile} {command(src)} {command} {error Filename}
    //judging {judgeFile} {start seccomp？1(start):0} {cpu(second)} {cpu(ms)} {size of memories} {command(src)} {command} {stdin path} {stdout path} {error Filename}

    public Lang_CPP(String path){
        super.setCompileCommand(Lang.getPath("C++")+"/g++ g++ -DONLINE_JUDGE -O2 -w -std=c++11 " + path + "/main.cpp" +" -lm -o " + path + "/main "+path+"/error.txt ");
    }



    public String getExecCommand() {
        return super.getExecCommand();
    }

    @Override
    public String getCompileCommand() {
        return super.getCompileCommand();
    }


}
