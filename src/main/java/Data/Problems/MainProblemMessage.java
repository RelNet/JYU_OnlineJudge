package Data.Problems;

import Database.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

abstract public class MainProblemMessage {
    public String ProblemDescribe;
    public int AcceptNumber;
    public int AttemptNumber;
    public String source;
    public String title;

    public String GetProblemDescribe() {
        return this.ProblemDescribe;
    }

    public String GetSource() {
        return this.source;
    }

    public String GetTitle() {
        return this.title;
    }

    public int GetAcceptNumber() {
        return this.AcceptNumber;
    }

    public int GetAttemptNumber() {
        return this.AttemptNumber;
    }

    public void SetProblemDescribe(String ProblemDescribe) {
        this.ProblemDescribe = ProblemDescribe;
    }

    public void SetSource(String source) {
        this.source = source;
    }

    public void SetTitle(String title) {
        this.title = title;
    }

    public abstract void UpdateProblemDescribe(String NewProblemDescribe, int ProblemID);

    public void UpdateSource(String NewSource, int ProblemID) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "update t_problem set source = ? where problem_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1, NewSource);
            GetPreparedStatement.setInt(2, ProblemID);
            GetPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetPreparedStatement, GetConnectionDatabase);
        }
    }

    public void UpdateTitle(String NewTitle, int ProblemID) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "update t_problem set title = ? where problem_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1, NewTitle);
            GetPreparedStatement.setInt(2, ProblemID);
            GetPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetPreparedStatement, GetConnectionDatabase);
        }
    }

    public abstract String GetProblemDescribe(int ProblemID);

    public int GetAcceptNumber(int ProblemID) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        int AcceptNumber = 0;
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "select accept_number from t_problem where problem_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, ProblemID);
            GetResultSet = GetPreparedStatement.executeQuery();
            if (GetResultSet.next()) {
                AcceptNumber = GetResultSet.getInt("accept_number");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return AcceptNumber;
    }

    public int GetAttemptNumber(int ProblemID) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        int AttemptNumber = 0;
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "select attempt_number from t_problem where problem_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, ProblemID);
            GetResultSet = GetPreparedStatement.executeQuery();
            if (GetResultSet.next()) {
                AttemptNumber = GetResultSet.getInt("attempt_number");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return AttemptNumber;
    }

    public String GetSource(int ProblemID) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        String source = null;
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "select source from t_problem where problem_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, ProblemID);
            GetResultSet = GetPreparedStatement.executeQuery();
            if (GetResultSet.next()) {
                source = GetResultSet.getString("source");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return source;
    }

    public String GetTitle(int ProblemID) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        String title = null;
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "select title from t_problem where problem_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, ProblemID);
            GetResultSet = GetPreparedStatement.executeQuery();
            if (GetResultSet.next()) {
                title = GetResultSet.getString("title");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return title;
    }

}

