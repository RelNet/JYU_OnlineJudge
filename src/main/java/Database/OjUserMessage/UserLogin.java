package Database.OjUserMessage;


import Database.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLogin {
    /**
     * CheckUserLogin方法就是检查数据库有没有存在传进来的UserName和password相对于的行
     */

    public boolean CheckUserLogin(String UserName , String password){
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        boolean flag  = false;
        try {
            GetConnectionDatabase = JdbcConnection.Get_Connection();
            //在t_student表里找与UserName 和 password都对应上的行
            String sql  = "select user_name from t_student where user_name = ? and password = ?";
            //获取数据库操作对象
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1,UserName);
            GetPreparedStatement.setString(2,password);
            GetResultSet = GetPreparedStatement.executeQuery();
            //能找出一行，说明在数据库存在
            if(GetResultSet.next()){
                flag = true;
            }
        } catch (SQLException e) {
            flag = true;
            e.printStackTrace();
        }finally {
            JdbcConnection.Free(GetResultSet,GetPreparedStatement,GetConnectionDatabase);
        }
        return flag;
    }

}
