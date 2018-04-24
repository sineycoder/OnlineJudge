package com.jsut.ecoder.dao;

import com.jsut.ecoder.po.*;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.parsing.Problem;

import java.util.Date;
import java.util.List;

public interface BackendMapper {

    @Update("UPDATE web_problems SET problemContestId = -1 WHERE problemId = #{problemId}")
    void insertToProblems(@Param("problemId")Integer problemId)throws Exception;

    @Select("SELECT problemId,problemTitle,problemAuthor,problemCreateTime,problemIsShow " +
            "FROM web_problems " +
            "WHERE problemContestId = #{id} " +
            "ORDER BY problemId DESC " +
            "LIMIT #{start},#{step}")
    List<ProblemRecords> selectProblemsByContestId(@Param("start") int start, @Param("step")int step,@Param("id")Integer id)throws Exception;

    @Delete("DELETE FROM web_contest WHERE id = #{id}")
    void deleteContestByContestId(@Param("id")Integer id)throws Exception;

    @Select("SELECT * FROM web_contest WHERE id = #{id}")
    Contest selectContestById(@Param("id")Integer id)throws Exception;

    @Select("SELECT id,contestName,startTime,endTime,createTime,contestPassword,contestStatus,contestVisible,contestCreator " +
            "FROM web_contest " +
            "ORDER BY id DESC " +
            "LIMIT #{start},#{step}")
    List<Contest> selectContests(@Param("start") int start, @Param("step")int step)throws Exception;

    @Delete("DELETE FROM web_submit_list WHERE problemId = #{id}")
    void deleteProblemSubmitByProblemId(@Param("id")Integer id)throws Exception;

    @Delete("DELETE FROM web_problems_tag WHERE problemId = #{id}")
    void deleteProblemTagByProblemId(@Param("id")Integer id)throws Exception;

    @Delete("DELETE FROM web_problems_language WHERE problemId = #{id}")
    void deleteProblemLanguageByProblemId(@Param("id")Integer id)throws Exception;

    @Delete("DELETE FROM web_problems WHERE problemId = #{id}")
    void deleteProblemById(@Param("id")Integer id)throws Exception;

    @Select("SELECT problemId,problemTitle,problemDescription,problemInputDescription,problemOutputDescription,problemSample,problemDifficulty,problemIsShow,problemType spj,problemReturn returnErr,problemHint,problemSource,problemInput,problemOutput " +
            "FROM web_problems " +
            "WHERE problemId = #{id} ")
    ProblemRecords selectProblemById(@Param("id")Integer id)throws Exception;

    @Select("SELECT aa.languageId,bb.languageName,aa.problemCpuTime,aa.problemMemory " +
            "FROM(SELECT * " +
            "FROM web_problems_language " +
            "where problemId = #{id}) aa " +
            "LEFT JOIN web_language bb " +
            "ON aa.languageId = bb.languageId")
    List<ProblemParams> selectProblemParamsById(@Param("id")Integer id)throws Exception;

    @Select("SELECT aa.tagId,bb.tagName,bb.createTime " +
            "FROM(SELECT * " +
            "FROM web_problems_tag " +
            "where problemId = #{id}) aa " +
            "LEFT JOIN web_tag bb " +
            "ON aa.tagId = bb.tagId ")
    List<Tag> selectProblemTagsById (@Param("id")Integer id)throws Exception;

    @Select("SELECT problemId,problemTitle,problemAuthor,problemCreateTime,problemIsShow " +
            "FROM web_problems " +
            "ORDER BY problemId DESC " +
            "LIMIT #{start},#{step}")
    List<ProblemRecords> selectProblems(@Param("start") int start, @Param("step")int step)throws Exception;

    @Delete("DELETE FROM web_notice WHERE noticeId = #{id}")
    void deleteNotice(@Param("id")Integer id)throws Exception;

    @Select("SELECT COUNT(*) FROM web_notice")
    int selectNoticeCount()throws Exception;

    @Select("SELECT a.noticeId,a.title,a.content,a.isShow,b.userName as createUserName,a.createTime,a.currentUpdateTime " +
            "FROM (select * " +
            "FROM web_notice " +
            "ORDER BY currentUpdateTime DESC " +
            "LIMIT #{start},#{step}) a " +
            "LEFT JOIN web_user b " +
            "ON a.createUserId = b.userId")
    List<Notice> selectNotices(@Param("start") int start, @Param("step")int step)throws Exception;

    @Select("SELECT * FROM web_language")
    List<Language> selectLanguages()throws Exception;

    @Select("SELECT * FROM backend_config")
    BackendConfig selectBackendConfig()throws Exception;

    @Select("SELECT userId,userName,createTime,lastLogin,infoRealName,userEmail,userGrant,isChange " +
            "FROM web_user " +
            "WHERE userName != 'root'" +
            "LIMIT #{start},#{step} ")
    List<User> selectUsers(@Param("start") int start, @Param("step")int step)throws Exception;

    @Delete("DELETE FROM web_user WHERE userId=#{userId}")
    void deleteUserByUserId(@Param("userId")Integer userId)throws Exception;

    void updateBackendConfig(BackendConfig config)throws Exception;

    void insertLanguagesParam(List<ProblemParams> params)throws Exception;

    void insertNewProblem(ProblemRecords records)throws Exception;

    Integer selectIdByTagName(@Param("tagname") String tagName)throws Exception;

    void insertTag(Tag tag)throws Exception;

    void insertTagProblem(@Param("tagId")Integer tagId,@Param("problemId")Integer problemId)throws Exception;

    void insertNotice(Notice notice);

    void updateNotice(Notice notice)throws Exception;

    void insertContest(Contest contest)throws Exception;

    void updateProblem(ProblemRecords problemRecords)throws Exception;

    void updateContest(Contest contest)throws Exception;

}
