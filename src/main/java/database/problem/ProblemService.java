package database.problem;

import data.problem.Problem;
import data.problem.ProblemInfo;
import database.Connect;
import judge.ProblemType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProblemService implements Cloneable {
    private static ProblemService service = new ProblemService();

    private ProblemService() {

    }

    public static ProblemService copy() {
        ProblemService temp = null;
        try {
            temp = (ProblemService) service.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return temp;
    }

    private final String GET_PROBLEM = "select * from problem, problem_info where problem.id = ? and problem_info.id = ?";

    public Problem get(Integer id) {
        Problem problem = null;
        try {
            Connection connection = Connect.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_PROBLEM);
            statement.setInt(1, id);
            statement.setInt(2, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                problem = new Problem();
                ProblemInfo problemInfo = new ProblemInfo();
                problem.setId(id);
                problem.setTitle(resultSet.getString("title"));
                problem.setSource(resultSet.getString("source"));
                problem.setAccept(resultSet.getInt("accept"));
                problem.setSubmit(resultSet.getInt("submit"));
                problemInfo.setDescription(resultSet.getString("description"));
                problemInfo.setType(ProblemType.IntToEnum(resultSet.getInt("type")));
                problemInfo.setMemory(resultSet.getInt("mem"));
                problemInfo.setTimeout(resultSet.getInt("timeout"));
                problemInfo.setSpaces(resultSet.getInt("space"));
                problemInfo.setInputOutputs(resultSet.getString("input"), resultSet.getString("output"));
                problemInfo.setInputDescription(resultSet.getString("input_des"));
                problemInfo.setOutputDescription(resultSet.getString("output_des"));
                problemInfo.setOptions(resultSet.getString("options"));
                problem.setProblemInfo(problemInfo);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return problem;
    }
}
