package data.submit;

import judge.JudgeMode;
import judge.JudgeSystemConstant;

import java.io.Serializable;
import java.sql.Timestamp;

public class Submit implements Serializable {
    private Integer id;
    private Integer problemId;
    private Integer userId;
    private Integer contestId;
    private String ans;
    private JudgeSystemConstant constant;
    private Integer constantInt;
    private Timestamp time;

    public void setConstantInt() {
        constantInt = JudgeSystemConstant.EnumToInt(constant);
    }

    public Integer getConstantInt() {
        return constantInt;
    }

    public void setConstantInt(Integer constantInt) {
        this.constantInt = constantInt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public JudgeSystemConstant getConstant() {
        return constant;
    }

    public void setConstant(JudgeSystemConstant constant) {
        this.constant = constant;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
