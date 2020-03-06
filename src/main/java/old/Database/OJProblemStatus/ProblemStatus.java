package old.Database.OJProblemStatus;

import old.Database.JdbcConnection;
import judge.JudgeLanguage;
import judge.JudgeMode;
import judge.JudgeSystemConstant;
import judge.ProblemType;

import java.io.Serializable;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProblemStatus implements Serializable {
    public String courseCodes;
    public JudgeMode judgeMode;     // 记录是ACM还是IO判断方式
    public String user;
    public Integer submitID;
    public java.sql.Date submitDate = java.sql.Date.valueOf(LocalDate.now());
    public Integer runTime;
    public Integer runMemory;

    public String getCourseCodes() {
        return courseCodes;
    }

    public void setCourseCodes(String courseCodes) {
        this.courseCodes = courseCodes;
    }

    public String getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "ProblemStatus{" +
                "courseCodes='" + courseCodes + '\'' +
                ", judgeMode=" + judgeMode +
                ", user='" + user + '\'' +
                ", submitID=" + submitID +
                ", submitDate=" + submitDate +
                ", runTime=" + runTime +
                ", runMemory=" + runMemory +
                ", languageType=" + languageType +
                ", problemID=" + problemID +
                ", controlCode=" + controlCode +
                ", problemType=" + problemType +
                ", contestID=" + contestID +
                '}';
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getSubmitID() {
        return submitID;
    }

    public void setSubmitID(Integer submitID) {
        this.submitID = submitID;
    }

    public void setSubmitDate(java.sql.Date submitDate) {
        this.submitDate = submitDate;
    }

    public Integer getRunTime() {
        return runTime;
    }

    public void setRunTime(Integer runTime) {
        this.runTime = runTime;
    }

    public Integer getRunMemory() {
        return runMemory;
    }

    public void setRunMemory(Integer runMemory) {
        this.runMemory = runMemory;
    }

    public int getContestID() {
        return contestID;
    }

    public void setContestID(Integer contestID) {
        this.contestID = contestID;
    }

    public JudgeLanguage languageType;
    public Integer problemID;
    public JudgeSystemConstant controlCode;
    public ProblemType problemType;
    public Integer contestID;

    //page是第几页，每页显示25个数据,constID就是比赛ID，初始题库的比赛ID是0
    public List<ProblemStatus> GetStatuspage(int page, int ContestID) {
        List<ProblemStatus> AnsList = new ArrayList<ProblemStatus>();
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "select * from t_status where contest_id = " + ContestID + " limit " + ((page - 1) * 25) + ",25";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetResultSet = GetPreparedStatement.executeQuery();
            while (GetResultSet.next()) {
                ProblemStatus temp = new ProblemStatus();
                //problem_id, submit_time, language, run_time, run_memory,code,status,judge_mode,user_name,contest_id
                temp.submitDate = GetResultSet.getDate("submit_time");
                temp.problemID = GetResultSet.getInt("problem_id");
                temp.languageType = JudgeLanguage.IntToEnum(GetResultSet.getInt("language"));
                temp.runTime = GetResultSet.getInt("run_time");
                temp.runMemory = GetResultSet.getInt("run_memory");
                temp.courseCodes = GetResultSet.getString("code");
                temp.judgeMode = JudgeMode.IntToEnum(GetResultSet.getInt("judge_mode"));
                temp.user = GetResultSet.getString("user_name");
                temp.contestID = GetResultSet.getInt("contest_id");
                temp.submitID = GetResultSet.getInt("submit_id");
                AnsList.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return AnsList;
    }

    public Integer InsertStatus(ProblemStatus NewStatus) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        int Key = 0;
        int count = 0;
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            //开启事务
            GetConnectionDatabase.setAutoCommit(false);
            String sql = "insert into t_status (problem_id, submit_time, language, run_time, run_memory,code,status,judge_mode,user_name,contest_id) " +
                    "values(?,?,?,?,?,?,?,?,?,?)";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            GetPreparedStatement.setInt(1, NewStatus.problemID);
            GetPreparedStatement.setDate(2, NewStatus.submitDate);
            GetPreparedStatement.setInt(3, NewStatus.languageType.ordinal());
            GetPreparedStatement.setInt(4, NewStatus.runTime);
            GetPreparedStatement.setInt(5, NewStatus.runMemory);
            GetPreparedStatement.setString(6, NewStatus.courseCodes);
            GetPreparedStatement.setInt(7, NewStatus.controlCode.ordinal());
            GetPreparedStatement.setInt(8, NewStatus.judgeMode.ordinal());
            GetPreparedStatement.setString(9, NewStatus.user);
            GetPreparedStatement.setInt(10, NewStatus.contestID);
            GetPreparedStatement.executeUpdate();
            GetResultSet = GetPreparedStatement.getGeneratedKeys();
            if (GetResultSet.next()) {
                Key = GetResultSet.getInt(1);
            }

            //插入用户与提交问题对应的表
            sql = "insert into t_sp_link (problem_id,user_name, submit_id , status) values(?,?,?,?)";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, NewStatus.problemID);
            GetPreparedStatement.setString(2, NewStatus.user);
            GetPreparedStatement.setInt(3, Key);
            GetPreparedStatement.setInt(4, NewStatus.controlCode.ordinal());
            GetPreparedStatement.executeUpdate();
            GetConnectionDatabase.commit();
        } catch (SQLException e) {
            if (GetConnectionDatabase != null) {
                //提交插入不成功
                //回滚事务
                try {
                    GetConnectionDatabase.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return Key;
    }

    private void UpdateMessage(String Column, int NewMessage, int SubmitID) {
        String sql = null;
        switch (Column) {
            case "status": {
                //更新t_sp_link的信息
                sql = "update  t_sp_link set status = ? where submit_id = ?";
                Connection GetConnectionDatabase = null;
                PreparedStatement GetPreparedStatement = null;
                JdbcConnection JdbcLink = new JdbcConnection();
                try {
                    GetConnectionDatabase = JdbcLink.Get_Connection();
                    GetConnectionDatabase.setAutoCommit(false);
                    GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
                    GetPreparedStatement.setInt(1, NewMessage);
                    GetPreparedStatement.setInt(2, SubmitID);
                    GetPreparedStatement.executeUpdate();
                    GetConnectionDatabase.commit();
                } catch (SQLException e) {
                    if (GetConnectionDatabase != null) {
                        //提交插入不成功
                        //回滚事务
                        try {
                            GetConnectionDatabase.rollback();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                    e.printStackTrace();
                } finally {
                    JdbcLink.Free(GetPreparedStatement, GetConnectionDatabase);
                }
                sql = "update  t_status set status = ? where submit_id = ?";
                break;
            }
            case "run_time":
                sql = "update  t_status set run_time = ? where submit_id = ?";
                break;
            case "run_memory":
                sql = "update  t_status set run_memory = ? where submit_id = ?";
                break;
        }
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            GetConnectionDatabase.setAutoCommit(false);
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, NewMessage);
            GetPreparedStatement.setInt(2, SubmitID);
            GetPreparedStatement.executeUpdate();
            GetConnectionDatabase.commit();
        } catch (SQLException e) {
            if (GetConnectionDatabase != null) {
                //提交插入不成功
                //回滚事务
                try {
                    GetConnectionDatabase.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetPreparedStatement, GetConnectionDatabase);
        }

    }

    public void UpdateStatus(int SubmitID, JudgeSystemConstant NewStatus) {
        this.UpdateMessage("status", NewStatus.ordinal(), SubmitID);
    }

    public void UpdateRuntime(int SubmitID, int NewRuntime) {
        this.UpdateMessage("run_time", NewRuntime, SubmitID);
    }

    public void UpdateRunMemory(int SubmitID, int NewRunMemory) {
        this.UpdateMessage("run_memory", NewRunMemory, SubmitID);
    }


    public JudgeMode getJudgeMode() {
        return judgeMode;
    }

    public void setJudgeMode(JudgeMode judgeMode) {
        this.judgeMode = judgeMode;
    }


    public Date getSubmitDate() {
        return submitDate;
    }


    public JudgeLanguage getLanguageType() {
        return languageType;
    }

    public void setLanguageType(JudgeLanguage languageType) {
        this.languageType = languageType;
    }

    public Integer getProblemID() {
        return problemID;
    }

    public void setProblemID(Integer problemID) {
        this.problemID = problemID;
    }

    public JudgeSystemConstant getControlCode() {
        return controlCode;
    }

    public void setControlCode(JudgeSystemConstant controlCode) {
        this.controlCode = controlCode;
    }

    public ProblemType getProblemType() {
        return problemType;
    }

    public void setProblemType(ProblemType problemType) {
        this.problemType = problemType;
    }

}
