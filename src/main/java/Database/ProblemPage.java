package Database;

import Database.OJProblemStatus.ProblemStatus;
import JudgeSystem.JudgeLanguage;
import JudgeSystem.JudgeMode;
import JudgeSystem.ProblemType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProblemPage {
    public int problem_id;
    public String title;
    public String source;
    public ProblemType type;
    public int AcceptNumber;
    public int AttemptNumber;
    public int LastStatus;


    /**
     *
     * @param page
     * @param UserName
     * @return 当前题目的问题信息包含这道题这个用户做完了吗
     *
     */

    public List<ProblemPage> GetProblemPage(int page, String UserName){
        List<ProblemPage> AnsList = new ArrayList<ProblemPage>();
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "select p.problem_id as pi, sp.status as st,p.source as s,p.problem_type as ty,\n" +
                    "p.accept_number as acn,p.title as ti, p.attempt_number as atn\n" +
                    "from t_problem p left join t_sp_link sp on p.problem_id = sp.problem_id and sp.user_name = ?\n" +
                    "where p.problem_id between "+(1+(page-1)*25)+" and "+(1+(page-1)*25 + 25)+" order by p.problem_id; \n";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1,UserName);
            GetResultSet = GetPreparedStatement.executeQuery();
            int LastStatus = 0;
            int LastProblemID = -1;
            boolean flag = false;ProblemPage temp = new ProblemPage();
            while (GetResultSet.next()) {
                temp = new ProblemPage();
                if(LastProblemID == GetResultSet.getInt("pi")){
                    flag = false;
                    if(GetResultSet.getInt("st") == 1)
                    LastStatus = 1;
                }
                else {
                   AnsList.add(temp);flag = true;
                   LastProblemID = GetResultSet.getInt("pi");
                    if(GetResultSet.getInt("st") == 1)
                        LastStatus = 1;
                    else
                    LastStatus = 0;
                    //problem_id, submit_time, language, run_time, run_memory,code,status,judge_mode,user_name,contest_id
                       temp.AcceptNumber = GetResultSet.getInt("acn");
                temp.AttemptNumber =  GetResultSet.getInt("atn");
                temp.source = GetResultSet.getString("s");
                    temp.LastStatus = GetResultSet.getInt("st");
                      temp.title = GetResultSet.getString("ti");
                    temp.problem_id = GetResultSet.getInt("pi");
                     temp.type = ProblemType.IntToEnum(GetResultSet.getInt("ty"));
                }
            }
            if(!flag)AnsList.add(temp);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return AnsList;
    }

}
