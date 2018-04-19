package com.jsut.ecoder.po;

import lombok.Data;
import org.omg.CORBA.INTERNAL;

@Data
public class JudgeResult {

    private Integer submitId;
    private Integer status;
    private Integer signal;
    private Integer cpu_time;
    private Integer real_time;
    private Integer memory;
    private String judge_result;
    private String judge_type;//judging? compiling?
    private String logs;
    private String output_user;
    private String output_ans;

}
