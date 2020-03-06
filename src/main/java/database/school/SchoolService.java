package database.school;

import data.school.School;
import database.Connect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SchoolService implements Cloneable {
    private static SchoolService service = new SchoolService();

    private final String GET_ALL_SCHOOL = "SELECT name FROM schools";

    private SchoolService() {

    }

    public static SchoolService copy() {
        SchoolService temp = null;
        try {
            temp = (SchoolService) service.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public School get() {
        School school = null;
        try {
            Connection connection = Connect.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_SCHOOL);
            school = new School();
            ArrayList<String> schoolList = new ArrayList<>();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                schoolList.add(name);
            }
            school.setSchoolList(schoolList);
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return school;
    }

    public void update(School school) {

    }
}
