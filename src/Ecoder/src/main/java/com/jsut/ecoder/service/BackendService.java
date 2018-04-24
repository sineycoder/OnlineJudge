package com.jsut.ecoder.service;

import com.jsut.ecoder.po.*;

import java.util.Date;
import java.util.List;

public interface BackendService {

    void addToProblems(Integer problemId)throws Exception;

    List<ProblemRecords> queryProblemsByContestId(Pagination pagination,Integer id)throws Exception;

    void dropContestByContestId(Integer id)throws Exception;

    Contest queryContestById(Integer id)throws Exception;

    List<Contest> queryContests(Pagination pagination)throws Exception;

    void dropProblemSubmitByProblemId(Integer id) throws Exception;

    void dropProblemTagByProblemId(Integer id) throws Exception;

    void dropProblemLanguageByProblemId(Integer id) throws Exception;

    void dropProblemById(Integer id) throws Exception;

    List<ProblemParams> querytProblemParamsById(Integer id) throws Exception;

    List<Tag> queryProblemTagsById(Integer id) throws Exception;

    ProblemRecords queryProblemById(Integer id) throws Exception;

    List<ProblemRecords> queryProblems(Pagination pagination)throws Exception;

    void dropNotice(Integer id)throws Exception;

    int queryNoticeCount()throws Exception;

    List<Notice> queryNotices(Pagination pagination)throws Exception;

    List<Language> queryLanguages()throws Exception;

    BackendConfig queryBackendConfig()throws Exception;

    List<User> queryUsers(Pagination pagination)throws Exception;

    void dropUserByUserId(Integer userId)throws Exception;

    void modifyBackendConfig(BackendConfig config)throws Exception;

    void addLanguagesParam(List<ProblemParams> params)throws Exception;

    void addNewProblem(ProblemRecords records)throws Exception;

    Integer queryIdByTagName(String tagName)throws Exception;

    void addTag(Tag tag)throws Exception;

    void addTagProblem(Integer tagId,Integer problemId)throws Exception;

    void addNotice(Notice notice)throws Exception;

    void modifyNotice(Notice notice)throws Exception;

    void addContest(Contest contest)throws Exception;

    void modifyProblem(ProblemRecords problemRecords) throws Exception;

    void modifyContest(Contest contest)throws Exception;

}
