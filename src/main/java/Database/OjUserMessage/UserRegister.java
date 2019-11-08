package Database.OjUserMessage;


import Database.JdbcConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRegister {
    /*register方法
    插入新用户所有详细信息（除了UserName和password其他可以为null
    * */
    public  boolean register(String UserName , String password, String ClassName , String RealName , String Academy ,String StudentId){
        Connection GetConnectionDatabase = null;
        PreparedStatement  GetPreparedStatement = null;
        boolean flag  = true;
        try {
            //注册驱动, 获取连接
            GetConnectionDatabase = JdbcConnection.Get_Connection();
            //开启事务，只有插入两个表能同时成功才插入
            GetConnectionDatabase.setAutoCommit(false);
            //向t_student 表插入用户名和密码
            String sql = "insert into t_student (user_name , password) values (?, ?)";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1,UserName);
            GetPreparedStatement.setString(2,password);
            GetPreparedStatement.executeUpdate();
            //向t_student_detail表插入详细信息
            sql = "insert into t_student_detail (user_name , class, real_name , academy, student_id) values (?, ? , ?, ? ,?)";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1,UserName);
            GetPreparedStatement.setString(2,ClassName);
            GetPreparedStatement.setString(3,RealName);
            GetPreparedStatement.setString(4,Academy);
            GetPreparedStatement.setString(5,StudentId);
            GetPreparedStatement.executeUpdate();
            GetConnectionDatabase.commit();
            //提交事务

        } catch (SQLException e) {
            flag = false;
            if(GetConnectionDatabase != null){
                //提交插入不成功
                //回滚事务
                try {
                    GetConnectionDatabase.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }finally {
            JdbcConnection.Free(GetPreparedStatement,GetConnectionDatabase);
        }
        return flag;
    }

}