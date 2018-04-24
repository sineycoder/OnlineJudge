package com.jsut.ecoder.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * judge content
 */
@Data
public class JudgeBody implements Serializable{

    private Integer submitId;
    private String judgeId;
    private String judgeLanguage;
    private String codes;
    private boolean specialJudge;
    private List<String> input;
    private List<String> output;
    private long cpu_limit;
    private long ram_limit;

}
