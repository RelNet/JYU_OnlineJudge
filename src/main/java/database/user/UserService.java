package database.user;


import data.user.User;
import data.user.UserInfo;
import database.Connect;

import java.sql.*;

public class UserService implements Cloneable {
    private static UserService service = new UserService();

    private final String INSERT_USER = "insert into user (username, password) values (?,?)";
    private final String INSERT_USER_INFO = "insert into user_info (user_id, school, student_id, class) values (?,?,?,?)";
    private final String CHECK_USER = "select id, username from user where id = ? and password = ?";

    private UserService() {

    }

    public static UserService copy() {
        UserService temp = null;
        try {
            temp = (UserService) service.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public Integer insert(User user, UserInfo userInfo) {
        int id = -1;
        try {
            Connection connection = Connect.getConnection();
            id = insertUser(user, connection);
            insertInfo(id, userInfo, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public Integer insertUser(User user, Connection connection) {
        int id = -1;
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void insertInfo(Integer id, UserInfo userInfo, Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_USER_INFO);
            statement.setInt(1, id);
            statement.setString(2, userInfo.getSchool());
            statement.setInt(3, userInfo.getStudentId());
            statement.setString(4, userInfo.getClassId());
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查是否存在这个用户，并将检查id和密码是否对应
     * 如果验证通过，将会修改 user.username
     *
     * @param user
     * @return true说明验证通过
     */
    public boolean check(User user) {

        boolean che = false;
        try {
            Connection connection = Connect.getConnection();
            PreparedStatement statement = connection.prepareStatement(CHECK_USER);
            statement.setInt(1, user.getId());
            statement.setString(2, user.getPassword());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                che = true;
                user.setUsername(resultSet.getString(2));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return che;
    }
}
