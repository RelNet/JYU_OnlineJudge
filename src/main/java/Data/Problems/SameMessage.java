package Data.Problems;

import Database.JdbcConnection;
import JudgeSystem.ProblemType;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SameMessage implements Serializable {
    public String ProblemDescribe;
    public int AcceptNumber;
    public int AttemptNumber;
    public String source;
    public String title;

    /**
     * @param ProblemID
     * @return 返回的是SameMessage类
     */
    public SameMessage GetMessage(int ProblemID) {
        SameMessage AnsList = new SameMessage();
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "select title,source," +
                    "accept_number,attempt_number from t_problem where problem_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, ProblemID);
            GetResultSet = GetPreparedStatement.executeQuery();
            if (GetResultSet.next()) {
                AnsList.AcceptNumber = GetResultSet.getInt("accept_number");
                AnsList.AttemptNumber = GetResultSet.getInt("attempt_number");
                AnsList.source = GetResultSet.getString("source");
                AnsList.title = GetResultSet.getString("title");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return AnsList;
    }
}
