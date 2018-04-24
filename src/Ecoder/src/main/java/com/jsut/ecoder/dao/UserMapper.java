package com.jsut.ecoder.dao;


import com.jsut.ecoder.po.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * @author:
 * @date:2018/1/20 16:25
 * @version:
 * @copyright:
 */
public interface UserMapper {

    @Select("SELECT COUNT(*) FROM web_user WHERE userName = #{userName,jdbcType=VARCHAR}")
    Integer selectUserByUserName(@Param("userName")String userName)throws Exception;

    @Update("UPDATE web_user SET infoRealName=#{infoRealName},infoSchoolName=#{infoSchoolName}," +
            "infoPhone=#{infoPhone},infoGender=#{infoGender},infoBlog=#{infoBlog},infoGithub=#{infoGithub},infoMotto=#{infoMotto}" +
            "WHERE userId = #{userId}")
    void updateUserInfo(User user);

    @Update("UPDATE web_user SET userEmail = #{userEmail} " +
            "WHERE userId = #{userId}")
    void updateUserEmail(User user)throws Exception;

    @Update("UPDATE web_user SET lastLogin=#{lastLogin} WHERE userName=#{userName}")
    void updateLastLoginTime(User user)throws Exception;

    @Update("UPDATE web_user SET userPwd = #{userPwd} " +
            "WHERE userId = #{userId}")
    void updateUserPassword(User user)throws Exception;

    @Select("SELECT * FROM web_user WHERE userId=#{userId}")
    User selectUserById(@Param("userId") Integer id)throws Exception;

    void insertUser(User user)throws Exception;

    void updateUser(User user)throws Exception;

    int isExistUserByuserName(String userName)throws Exception;

    User selectUserByuserNameAndPwd(User user)throws Exception;

    int selectSubmissionByResult(@Param("id") int id,@Param("result")String result)throws Exception;

    List<Result> selectUserProblemsTotals(@Param("start") int start,@Param("step")int step,
                                          @Param("userName")String userName,@Param("type")String type)throws Exception;

    int selectUsers()throws Exception;

    int isExistEmail(@Param("email")String email)throws Exception;

    ProblemRecords selectJudgeProblemByIdAndLanguage(@Param("id")Integer id,
                                                     @Param("language")String language)throws Exception;

    void insertJudgeSubmit(SubmitsRecords submitsRecords)throws Exception;

    void updateJudgeSubmitResult(@Param("id")Integer in,@Param("result")String result)throws Exception;

    void updateJudgeSubmit(@Param("id")Integer id, @Param("result")JudgeResult result)throws Exception;

}
