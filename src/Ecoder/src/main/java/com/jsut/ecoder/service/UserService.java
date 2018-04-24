package com.jsut.ecoder.service;

import com.jsut.ecoder.po.*;

import java.util.List;

public interface UserService {


    Integer queryUserByUserName(String userName)throws Exception;

    void modifyUserInfo(User user)throws Exception;

    void modifyUserEmail(User user)throws Exception;

    void modifyLastLoginTime(User user)throws Exception;

    void modifyUserPassword(User user)throws Exception;

    User queryUserById(Integer id)throws Exception;

    void addUser(User user)throws Exception;

    void modifyUser(User user) throws Exception;

    int isExsitUser(String userName) throws Exception;

    User queryUserByuserNameAndPwd(User user)throws Exception;

    int querySubmissionByResult(int id,String result)throws Exception;

    List<Result> queryUserProblemsTotals(Pagination pagination,String userName,String type)throws Exception;

    int queryUsers()throws Exception;

    int isExistEmail(String email)throws Exception;

    ProblemRecords queryJudgeProblemByIdAndLanguage(Integer id,String language)throws Exception;

    void addJudgeSubmit(SubmitsRecords submitsRecords)throws Exception;

    void modifyJudgeSubmitResult(Integer id,String result)throws Exception;

    void modifyJudgeSubmit(Integer id,JudgeResult result)throws Exception;

}
