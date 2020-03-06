

package old.Database.OjUserMessage;


import old.Database.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLogin {
    /**
     * public boolean CheckUserName(String UserName);
     * 查询用户名有没有重复
     * true表示用户名在数据库存在
     * false 表示用户名在数据库不存在
     */
    public boolean CheckUserName(String UserName) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        boolean flag = false;
        try {
            //注册驱动, 获取连接
            GetConnectionDatabase = JdbcLink.Get_Connection();
            //查询传进来的参数UserName有没有在用户表里出现过
            String sql = "select user_name from t_student where user_name = ?";
            //获取数据库操作对象
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1, UserName);
            GetResultSet = GetPreparedStatement.executeQuery();
            //看看表里有没有相同的用户名
            if (GetResultSet.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return flag;
    }


    /**
     * CheckUserLogin方法就是检查数据库有没有存在传进来的UserName和password相对于的行
     */

    public boolean CheckUserLogin(String UserName, String password) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        boolean flag = false;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            //在t_student表里找与UserName 和 password都对应上的行
            String sql = "select user_name from t_student where user_name = ? and password = ?";
            //获取数据库操作对象
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1, UserName);
            GetPreparedStatement.setString(2, password);
            GetResultSet = GetPreparedStatement.executeQuery();
            //能找出一行，说明在数据库存在
            if (GetResultSet.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            flag = true;
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return flag;
    }

}