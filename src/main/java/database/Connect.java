package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private static Connect connect;

    private String ip;
    private String user;
    private String password;
    private String port;
    private String schema;
    private String connectStr;
    private String driver = "com.mysql.cj.jdbc.Driver";

    private Connect(String ip, String user, String password, String port, String schema) {
        this.ip = ip;
        this.user = user;
        this.password = password;
        this.port = port;
        this.schema = schema;
        this.connectStr = "jdbc:mysql://" + ip + ":" + port + "/" + schema + "?serverTimezone=UTC";
    }

    /**
     * 设置连接器的信息
     *
     * @param ip
     * @param user
     * @param port
     * @param schema
     * @Warning 警告，仅仅用于初始化或更新
     */
    public static void init(String ip, String user, String password, String port, String schema) {
        connect = new Connect(ip, user, password, port, schema);
    }

    public static Connect copy() {
        Connect temp = null;
        try {
            temp = (Connect) connect.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            System.out.println("拷贝database的Connect时出错");
        }
        return temp;
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(connect.driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(connect.connectStr, connect.user, connect.password);
    }
}
