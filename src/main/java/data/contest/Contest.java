package data.contest;

import judge.JudgeMode;

import java.sql.Timestamp;

public class Contest {
    private Integer id;
    private JudgeMode mode;
    private String password;
    private ContestInfo info;

    public boolean isClose() {
        return new Timestamp(System.currentTimeMillis()).after(info.getEnd());
    }

    public ContestInfo getInfo() {
        return info;
    }

    public void setInfo(ContestInfo info) {
        this.info = info;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JudgeMode getMode() {
        return mode;
    }

    public void setMode(JudgeMode mode) {
        this.mode = mode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
