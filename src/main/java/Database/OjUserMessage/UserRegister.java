
package Database.OjUserMessage;


import Database.JdbcConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRegister {
    /* public boolean CheckUserName(String UserName);
     * 查询用户名有没有重复
     * true表示用户名在数据库存在
     * false 表示用户名在数据库不存在
     * */
    public boolean CheckUserName(String UserName){
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        boolean flag  = false;
        JdbcConnection JdbcLink = new JdbcConnection();
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

    /*register方法
    插入新用户所有详细信息（除了UserName和password其他可以为null
    * */
    public  boolean register(String UserName , String password, String ClassName , String RealName , String Academy ,String StudentId){
        Connection GetConnectionDatabase = null;
        PreparedStatement  GetPreparedStatement = null;
        boolean flag  = true;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            //注册驱动, 获取连接
            GetConnectionDatabase = JdbcLink.Get_Connection();
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
            JdbcLink.Free(GetPreparedStatement,GetConnectionDatabase);
        }
        return flag;
    }

}