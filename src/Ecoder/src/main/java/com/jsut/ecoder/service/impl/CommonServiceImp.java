package com.jsut.ecoder.service.impl;

import com.jsut.ecoder.dao.CommonMapper;
import com.jsut.ecoder.po.*;
import com.jsut.ecoder.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommonServiceImp implements CommonService{

    @Autowired
    private CommonMapper commonMapper;

    @Override
    public List<Notice> queryVisibleNotices() throws Exception {
        List<Notice> notices = commonMapper.selectVisibleNotices();
        return notices;
    }

    @Override
    public int querySubmitsCounts(String userName) throws Exception {
        return commonMapper.selectSubmitsCounts(userName);
    }

    @Override
    public List<SubmitsRecords> querySubmitsTotals(Pagination pagination,String userName) throws Exception {
        int step = pagination.pageSize;
        int size = (pagination.getTotal()-1) / step + 1;
        if(pagination.getCurrentPage() > size)throw new Exception("Error , your page is greater than size !");
        int start = (pagination.getCurrentPage()-1) * step;
        return commonMapper.selectSubmitsTotals(start, step, userName);
    }

    @Override
    public int queryProblemsCounts() throws Exception {
        return commonMapper.selectProblemsCounts();
    }

    @Override
    public List<ProblemRecords> queryProblemsTotals(Pagination pagination) throws Exception {
        int step = pagination.pageSize;
        int size = (pagination.getTotal()-1) / step + 1;
        if(pagination.getCurrentPage() > size)throw new Exception("Error , your page is greater than size !");
        int start = (pagination.getCurrentPage()-1) * step;
        return commonMapper.selectProblemsTotals(start, step);
    }


    @Override
    public List<RankUsers> queryRankResults(Pagination pagination, String userName) throws Exception {
        int step = pagination.pageSize;
        int size = (pagination.getTotal()-1) / step + 1;
        if(pagination.getCurrentPage() > size)throw new Exception("Error , your page is greater than size !");
        int start = (pagination.getCurrentPage()-1) * step;
        return commonMapper.selectRankResults(start, step, userName);
    }

    @Override
    public List<Tag> queryProblemsTags(Pagination pagination) throws Exception {
        int step = pagination.pageSize;
        int size = (pagination.getTotal()-1) / step + 1;
        if(pagination.getCurrentPage() > size)throw new Exception("Error , your page is greater than size !");
        int start = (pagination.getCurrentPage()-1) * step;
        return commonMapper.selectProblemsTags(start,step);
    }

    @Override
    public int queryContestTotals() throws Exception {
        return commonMapper.selectContestTotals();
    }

    @Override
    public List<Contest> queryContestResult(Pagination pagination) throws Exception {
        int step = pagination.pageSize;
        int size = (pagination.getTotal()-1) / step + 1;
        if(pagination.getCurrentPage() > size)throw new Exception("Error , your page is greater than size !");
        int start = (pagination.getCurrentPage()-1) * step;
        return commonMapper.selectContestResult(start, step);
    }

    @Override
    public ProblemRecords queryProblemCodingById(int id) throws Exception {
        return commonMapper.selectProblemCodingById(id);
    }

    @Override
    public List<ProblemParams> queryProblemParamsById(int id) throws Exception {
        return commonMapper.selectProblemParamsById(id);
    }

}
