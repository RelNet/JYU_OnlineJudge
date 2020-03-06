package old.Data.Problems;

import old.Database.JdbcConnection;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FCFProblem extends CFProblem implements Serializable {
    public String SampleCode = null;
    //Space Number就是空出来的需要填入的数量
    public int SpaceNumber = 0;

    public int getSpaceNumber() {
        return SpaceNumber;
    }

    public void setSpaceNumber(int spaceNumber) {
        SpaceNumber = spaceNumber;
    }

    public String getSampleCode() {
        return SampleCode;
    }

    public void setSampleCode(String sampleCode) {
        SampleCode = sampleCode;
    }

    @Override
    public String toString() {
        return "FCFProblem{" +
                "SampleCode='" + SampleCode + '\'' +
                ", SpaceNumber=" + SpaceNumber +
                ", InputDescribe='" + InputDescribe + '\'' +
                ", OutputDescribe='" + OutputDescribe + '\'' +
                ", TimeLimit='" + TimeLimit + '\'' +
                ", MemoryLimit='" + MemoryLimit + '\'' +
                ", SampleInputOutput=" + SampleInputOutput +
                ", ProblemDescribe='" + ProblemDescribe + '\'' +
                ", AcceptNumber=" + AcceptNumber +
                ", AttemptNumber=" + AttemptNumber +
                ", source='" + source + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    //传入题目ID得到这个这个题目有多少个需要填的空格
    public int GetSpaceNumber(int ProblemID) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        int number = 0;
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "select space_number from t_fcf_samplecode  where problem_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, ProblemID);
            GetResultSet = GetPreparedStatement.executeQuery();
            if (GetResultSet.next()) {
                number = GetResultSet.getInt("space_number");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return number;
    }

    public void UpdateSpaceNumber(int ProblemID, int NewSpaceNumber) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "update t_fcf_samplecode set space_number = ? where problem_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, NewSpaceNumber);
            GetPreparedStatement.setInt(2, ProblemID);
            GetPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
    }

    public String GetSampleCode(int ProblemID) {
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

    public void UpdateSampleCode(int ProblemID, String NewSampleCode) {
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

    public void InsertDatabase(FCFProblem NewProblem) {
        int Key = NewProblem.InsertDatabase((CFProblem) NewProblem);
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "insert into t_fcf_samplecode (problem_id, sample_code,space_number ) values(?,?,?)";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, Key);
            GetPreparedStatement.setString(2, NewProblem.SampleCode);
            GetPreparedStatement.setInt(3, NewProblem.SpaceNumber);
            GetPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetPreparedStatement, GetConnectionDatabase);
        }

    }


    public int GetProblemSpaceNumber(int ProblemID) {
        return 0;
    }
}
