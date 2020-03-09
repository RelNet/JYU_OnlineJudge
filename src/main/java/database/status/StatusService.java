package database.status;

import data.status.Status;
import data.submit.Submit;
import database.Connect;
import judge.JudgeMode;
import judge.JudgeSystemConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StatusService implements Cloneable {
    private static StatusService service = new StatusService();

    private StatusService() {

    }

    public static StatusService copy() {
        StatusService temp = null;
        try {
            temp = (StatusService) service.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return temp;
    }

    private final String GET_STATUS = "select  * from status order by id desc limit ";

    public Status get(Integer contestId, Integer page) {
        Status status = new Status();
        ArrayList<Submit> submits = new ArrayList<>();
        status.setSubmits(submits);
        Integer start = (page - 1) * 50;
        Integer end = page * 50;
        try {
            Connection connection = Connect.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_STATUS + start.toString() + ", " + end.toString());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Submit temp = new Submit();
                temp.setConstant(JudgeSystemConstant.IntToEnum(resultSet.getInt("status")));
                temp.setId(resultSet.getInt("id"));
                temp.setProblemId(resultSet.getInt("problem_id"));
                temp.setTime(resultSet.getTimestamp("time"));
                temp.setUserId(resultSet.getInt("user_id"));
                submits.add(temp);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
}
