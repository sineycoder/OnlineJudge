package cn.jsut.oj.Ecoder.po;

import java.util.List;

/**
 * judge content
 */
public class JudgeBody {
    private String judgeId;
    private String judgeLanguage;
    private String codes;
    private boolean specialJudge;
    private List<String> input;
    private List<String> output;
    private long cpu_limit;
    private long ram_limit;

    public String getJudgeId() {
        return judgeId;
    }

    public void setJudgeId(String judgeId) {
        this.judgeId = judgeId;
    }

    public String getJudgeLanguage() {
        return judgeLanguage;
    }

    public void setJudgeLanguage(String judgeLanguage) {
        this.judgeLanguage = judgeLanguage;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public boolean isSpecialJudge() {
        return specialJudge;
    }

    public void setSpecialJudge(boolean specialJudge) {
        this.specialJudge = specialJudge;
    }

    public List<String> getInput() {
        return input;
    }

    public void setInput(List<String> input) {
        this.input = input;
    }

    public List<String> getOutput() {
        return output;
    }

    public void setOutput(List<String> output) {
        this.output = output;
    }

    public long getCpu_limit() {
        return cpu_limit;
    }

    public void setCpu_limit(long cpu_limit) {
        this.cpu_limit = cpu_limit;
    }

    public long getRam_limit() {
        return ram_limit;
    }

    public void setRam_limit(long ram_limit) {
        this.ram_limit = ram_limit;
    }

    @Override
    public String toString() {
        return "JudgeBody{" +
                "judgeId='" + judgeId + '\'' +
                ", judgeLanguage='" + judgeLanguage + '\'' +
                ", codes='" + codes + '\'' +
                ", specialJudge=" + specialJudge +
                ", input=" + input +
                ", output=" + output +
                ", cpu_limit=" + cpu_limit +
                ", ram_limit=" + ram_limit +
                '}';
    }
}
