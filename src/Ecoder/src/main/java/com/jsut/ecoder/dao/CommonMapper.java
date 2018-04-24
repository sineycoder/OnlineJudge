package com.jsut.ecoder.dao;

import com.jsut.ecoder.po.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface CommonMapper {

    @Select("select NoticeId,startTime,endTIme FROM web_contest WHERE #{date} BETWEEN startTime and endTime")
    List<Notice> selectContestStatus(@Param("date")Date date)throws Exception;

    @Select("SELECT COUNT(*) FROM web_submit_list WHERE result=#{result}")
    int selectSubmitsCountsByResult(@Param("result")String result)throws Exception;

    List<Notice> selectVisibleNotices()throws Exception;

    int selectSubmitsCounts(@Param("userName")String userName)throws Exception;

    List<SubmitsRecords> selectSubmitsTotals(@Param("start")int start,@Param("step")int step,@Param("userName")String userName)throws Exception;

    int selectProblemsCounts(ProblemRecords problemRecords)throws Exception;

    List<ProblemRecords> selectProblemsTotals(@Param("start") int start, @Param("step")int step)throws Exception;

    List<RankUsers> selectRankResults(@Param("start")int start,@Param("step")int step,@Param("userName")String userName)throws Exception;

    List<Tag> selectProblemsTags(@Param("start")int start,@Param("step")int step)throws Exception;

    int selectContestTotals(@Param("contestVisible") Boolean contestVisible)throws Exception;

    List<Contest> selectContestResult(@Param("start")int start,@Param("step")int step)throws Exception;

    ProblemRecords selectProblemCodingById(@Param("id")int id)throws Exception;

    List<ProblemParams> selectProblemParamsById(@Param("id")int id)throws Exception;

}
