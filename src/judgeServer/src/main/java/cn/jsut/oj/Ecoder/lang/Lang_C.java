package cn.jsut.oj.Ecoder.lang;

import cn.jsut.oj.Ecoder.po.JudgeBody;
import sun.text.resources.iw.FormatData_iw_IL;

import javax.sql.rowset.JdbcRowSet;
import java.io.File;

public class Lang_C extends Lang{

    //compiling  {compileFile} {command(src)} {command} {error Filename}
    //judging {judgeFile} {start seccomp？1(start):0} {cpu(second)} {cpu(ms)} {size of memories} {command(src)} {command} {stdin path} {stdout path} {error Filename}
    public Lang_C(String path){
        super.setCompileCommand(Lang.getPath("C")+"/gcc gcc -DONLINE_JUDGE -O2 -w -std=c99 " + path + "/main.c" +" -lm -o " + path + "/main "+path+"/error.txt ");
    }

    @Override
    public void setCompileCommand(String compileCommand) {
        super.setCompileCommand(compileCommand);
    }




    public String getCompileCommand() {
        return super.getCompileCommand();
    }

    public String getExecCommand() {
        return super.getExecCommand();
    }


}
