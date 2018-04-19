package com.jsut.ecoder.service;

import com.jsut.ecoder.po.Language;
import com.jsut.ecoder.po.ProblemParams;
import com.jsut.ecoder.po.ProblemRecords;
import com.jsut.ecoder.po.Tag;

import java.util.Date;
import java.util.List;

public interface BackendService {

    List<Language> queryLanguages()throws Exception;

    void addLanguagesParam(List<ProblemParams> params)throws Exception;

    void addNewProblem(ProblemRecords records)throws Exception;

    Integer queryIdByTagName(String tagName)throws Exception;

    void addTag(Tag tag)throws Exception;

    void addTagProblem(Integer tagId,Integer problemId)throws Exception;

}
