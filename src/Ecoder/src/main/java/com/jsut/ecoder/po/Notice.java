package com.jsut.ecoder.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Notice {
    private Integer noticeId;
    private Integer createUserId;
    private String title;
    private String content;
    private Boolean isShow;
    private String createUserName;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date currentUpdateTime;
}
