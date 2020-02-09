package Database.Team;

import Data.Team.TeamMessage;
import Database.JdbcConnection;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Team extends TeamMessage implements Serializable {
    /**
     * 下面你的其他方法使用前最好都先用下这个检查用户名
     * 判断这个用户有没有属于这个团队中
     * 如果这个用户已经参加了这个团队，返回true
     * 如果没参加返回false
     * @param teamID
     * @param studentName
     * @return
     */
    public boolean checkStudent(int teamID, String studentName){
        Connection GetConnectionDatabase = null;
        ResultSet GetResultSet = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        boolean flag  = false;
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            GetConnectionDatabase.setAutoCommit(false);
            String sql = "select * from t_ts_link where team_id = ? and user_name = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, teamID);
            GetPreparedStatement.setString(2, studentName);
            GetResultSet = GetPreparedStatement.executeQuery();
            if(GetResultSet.next()){
                flag = true;
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
        return flag;
    }

    /**
     * 传入一个Team里面的所有信息（除了team——id）插入数据库
     * 返回值就是插入后的team_id;
     *
     * @param newTeam
     */
    public int insertDatabase(TeamMessage newTeam) {
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
            String sql = "insert into t_team(team_name , leader , hash) values(?,?,?)";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            GetPreparedStatement.setString(1, newTeam.teamName);
            GetPreparedStatement.setString(2, newTeam.leader);
            GetPreparedStatement.setString(3, newTeam.hash);
            GetPreparedStatement.executeUpdate();
            GetResultSet = GetPreparedStatement.getGeneratedKeys();
            if (GetResultSet.next()) {
                Key = GetResultSet.getInt(1);
            }
            //此时的key就是插入的team_id;

            sql = "insert into t_ts_link (team_id , user_name) values(?,?)";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, Key);
            for (String temp : newTeam.studentList) {
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
        return Key;
    }

    /**
     * 传入团队id和List，
     * 这个团队能参加的人就是这个传进来的List的人
     * 先前存在数据库的人已经删除
     *
     * @param teamID
     * @param newMember
     */
    public void updateAllTeamMember(int teamID, List<String> newMember) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            GetConnectionDatabase.setAutoCommit(false);
            String sql = "delete from t_ts_link where team_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, teamID);
            GetPreparedStatement.executeUpdate();
            sql = "insert into t_ts_link (team_id , user_name) values(?,?)";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, teamID);
            for (String temp : newMember) {
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

    /**
     * 传入比赛ID和新的用户的ID
     * 将这个用户加入这个团队
     * @param teamID
     * @param studentName
     */
    public void insertOneTeamMember(int teamID, String studentName) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            GetConnectionDatabase.setAutoCommit(false);
            String sql = "insert into t_ts_link (team_id , user_name) values(?,?)";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, teamID);
            GetPreparedStatement.setString(2, studentName);
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

    /**
     * 传入比赛ID和新的用户的ID
     * 将这个用户踢出该团队
     * @param teamID
     * @param studentName
     */
    public void deleteOneTeamMember(int teamID, String studentName){
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            GetConnectionDatabase.setAutoCommit(false);
            String sql = "delete from t_ts_link where team_id = ? and user_name = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, teamID);
            GetPreparedStatement.setString(2, studentName);
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

    /**
     * 传入一个用户名，返回一个list，包含该用户所参加的所有团队
     * @param studentName
     * @return
     */
    public List<Integer> getStudentAllTeam(String studentName){
        Connection GetConnectionDatabase = null;
        ResultSet GetResultSet = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        List<Integer> ans  = new ArrayList<>();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            GetConnectionDatabase.setAutoCommit(false);
            String sql = "select team_id from t_ts_link where  user_name = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1, studentName);
            GetResultSet = GetPreparedStatement.executeQuery();
            while (GetResultSet.next()){
               ans.add(GetResultSet.getInt("team_id"));
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
        return ans;
    }

    /**
     * 传入一个比赛id
     * 返回一个list，这个list包含了所有这个比赛的username
     * @param teamID
     * @return
     */
    public List<String> getTeamAllStudent(int teamID){
        Connection GetConnectionDatabase = null;
        ResultSet GetResultSet = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        List<String> ans  = new ArrayList<>();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            GetConnectionDatabase.setAutoCommit(false);
            String sql = "select user_name from t_ts_link where  team_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, teamID);
            GetResultSet = GetPreparedStatement.executeQuery();
            while (GetResultSet.next()){
                ans.add(GetResultSet.getString("user_name"));
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
        return ans;
    }
}
