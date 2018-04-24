package com.jsut.ecoder.service.impl;

import com.jsut.ecoder.dao.UserMapper;
import com.jsut.ecoder.po.*;
import com.jsut.ecoder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer queryUserByUserName(String userName) throws Exception {
        return userMapper.selectUserByUserName(userName);
    }

    @Override
    public void modifyUserInfo(User user) throws Exception {
        userMapper.updateUserInfo(user);
    }

    @Override
    public void modifyUserEmail(User user) throws Exception {
        userMapper.updateUserEmail(user);
    }

    @Override
    public void modifyLastLoginTime(User user) throws Exception {
        userMapper.updateLastLoginTime(user);
    }

    @Override
    public void modifyUserPassword(User user) throws Exception {
        userMapper.updateUserPassword(user);
    }

    @Override
    public User queryUserById(Integer id) throws Exception {
        return userMapper.selectUserById(id);
    }

    @Override
    public void addUser(User user) throws Exception {
        userMapper.insertUser(user);
    }

    @Override
    public void modifyUser(User user) throws Exception {
        userMapper.updateUser(user);
    }

    @Override
    public int isExsitUser(String userName) throws Exception {
        int res = userMapper.isExistUserByuserName(userName);
        return res;
    }

    @Override
    public User queryUserByuserNameAndPwd(User user) throws Exception {
        User res = userMapper.selectUserByuserNameAndPwd(user);
        return res;
    }

    @Override
    public int querySubmissionByResult(int id, String result) throws Exception {
        return userMapper.selectSubmissionByResult(id,result);
    }

    @Override
    public List<Result> queryUserProblemsTotals(Pagination pagination, String userName, String type) throws Exception {
        int step = pagination.pageSize;
        int size = (pagination.getTotal()-1) / step + 1;
        if(pagination.getCurrentPage() > size)throw new Exception("Error , your page is greater than size !");
        int start = (pagination.getCurrentPage()-1) * step;
        return userMapper.selectUserProblemsTotals(start,step,userName,type);
    }

    @Override
    public int queryUsers() throws Exception {
        return userMapper.selectUsers();
    }

    @Override
    public int isExistEmail(String email) throws Exception {
        return userMapper.isExistEmail(email);
    }

    @Override
    public ProblemRecords queryJudgeProblemByIdAndLanguage(Integer id,String language) throws Exception {
        return userMapper.selectJudgeProblemByIdAndLanguage(id,language);
    }

    @Override
    public void addJudgeSubmit(SubmitsRecords submitsRecords) throws Exception {
        userMapper.insertJudgeSubmit(submitsRecords);
    }

    @Override
    public void modifyJudgeSubmitResult(Integer id, String result) throws Exception {
        userMapper.updateJudgeSubmitResult(id,result);
    }

    @Override
    public void modifyJudgeSubmit(Integer id, JudgeResult result) throws Exception {
        userMapper.updateJudgeSubmit(id,result);
    }

}
