package Database.OjUserMessage;

import Database.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetUserMessage {
    /**
     * public boolean CheckUserName(String UserName);
     * 查询用户名有没有重复
     * true表示用户名在数据库存在可用
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
     * GetUserClass方法就是传入一个UserName(已经存在的用户名)；
     * 返回的就是Class的名字
     */
    public String GetUserClass(String UserName) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        String UserClass = null;
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            //在t_student_detail这张表找用户名是UserName的班级
            String sql = "select class from t_student_detail where user_name = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1, UserName);
            GetResultSet = GetPreparedStatement.executeQuery();
            if (GetResultSet.next()) {
                UserClass = GetResultSet.getString("class");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return UserClass;
    }

    /**
     * GetUserRealname方法就是传入一个UserName(已经存在的用户名)；
     * 返回的就是Realname的名字
     */
    public String GetUserRealname(String UserName) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        String Realname = null;
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            //在t_student_detail这张表找用户名是UserName的班级
            String sql = "select real_name from t_student_detail where user_name = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1, UserName);
            GetResultSet = GetPreparedStatement.executeQuery();
            if (GetResultSet.next()) {
                Realname = GetResultSet.getString("real_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return Realname;
    }

    /**
     * GetUserAcademy方法就是传入一个UserName(已经存在的用户名)；
     * 返回的就是Academy的名字
     */
    public String GetUserAcademy(String UserName) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        String Academy = null;
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            //在t_student_detail这张表找用户名是UserName的班级
            String sql = "select academy from t_student_detail where user_name = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1, UserName);
            GetResultSet = GetPreparedStatement.executeQuery();
            if (GetResultSet.next()) {
                Academy = GetResultSet.getString("academy");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return Academy;
    }

    /**
     * GetUserAcademy方法就是传入一个UserName(已经存在的用户名)；
     * 返回的就是Password
     */
    public String GetUserPassword(String UserName) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        String Password = null;
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            //在t_student_detail这张表找用户名是UserName的班级
            String sql = "select password from t_student where user_name = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1, UserName);
            GetResultSet = GetPreparedStatement.executeQuery();
            if (GetResultSet.next()) {
                Password = GetResultSet.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return Password;
    }
}
