package Database;

import java.sql.*;
/*
* 工具类，连接数据库*/
public class JdbcConnection {
    private JdbcConnection(){}

    static {//注册驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public  static  Connection Get_Connection()throws SQLException {
        //获取链接
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ojtext_database?serverTimezone=UTC",
                "root", "cy6666330"  );
    }
    public static  void Free( ResultSet rs  , Statement stm,Connection con){
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(stm != null){
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(con != null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static  void Free( Statement stm,Connection con){
        if(stm != null){
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(con != null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
