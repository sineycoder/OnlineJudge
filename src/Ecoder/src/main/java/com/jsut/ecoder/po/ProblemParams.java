package com.jsut.ecoder.po;

import lombok.Data;
import org.omg.CORBA.INTERNAL;

import java.io.Serializable;

@Data
public class ProblemParams implements Serializable {

        private Integer problemId = null;
        private Integer languageId;
        private String languageName;
        private Integer problemMemory;
        private Integer problemCpuTime;

}
