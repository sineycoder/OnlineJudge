package com.jsut.ecoder.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class Pagination implements Serializable {

    public  Integer pageSize = 10;//default size = 10
    private Integer currentPage = 1;//default page = 1
    private Integer total;

}
