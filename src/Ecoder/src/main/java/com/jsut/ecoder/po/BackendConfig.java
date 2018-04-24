package com.jsut.ecoder.po;

import lombok.Data;

@Data
public class BackendConfig {

    private Integer id;
    private String name;
    private boolean registerPermission;
    private String footer;
    private String email;
    private String emailPass;
    private String host;

}
