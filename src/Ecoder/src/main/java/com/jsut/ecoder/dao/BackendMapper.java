package com.jsut.ecoder.dao;

import com.jsut.ecoder.po.Language;
import com.jsut.ecoder.po.ProblemParams;
import com.jsut.ecoder.po.ProblemRecords;
import com.jsut.ecoder.po.Tag;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.parsing.Problem;

import java.util.Date;
import java.util.List;

public interface BackendMapper {

    @Select("SELECT * FROM web_language")
    List<Language> selectLanguages()throws Exception;

    void insertLanguagesParam(List<ProblemParams> params)throws Exception;

    void insertNewProblem(ProblemRecords records)throws Exception;

    Integer selectIdByTagName(@Param("tagname") String tagName)throws Exception;

    void insertTag(Tag tag)throws Exception;

    void insertTagProblem(@Param("tagId")Integer tagId,@Param("problemId")Integer problemId)throws Exception;


}
