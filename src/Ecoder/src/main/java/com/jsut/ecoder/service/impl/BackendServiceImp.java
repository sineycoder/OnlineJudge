package com.jsut.ecoder.service.impl;

import com.jsut.ecoder.dao.BackendMapper;
import com.jsut.ecoder.po.Language;
import com.jsut.ecoder.po.ProblemParams;
import com.jsut.ecoder.po.ProblemRecords;
import com.jsut.ecoder.po.Tag;
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
    public List<Language> queryLanguages() throws Exception {
        return backendMapper.selectLanguages();
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

}
