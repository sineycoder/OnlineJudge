package cn.jsut.oj.Ecoder.po;

public class JudgeResult {
    private int status;
    private int signal;
    private double cpu_time;
    private double real_time;

    private String judge_result;
    private String judge_type;//judging? compiling?
    private String logs;
    private String output_user;
    private String output_ans;

    @Override
    public String toString() {
        return "JudgeResult{" +
                "status=" + status +
                ", signal=" + signal +
                ", cpu_time=" + cpu_time +
                ", real_time=" + real_time +
                ", judge_result='" + judge_result + '\'' +
                ", judge_type='" + judge_type + '\'' +
                ", logs='" + logs + '\'' +
                ", output_user='" + output_user + '\'' +
                ", output_ans='" + output_ans + '\'' +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

    public double getCpu_time() {
        return cpu_time;
    }

    public void setCpu_time(double cpu_time) {
        this.cpu_time = cpu_time;
    }

    public double getReal_time() {
        return real_time;
    }

    public void setReal_time(double real_time) {
        this.real_time = real_time;
    }

    public String getJudge_result() {
        return judge_result;
    }

    public void setJudge_result(String judge_result) {
        this.judge_result = judge_result;
    }

    public String getJudge_type() {
        return judge_type;
    }

    public void setJudge_type(String judge_type) {
        this.judge_type = judge_type;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    public String getOutput_user() {
        return output_user;
    }

    public void setOutput_user(String output_user) {
        this.output_user = output_user;
    }

    public String getOutput_ans() {
        return output_ans;
    }

    public void setOutput_ans(String output_ans) {
        this.output_ans = output_ans;
    }
}
