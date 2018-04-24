package com.jsut.ecoder.po;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:
 * @date:2018/1/20 16:26
 * @version:
 * @copyright:
 */
@Data
public class User implements Serializable {

    private Integer userId;
    private String userName;
    private Integer isChange;
    private String userPwd;
    private String userEmail;
    private String userGrant;
    private String infoGender;
    private String infoSchoolName;
    private String infoSchoolNum;
    private String infoBlog;
    private String infoMotto;
    private String infoGithub;
    private String infoPhone;
    private String infoRealName;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastLogin;
    private String captcha;

}
