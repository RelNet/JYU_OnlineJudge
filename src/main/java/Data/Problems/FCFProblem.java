package Data.Problems;

import Database.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FCFProblem extends CFProblem {
    public String SampleCode = null;

    public String getSampleCode() {
        return SampleCode;
    }

    public void setSampleCode(String sampleCode) {
        SampleCode = sampleCode;
    }

    public String GetSampleCode(int ProblemID){
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        String SampleCode = null;
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "select sample_code from t_fcf_samplecode  where problem_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, ProblemID);
            GetResultSet = GetPreparedStatement.executeQuery();
            if (GetResultSet.next()) {
                SampleCode = GetResultSet.getString("sample_code");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return SampleCode;
    }

    public void UpdateSampleCode(int ProblemID, String NewSampleCode){
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "update t_fcf_samplecode set sample_code = ? where problem_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1, NewSampleCode);
            GetPreparedStatement.setInt(2, ProblemID);
            GetPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetPreparedStatement, GetConnectionDatabase);
        }
    }

    public void InsertDatabase(FCFProblem NewProblem){
        int Key = NewProblem.InsertDatabase((CFProblem) NewProblem);
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "insert into t_fcf_samplecode (problem_id, sample_code) values(?,?)";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, Key);
            GetPreparedStatement.setString(2, NewProblem.SampleCode);
            GetPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetPreparedStatement, GetConnectionDatabase);
        }

    }



}
