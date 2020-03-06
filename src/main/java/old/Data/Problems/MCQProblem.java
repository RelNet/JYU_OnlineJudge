package old.Data.Problems;


import old.Database.JdbcConnection;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MCQProblem extends MainProblemMessage implements Serializable {
    public List<String> ProblemOption = new ArrayList<String>();
    public List<String> ProblemAnswer = new ArrayList<String>();

    public List<String> GetProblemOption() {
        return this.ProblemOption;
    }

    public List<String> GetProblemAnswer() {
        return this.ProblemAnswer;
    }

    public void SetProblemOption(List<String> ProblemOption) {
        this.ProblemOption = ProblemOption;
    }

    public void SetProblemAnswer(List<String> ProblemAnswer) {
        this.ProblemAnswer = ProblemAnswer;
    }

    @Override
    public String toString() {
        return "MCQProblem{" +
                "ProblemOption=" + ProblemOption +
                ", ProblemAnswer=" + ProblemAnswer +
                ", ProblemDescribe='" + ProblemDescribe + '\'' +
                ", AcceptNumber=" + AcceptNumber +
                ", AttemptNumber=" + AttemptNumber +
                ", source='" + source + '\'' +
                ", title='" + title + '\'' +
                '}';
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

    public List<String> GetProblemAnswer(int ProblemID) {
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

    public List<String> GetProblemOption(int ProblemID) {
        List<String> option = new ArrayList<String>();
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "select problem_option from t_mcq_detail where problem_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, ProblemID);
            GetResultSet = GetPreparedStatement.executeQuery();
            while (GetResultSet.next()) {
                option.add(GetResultSet.getString("problem_option"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return option;
    }

    public void UpdateProblemOption(List<String> NewProblemOption, int ProblemID) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        int count = 0;
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            GetConnectionDatabase.setAutoCommit(false);
            String sql = "delete from t_mcq_fb_detail where problem_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, ProblemID);
            GetPreparedStatement.executeUpdate();
            for (String temp : NewProblemOption) {
                sql = "insert into t_mcq_fb_detail(problem_id,problem_option) values( problem_id = ?, problem_option = ?)";
                GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
                GetPreparedStatement.setInt(1, ProblemID);
                GetPreparedStatement.setString(2, temp);
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

    public void InsertDatabase(MCQProblem NewProblem) {
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
            String sql = "insert into t_problem (title , source, problem_type) values(?,?,0)";
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

            for (String temp : NewProblem.ProblemAnswer) {
                sql = "insert into t_mcq_fb_answer (problem_id , answer) values(?,?)";
                GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
                GetPreparedStatement.setInt(1, Key);
                GetPreparedStatement.setString(2, temp);
                GetPreparedStatement.executeUpdate();
            }

            for (String temp : NewProblem.ProblemOption) {
                sql = "insert into t_mcq_detail (problem_id , problem_option) values(?,?)";
                GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
                GetPreparedStatement.setInt(1, Key);
                GetPreparedStatement.setString(2, temp);
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



