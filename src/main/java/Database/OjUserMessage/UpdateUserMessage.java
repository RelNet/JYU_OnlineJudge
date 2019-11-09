package Database.OjUserMessage;

import Database.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateUserMessage {
    /** public boolean CheckUserName(String UserName);
     * 查询用户名有没有重复
     * true表示用户名在数据库存在可用
     * false 表示用户名在数据库不存在
     * */
    public boolean CheckUserName(String UserName){
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        boolean flag  = false;
        try {
            //注册驱动, 获取连接
            GetConnectionDatabase = JdbcLink.Get_Connection();
            //查询传进来的参数UserName有没有在用户表里出现过
            String sql  = "select user_name from t_student where user_name = ?";
            //获取数据库操作对象
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1,UserName);
            GetResultSet = GetPreparedStatement.executeQuery();
            //看看表里有没有相同的用户名
            if(GetResultSet.next()){
                flag = true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            JdbcLink.Free(GetResultSet,GetPreparedStatement,GetConnectionDatabase);
        }
        return flag;
    }


    public boolean UpdatePassword(String UserName , String NewPassword){
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        int count = 0;
        boolean flag  = true;
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "update t_student set password = ? where user_name = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1,NewPassword);
            GetPreparedStatement.setString(2,UserName);
            count = GetPreparedStatement.executeUpdate();
            if(count == 0){
               flag  = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;

    }

}
