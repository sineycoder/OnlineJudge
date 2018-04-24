package com.jsut.ecoder.po;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import java.util.Date;

@Data
public class Contest {

    private Integer id;
    private Notice notice;
    private String contestName;
    private String contestDescription;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String contestPassword;
    private String contestStatus;
    private Boolean contestVisible;
    private String contestCreator;
    private Integer total;

}
