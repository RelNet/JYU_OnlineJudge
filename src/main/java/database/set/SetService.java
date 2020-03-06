package database.set;

import data.problem.Problem;
import database.Connect;

import java.sql.*;
import java.util.ArrayList;

public class SetService implements Cloneable {
    private static SetService service = new SetService();

    private SetService() {

    }

    public static SetService copy() {
        SetService temp = null;
        try {
            temp = (SetService) service.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return temp;
    }

    private final String GET_SET = "select id, title, submit, accept, source from problem where id between ";
    private final String GET_MAX_PAGE = "select count(*) from problem";

    public ArrayList<Problem> get(Integer page) {
        ArrayList<Problem> list = new ArrayList<>();
        try {
            Connection connection = Connect.getConnection();
            Integer start = (page - 1) * 50 + 1;
            Integer end = page * 50;
            PreparedStatement statement = connection.prepareStatement(GET_SET + start.toString() + " and " + end.toString());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Problem temp = new Problem();
                temp.setId(resultSet.getInt("id"));
                temp.setTitle(resultSet.getString("title"));
                temp.setSubmit(resultSet.getInt("submit"));
                temp.setAccept(resultSet.getInt("accept"));
                temp.setSource(resultSet.getString("source"));
                list.add(temp);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Integer maxPage() {
        Integer max = -1;
        try {
            Connection connection = Connect.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_MAX_PAGE);
            if (resultSet.next()) {
                max = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (max / 50) + ((max % 50) > 0 ? 1 : 0);
    }
}
