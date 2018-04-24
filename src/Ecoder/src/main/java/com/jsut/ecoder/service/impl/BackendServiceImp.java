package com.jsut.ecoder.service.impl;

import com.jsut.ecoder.dao.BackendMapper;
import com.jsut.ecoder.po.*;
import com.jsut.ecoder.service.BackendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BackendServiceImp implements BackendService {

    @Autowired
    BackendMapper backendMapper;

    @Override
    public void addToProblems(Integer problemId) throws Exception {
        backendMapper.insertToProblems(problemId);
    }

    @Override
    public List<ProblemRecords> queryProblemsByContestId(Pagination pagination, Integer id) throws Exception {
        int step = pagination.pageSize;
        int size = (pagination.getTotal()-1) / step + 1;
        if(pagination.getCurrentPage() > size)throw new Exception("Error , 超出搜索的大小!");
        int start = (pagination.getCurrentPage()-1) * step;
        return backendMapper.selectProblemsByContestId(start,step,id);
    }

    @Override
    public void dropContestByContestId(Integer id) throws Exception {
        backendMapper.deleteContestByContestId(id);
    }

    @Override
    public Contest queryContestById(Integer id) throws Exception {
        return backendMapper.selectContestById(id);
    }

    @Override
    public List<Contest> queryContests(Pagination pagination) throws Exception {
        int step = pagination.pageSize;
        int size = (pagination.getTotal()-1) / step + 1;
        if(pagination.getCurrentPage() > size)throw new Exception("Error , 超出搜索的大小!");
        int start = (pagination.getCurrentPage()-1) * step;
        return backendMapper.selectContests(start,step);
    }

    @Override
    public void dropProblemSubmitByProblemId(Integer id) throws Exception {
        backendMapper.deleteProblemSubmitByProblemId(id);
    }

    @Override
    public void dropProblemTagByProblemId(Integer id) throws Exception {
        backendMapper.deleteProblemTagByProblemId(id);
    }

    @Override
    public void dropProblemLanguageByProblemId(Integer id) throws Exception {
        backendMapper.deleteProblemLanguageByProblemId(id);
    }

    @Override
    public void dropProblemById(Integer id) throws Exception {
        backendMapper.deleteProblemById(id);
    }

    @Override
    public List<ProblemParams> querytProblemParamsById(Integer id) throws Exception {
        return backendMapper.selectProblemParamsById(id);
    }

    @Override
    public List<Tag> queryProblemTagsById(Integer id) throws Exception {
        return backendMapper.selectProblemTagsById(id);
    }

    @Override
    public ProblemRecords queryProblemById(Integer id) throws Exception {
        return backendMapper.selectProblemById(id);
    }

    @Override
    public List<ProblemRecords> queryProblems(Pagination pagination) throws Exception {
        int step = pagination.pageSize;
        int size = (pagination.getTotal()-1) / step + 1;
        if(pagination.getCurrentPage() > size)throw new Exception("Error , 超出搜索的大小!");
        int start = (pagination.getCurrentPage()-1) * step;
        return backendMapper.selectProblems(start,step);
    }

    @Override
    public void dropNotice(Integer id) throws Exception {
        backendMapper.deleteNotice(id);
    }

    @Override
    public int queryNoticeCount() throws Exception {
        return backendMapper.selectNoticeCount();
    }

    @Override
    public List<Notice> queryNotices(Pagination pagination) throws Exception {
        int step = pagination.pageSize;
        int size = (pagination.getTotal()-1) / step + 1;
        if(pagination.getCurrentPage() > size)throw new Exception("Error , 超出搜索的大小!");
        int start = (pagination.getCurrentPage()-1) * step;
        return backendMapper.selectNotices(start,step);
    }

    @Override
    public List<Language> queryLanguages() throws Exception {
        return backendMapper.selectLanguages();
    }

    @Override
    public BackendConfig queryBackendConfig() throws Exception {
        return backendMapper.selectBackendConfig();
    }

    @Override
    public List<User> queryUsers(Pagination pagination) throws Exception {
        int step = pagination.pageSize;
        int size = (pagination.getTotal()-1) / step + 1;
        if(pagination.getCurrentPage() > size)throw new Exception("Error , 超出搜索的大小!");
        int start = (pagination.getCurrentPage()-1) * step;
        return backendMapper.selectUsers(start, step);
    }

    @Override
    public void dropUserByUserId(Integer userId) throws Exception {
        backendMapper.deleteUserByUserId(userId);
    }

    @Override
    public void modifyBackendConfig(BackendConfig config) throws Exception {
        backendMapper.updateBackendConfig(config);
    }

    @Override
    public void addLanguagesParam(List<ProblemParams> params) throws Exception {
        backendMapper.insertLanguagesParam(params);
    }


    @Override
    public void addNewProblem(ProblemRecords records) throws Exception {
        backendMapper.insertNewProblem(records);
    }

    @Override
    public Integer queryIdByTagName(String tagName) throws Exception {
        return backendMapper.selectIdByTagName(tagName);
    }

    @Override
    public void addTag(Tag tag) throws Exception {
        backendMapper.insertTag(tag);
    }

    @Override
    public void addTagProblem(Integer tagId, Integer problemId) throws Exception {
        backendMapper.insertTagProblem(tagId,problemId);
    }

    @Override
    public void addNotice(Notice notice) throws Exception {
        backendMapper.insertNotice(notice);
    }

    @Override
    public void modifyNotice(Notice notice) throws Exception {
        backendMapper.updateNotice(notice);
    }

    @Override
    public void addContest(Contest contest) throws Exception {
        backendMapper.insertContest(contest);
    }

    @Override
    public void modifyProblem(ProblemRecords problemRecords) throws Exception {
        backendMapper.updateProblem(problemRecords);
    }

    @Override
    public void modifyContest(Contest contest) throws Exception {
        backendMapper.updateContest(contest);
    }

}
