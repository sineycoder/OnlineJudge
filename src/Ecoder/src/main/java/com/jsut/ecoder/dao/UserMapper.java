package com.jsut.ecoder.dao;


import com.jsut.ecoder.po.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    void insertUser(User user)throws Exception;

    void updateUserChange(User user)throws Exception;

    void deleteUser(int id)throws Exception;

    User selectUserById(int id)throws Exception;

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

    void updateJUdgeSubmit(@Param("id")Integer id, @Param("result")JudgeResult result)throws Exception;

}
