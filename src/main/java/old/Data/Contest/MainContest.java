package old.Data.Contest;

import judge.JudgeMode;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainContest implements Serializable {
    public String ContestName = null;//比赛标题
    public java.sql.Date StartTime = java.sql.Date.valueOf(LocalDate.now());//比赛开始时间
    public java.sql.Date EndTime = java.sql.Date.valueOf(LocalDate.now());
    ;//比赛结束时间
    public String leader = null;//发起人
    public JudgeMode mode;//比赛模式，ACM,OI；
    public List<Integer> ProblemList = new ArrayList<>();//比赛问题列表
    public String ContestPassword = null;//比赛密码
    public List<String> ContestStuduent = new ArrayList<>();//参加比赛的人

    @Override
    public String toString() {
        return "MainContest{" +
                "ContestName='" + ContestName + '\'' +
                ", StartTime=" + StartTime +
                ", EndTime=" + EndTime +
                ", leader='" + leader + '\'' +
                ", mode=" + mode +
                ", ProblemList=" + ProblemList +
                ", ContestPassword='" + ContestPassword + '\'' +
                ", ContestStuduent=" + ContestStuduent +
                '}';
    }

    public String getContestName() {
        return ContestName;
    }

    public void setContestName(String contestName) {
        ContestName = contestName;
    }

    public Date getStartTime() {
        return StartTime;
    }

    public void setStartTime(Date startTime) {
        StartTime = startTime;
    }

    public Date getEndTime() {
        return EndTime;
    }

    public void setEndTime(Date endTime) {
        EndTime = endTime;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public JudgeMode getMode() {
        return mode;
    }

    public void setMode(JudgeMode mode) {
        this.mode = mode;
    }

    public List<Integer> getProblemList() {
        return ProblemList;
    }

    public void setProblemList(List<Integer> problemList) {
        ProblemList = problemList;
    }

    public String getContestPassword() {
        return ContestPassword;
    }

    public void setContestPassword(String contestPassword) {
        ContestPassword = contestPassword;
    }

    public List<String> getContestStuduent() {
        return ContestStuduent;
    }

    public void setContestStuduent(List<String> contestStuduent) {
        ContestStuduent = contestStuduent;
    }
}
