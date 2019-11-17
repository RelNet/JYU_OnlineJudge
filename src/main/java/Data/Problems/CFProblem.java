package Data.Problems;

import Database.JdbcConnection;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CFProblem extends MainProblemMessage  implements Serializable {

    public String InputDescribe = null;
    public String OutputDescribe = null;
    public String TimeLimit = null;
    public String MemoryLimit = null;
    //List里面的顺序要是inputoutput
    public List<InputOutput> SampleInputOutput = new ArrayList<InputOutput>();

    /**
     * GetMessage()
     * @param ProblemID
     * @return List的顺序是problem_descirbe，input_describe， output_describe，
     * time_limit，memory_limit；
     */

    public List<String> GetCFPMessage(int ProblemID){
        List<String> AnsList = new ArrayList<String>();
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "select problem_describe,input_describe," +
                    "output_describe,time_limit,memory_limit from t_cf_fcf_problem_detail where problem_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, ProblemID);
            GetResultSet = GetPreparedStatement.executeQuery();
            if (GetResultSet.next()) {
                AnsList.add(GetResultSet.getString("problem_describe"));
                AnsList.add(GetResultSet.getString("input_describe"));
                AnsList.add(GetResultSet.getString("output_describe"));
                AnsList.add(GetResultSet.getString("time_limit"));
                AnsList.add(GetResultSet.getString("memory_limit"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return AnsList;
    }
    public List<InputOutput> getSampleInputOutput() {
        return SampleInputOutput;
    }

    public void setSampleInputOutput(List<InputOutput> sampleInputOutput) {
        SampleInputOutput = sampleInputOutput;
    }

    public String getOutputDescribe() {
        return OutputDescribe;
    }

    public void setOutputDescribe(String outputDescribe) {
        OutputDescribe = outputDescribe;
    }

    public String getTimeLimit() {
        return TimeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        TimeLimit = timeLimit;
    }

    public String getMemoryLimit() {
        return MemoryLimit;
    }

    public void setMemoryLimit(String memoryLimit) {
        MemoryLimit = memoryLimit;
    }


    public String getInputDescribe() {
        return InputDescribe;
    }

    public void setInputDescribe(String inputDescribe) {
        InputDescribe = inputDescribe;
    }


    private void UpdateMessage(String ColumbName, int ProblemID, String NewMessage) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        String sql = null;
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            switch (ColumbName) {
                case "problem_describe":
                    sql = "update t_cf_fcf_problem_detail set problem_describe = ? where problem_id = ?";
                    break;
                case "input_describe":
                    sql = "update t_cf_fcf_problem_detail set input_describe = ? where problem_id = ?";
                    break;
                case "output_describe":
                    sql = "update t_cf_fcf_problem_detail set output_describe = ? where problem_id = ?";
                    break;
                case "time_limit":
                    sql = "update t_cf_fcf_problem_detail set time_limit = ? where problem_id = ?";
                    break;
                case "memory_limit":
                    sql = "update t_cf_fcf_problem_detail set memory_limit = ? where problem_id = ?";
                    break;
            }
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1, NewMessage);
            GetPreparedStatement.setInt(2, ProblemID);
            GetPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetPreparedStatement, GetConnectionDatabase);
        }
    }

    public void UpdateInputDescribe(String NewInputDescribe, int ProblemID) {
        this.UpdateMessage("input_describe", ProblemID, NewInputDescribe);
    }

    public void UpdateOutputDescribe(String NewOutputDescribe, int ProblemID) {
        this.UpdateMessage("output_describe", ProblemID, NewOutputDescribe);
    }

    public void UpdateTimeLimit(String NewTimeLimit, int ProblemID) {
        this.UpdateMessage("time_limit", ProblemID, NewTimeLimit);
    }

    public void UpdateMemoryLimit(String NewMemoryLimit, int ProblemID) {
        this.UpdateMessage("memory_limit", ProblemID, NewMemoryLimit);
    }

    @Override
    public void UpdateProblemDescribe(String NewProblemDescribe, int ProblemID) {
        this.UpdateMessage("problem_describe", ProblemID, NewProblemDescribe);
    }

    private String GetMessage(String ColumbName, int ProblemID) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        String sql = null;
        ResultSet GetResultSet = null;
        String AimMessage = null;
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            switch (ColumbName) {
                case "problem_describe":
                    sql = "select problem_describe from t_cf_fcf_problem_detail where problem_id = ?";
                    break;
                case "input_describe":
                    sql = "select input_describe from t_cf_fcf_problem_detail where problem_id = ?";
                    break;
                case "output_describe":
                    sql = "select output_describe from t_cf_fcf_problem_detail where problem_id = ?";
                    break;
                case "time_limit":
                    sql = "select time_limit from t_cf_fcf_problem_detail where problem_id = ?";
                    break;
                case "memory_limit":
                    sql = "select memory_limit from t_cf_fcf_problem_detail where problem_id = ?";
                    break;
            }
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, ProblemID);
            GetResultSet = GetPreparedStatement.executeQuery();
            if (GetResultSet.next()) {
                AimMessage = GetResultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return AimMessage;
    }

    @Override
    public String GetProblemDescribe(int ProblemID) {
        return this.GetMessage("problem_describe", ProblemID);
    }

    public String GetInputDescribe(int ProblemID) {
        return this.GetMessage("input_describe", ProblemID);
    }

    public String GetOutputDescribe(int ProblemID) {
        return this.GetMessage("output_describe", ProblemID);
    }

    public String GetTimeLimit(int ProblemID) {
        return this.GetMessage("time_limit", ProblemID);
    }

    public String GetMemoryLimit(int ProblemID) {
        return this.GetMessage("memory_limit", ProblemID);
    }

    public List<InputOutput> GetSampleInputOutput(int ProblemID){
        List<InputOutput> Sample = new ArrayList<InputOutput>();
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "select input ,output from t_cf_fcf_problem_sample where problem_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, ProblemID);
            GetResultSet = GetPreparedStatement.executeQuery();
            InputOutput temp = new InputOutput();
            while (GetResultSet.next()) {
                temp.setInput(GetResultSet.getString("input"));
                temp.setOutput(GetResultSet.getString("output"));
                Sample.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return Sample;
    }

    public void UpdateSampleInputOutput(int ProblemID,List<InputOutput> NewSampleInput){
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        int count = 0;
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            GetConnectionDatabase.setAutoCommit(false);
            String sql = "delete from t_cf_fcf_problem_sample where problem_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, ProblemID);
            GetPreparedStatement.executeUpdate();
            for (InputOutput temp : NewSampleInput) {
                sql = "insert into t_cf_fcf_problem_sample(problem_id,input, output) values(?,?,?)";
                GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
                GetPreparedStatement.setInt(1, ProblemID);
                GetPreparedStatement.setString(2,temp.getInput());
                GetPreparedStatement.setString(3, temp.getOutput());
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

    /**
     *
     * 返回的是插入数据的主键
     *
     */
    public int InsertDatabase(CFProblem NewProblem){
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
            String sql = "insert into t_problem (title , source, problem_type) values(?,?,2)";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            GetPreparedStatement.setString(1, NewProblem.title);
            GetPreparedStatement.setString(2, NewProblem.source);
            GetPreparedStatement.executeUpdate();
            GetResultSet = GetPreparedStatement.getGeneratedKeys();
            if (GetResultSet.next()) {
                Key = GetResultSet.getInt(1);
            }
            //此时的key就是插入的problem_id;
            sql = "insert into t_cf_fcf_problem_detail(problem_id, problem_describe, input_describe," +
                    "output_describe,time_limit,memory_limit) values(?,?,?,?,?,?)";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, Key);
            GetPreparedStatement.setString(2, NewProblem.ProblemDescribe);
            GetPreparedStatement.setString(3, NewProblem.InputDescribe);
            GetPreparedStatement.setString(4, NewProblem.OutputDescribe);
            GetPreparedStatement.setString(5, NewProblem.TimeLimit);
            GetPreparedStatement.setString(6, NewProblem.MemoryLimit);
            GetPreparedStatement.executeUpdate();
            for (InputOutput temp : NewProblem.SampleInputOutput) {
                sql = "insert into t_cf_fcf_problem_sample(problem_id,input, output) values(?,?,?)";
                GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
                GetPreparedStatement.setInt(1, Key);
                GetPreparedStatement.setString(2,temp.getInput());
                GetPreparedStatement.setString(3, temp.getOutput());
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
        return Key;

    }





}
