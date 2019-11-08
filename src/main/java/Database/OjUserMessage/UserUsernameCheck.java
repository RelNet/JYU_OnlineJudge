package Database.OjUserMessage;

import Database.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserUsernameCheck {
    /* public boolean CheckUserName(String UserName);
     * 查询用户名有没有重复
     * true表示用户名可用
     * false 表示不可用
     * */
    public boolean CheckUserName(String UserName){
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        boolean flag  = true;
        try {
            //注册驱动, 获取连接
            GetConnectionDatabase = JdbcConnection.Get_Connection();
            //查询传进来的参数UserName有没有在用户表里出现过
            String sql  = "select user_name from t_student where user_name = ?";
            //获取数据库操作对象
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1,UserName);
            GetResultSet = GetPreparedStatement.executeQuery();
            //看看表里有没有相同的用户名
            if(GetResultSet.next()){
                flag = false;
            }
        } catch (SQLException e) {
            flag = false;
            e.printStackTrace();
        }finally {
            JdbcConnection.Free(GetResultSet,GetPreparedStatement,GetConnectionDatabase);
        }
        return flag;
    }
}
