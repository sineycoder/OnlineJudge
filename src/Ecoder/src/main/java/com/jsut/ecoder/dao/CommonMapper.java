package com.jsut.ecoder.dao;

import com.jsut.ecoder.po.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommonMapper {

    List<Notice> selectVisibleNotices()throws Exception;

    int selectSubmitsCounts(@Param("userName")String userName)throws Exception;

    List<SubmitsRecords> selectSubmitsTotals(@Param("start")int start,@Param("step")int step,@Param("userName")String userName)throws Exception;

    int selectProblemsCounts()throws Exception;

    List<ProblemRecords> selectProblemsTotals(@Param("start") int start, @Param("step")int step)throws Exception;

    List<RankUsers> selectRankResults(@Param("start")int start,@Param("step")int step,@Param("userName")String userName)throws Exception;

    List<Tag> selectProblemsTags(@Param("start")int start,@Param("step")int step)throws Exception;

    int selectContestTotals()throws Exception;

    List<Contest> selectContestResult(@Param("start")int start,@Param("step")int step)throws Exception;

    ProblemRecords selectProblemCodingById(@Param("id")int id)throws Exception;

    List<ProblemParams> selectProblemParamsById(@Param("id")int id)throws Exception;

}
