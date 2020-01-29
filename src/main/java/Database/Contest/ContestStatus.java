package Database.Contest;

import Data.Contest.ContestStatusMessage;
import Database.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//比赛状态的大部分方法都和Problemstatus一样，
// 只是需要额外增加更新，通过样例和总样例的方法
public class ContestStatus extends ContestStatusMessage {
    /**
     * @param submitID
     * @param newPassCases
     */
    public void updatePassCases(int submitID, int newPassCases, int newtotalCases) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            GetConnectionDatabase.setAutoCommit(false);
            String sql = "delete from t_contest_status_detail where submit_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, submitID);
            GetPreparedStatement.executeUpdate();
            sql = "insert into t_contest_status_detail(submit_id,pass_case,total_case) values(?,?,?)";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, submitID);
            GetPreparedStatement.setInt(2, newPassCases);
            GetPreparedStatement.setInt(3, newtotalCases);
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
}
