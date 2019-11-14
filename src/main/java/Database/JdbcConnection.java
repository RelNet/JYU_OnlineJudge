package Database;

import java.sql.*;

/*
 * 工具类，连接数据库*/
public class JdbcConnection {
    public static String use = null;
    public static String password = null;
    public static String ip = null;
    public static String port = null;
    public static String databasename = null;

    public static String getUse() {
        return use;
    }

    public static void setUse(String use) {
        JdbcConnection.use = use;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        JdbcConnection.password = password;
    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        JdbcConnection.ip = ip;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        JdbcConnection.port = port;
    }

    public static String getDatabasename() {
        return databasename;
    }

    public static void setDatabasename(String databasename) {
        JdbcConnection.databasename = databasename;
    }

    public Connection Get_Connection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
<<<<<<< HEAD
        //获取链接
        return DriverManager.getConnection("jdbc:mysql://"+ip+":"+port+"/"+databasename+"?serverTimezone=UTC",
                use, password);
=======
    }

    public Connection Get_Connection() throws SQLException {
        //获取链接
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ojtext_database?serverTimezone=UTC",
                "root", "cy6666330");
>>>>>>> dcd63d6314ae4f6bbc24242c63ac69c0ab392cae
    }

    public void Free(ResultSet rs, Statement stm, Connection con) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stm != null) {
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void Free(Statement stm, Connection con) {
        if (stm != null) {
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
