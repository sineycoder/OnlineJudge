package com.jsut.ecoder.po;

import lombok.Data;

@Data
public class RankUsers {

    private Integer userId;
    private String userName;
    private String infoMotto;
    private String userTag = "";
    private Integer submitTotal;
    private Double ratio;

}
