package com.jsut.ecoder.po;

import lombok.Data;

import java.io.Serializable;

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
    private String userGrant = "0";
    private String infoGender;
    private String infoSchoolName;
    private String infoSchoolNum;
    private String infoBlog;
    private String infoMotto;
    private String infoGithub;
    private String infoPhone;
    private String captcha;

}
