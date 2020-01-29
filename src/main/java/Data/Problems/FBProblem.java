package Data.Problems;

import Database.JdbcConnection;
import JudgeSystem.ProblemType;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FBProblem extends MainProblemMessage implements Serializable {
    public List<String> ans = new ArrayList<String>();

    public List<String> GetAns() {
        return this.ans;
    }

    public List<String> getAns() {
        return ans;
    }

    public void setAns(List<String> ans) {
        this.ans = ans;
    }

    public void SetAns(List<String> ans) {
        for (String temp : ans) {
            this.ans.add(temp);
        }
    }

    @Override
    public String GetProblemDescribe(int ProblemID) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        String ProblemDescribe = null;
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "select problem_describe from t_mcq_fb_detail where problem_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, ProblemID);
            GetResultSet = GetPreparedStatement.executeQuery();
            if (GetResultSet.next()) {
                ProblemDescribe = GetResultSet.getString("problem_describe");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return ProblemDescribe;
    }

    @Override
    public void UpdateProblemDescribe(String NewProblemDescribe, int ProblemID) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "update t_mcq_fb_detail set problem_describe = ? where problem_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1, NewProblemDescribe);
            GetPreparedStatement.setInt(2, ProblemID);
            GetPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetPreparedStatement, GetConnectionDatabase);
        }
    }

    public List<String> GetAns(int ProblemID) {
        List<String> AnsList = new ArrayList<String>();
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "select answer from t_mcq_fb_answer where problem_id = ? order by number";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, ProblemID);
            GetResultSet = GetPreparedStatement.executeQuery();
            while (GetResultSet.next()) {
                AnsList.add(GetResultSet.getString("answer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return AnsList;
    }

    public void UpdateAnswer(List<String> NewProblemAnswer, int ProblemID) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        int count = 0;
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            GetConnectionDatabase.setAutoCommit(false);
            String sql = "delete from t_mcq_fb_answer where problem_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, ProblemID);
            GetPreparedStatement.executeUpdate();
            for (String temp : NewProblemAnswer) {
                sql = "insert into t_mcq_fb_answer (problem_id , answer, number) values(?,?,?)";
                GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
                GetPreparedStatement.setInt(1, ProblemID);
                GetPreparedStatement.setString(2, temp);
                GetPreparedStatement.setInt(3, count);
                count++;
                GetPreparedStatement.executeUpdate();
            }
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

    public void InsertDatabase(FBProblem NewProblem) {
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
            String sql = "insert into t_problem (title , source, problem_type) values(?,?,1)";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            GetPreparedStatement.setString(1, NewProblem.title);
            GetPreparedStatement.setString(2, NewProblem.source);
            GetPreparedStatement.executeUpdate();
            GetResultSet = GetPreparedStatement.getGeneratedKeys();
            if (GetResultSet.next()) {
                Key = GetResultSet.getInt(1);
            }
            //此时的key就是插入的problem_id;
            sql = "insert into t_mcq_fb_detail(problem_id, problem_describe) values(?,?)";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, Key);
            GetPreparedStatement.setString(2, NewProblem.ProblemDescribe);
            GetPreparedStatement.executeUpdate();

            for (String temp : NewProblem.ans) {
                sql = "insert into t_mcq_fb_answer (problem_id , answer, number) values(?,?,?)";
                GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
                GetPreparedStatement.setInt(1, Key);
                GetPreparedStatement.setString(2, temp);
                GetPreparedStatement.setInt(3, count);
                count++;
                GetPreparedStatement.executeUpdate();
            }
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


    }


}
