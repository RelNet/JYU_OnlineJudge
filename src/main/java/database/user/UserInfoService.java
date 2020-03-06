package database.user;

import data.user.UserInfo;
import data.user.UserType;
import database.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoService implements Cloneable {
    private static UserInfoService service = new UserInfoService();

    private UserInfoService() {

    }

    public static UserInfoService copy() {
        UserInfoService temp = null;
        try {
            temp = (UserInfoService) service.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return temp;
    }

    private final String GET_USER_INFO = "select school, student_id, class, submit, accept, type from user_info where id = ?";

    public UserInfo get(Integer id) {
        UserInfo userInfo = null;
        try {
            Connection connection = Connect.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_USER_INFO);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userInfo = new UserInfo();
                userInfo.setSchool(resultSet.getString("school"));
                userInfo.setStudentId(resultSet.getInt("student_id"));
                userInfo.setClassId(resultSet.getString("class"));
                userInfo.setSubmit(resultSet.getString("submit"));
                userInfo.setAccept(resultSet.getString("accept"));
                userInfo.setType(UserType.IntToEnum(resultSet.getInt("type")));

                userInfo.createAcceptList();
                userInfo.createSubmitList();
                userInfo.createFailedList();
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInfo;
    }
}
