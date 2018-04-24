package com.jsut.ecoder.service;

import com.jsut.ecoder.po.*;

import java.util.Date;
import java.util.List;

public interface CommonService {

    List<Notice> queryContestStatus(Date date)throws Exception;

    int querySubmitsCountsByResult(String result)throws Exception;

    List<Notice> queryVisibleNotices()throws Exception;

    int querySubmitsCounts(String userName)throws Exception;

    List<SubmitsRecords> querySubmitsTotals(Pagination pagination,String userName)throws Exception;

    int queryProblemsCounts(ProblemRecords problemRecords)throws Exception;

    List<ProblemRecords> queryProblemsTotals(Pagination pagination)throws Exception;

    List<RankUsers> queryRankResults(Pagination pagination,String userName)throws Exception;

    List<Tag> queryProblemsTags(Pagination pagination)throws Exception;

    int queryContestTotals(Boolean contestVisible)throws Exception;

    List<Contest> queryContestResult(Pagination pagination)throws Exception;

    ProblemRecords queryProblemCodingById(int id)throws Exception;

    List<ProblemParams> queryProblemParamsById(int id)throws Exception;

}
