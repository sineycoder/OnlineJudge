package com.jsut.ecoder.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ProblemRecords implements Serializable{

    private String status;
    private Integer problemId;
    private String problemTitle;
    private String problemDescription;
    private String problemInputDescription;
    private String problemOutputDescription;
    private Integer problemCpuTime;
    private Integer problemMemory;
    private String problemSample;
    private String problemHint;
    private String problemSource;
    private String problemDifficulty;
    private boolean problemIsShow;
    private boolean spj;
    private boolean returnErr;
    private String[] tag;
    private String problemAuthor;
    private String problemInput;
    private String problemOutput;
    private Date problemCreateTime;
    private Integer submitTotal;
    private Double ratio;
    private List<Result> result;
    private List<Tag> tags;
    private List<ProblemParams> params;
    private String problemParams;

}
