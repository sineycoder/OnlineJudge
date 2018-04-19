package com.jsut.ecoder.po;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Tag {

    private Integer tagId;
    private String tagName;
    private Date createTime;

}
