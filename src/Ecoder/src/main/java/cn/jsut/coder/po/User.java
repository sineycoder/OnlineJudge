package cn.jsut.coder.po;

import java.io.Serializable;

/**
 * @author:
 * @date:2018/1/20 16:26
 * @version:
 * @copyright:
 */
public class User implements Serializable {
    private String userId;
    private String userName;
    private String userPwd;
    private String userEmail;
    private String userGrant;

    @Override
    public String toString() {
        return "UserMapper{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userGrant='" + userGrant + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserGrant() {
        return userGrant;
    }

    public void setUserGrant(String userGrant) {
        this.userGrant = userGrant;
    }
}
