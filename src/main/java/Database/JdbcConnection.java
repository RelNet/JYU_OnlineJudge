package Database;

import java.sql.*;

/*
 * 工具类，连接数据库*/
public class JdbcConnection {
    public static String user = null;
    public static String password = null;
    public static String ip = null;
    public static String port = null;
    public static String databasename = null;

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        JdbcConnection.user = user;
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
        //获取链接
        return DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + databasename + "?serverTimezone=UTC",
                user, password);

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
