package com.jsut.ecoder.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class SubmitsRecords implements Serializable {

    private Integer id;
    private Integer userId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date submitDate;
    private String userName;
    private Integer problemId;
    private String result;
    private Integer cpuTime;
    private Integer memorySize;
    private String language;
    private String codes;

}
