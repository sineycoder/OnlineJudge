package com.jsut.ecoder.po;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import java.util.Date;

@Data
public class Contest {

    private Integer id;
    private Notice notice;
    private String contestName;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private String times;
    private String contestPassword;
    private String contestStatus;
    private Integer contestVisible;
    private Integer total;

}
