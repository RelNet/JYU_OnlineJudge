package Database.Contest;

import Data.Contest.MainContest;
import Data.Problems.InputOutput;
import Database.JdbcConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContestMessage extends MainContest {
    /**
     * 传入一个ContestMessage的变量，里面的字段信息都要有，才能存入数据库，password可为空
     * 把这个的所有信息传入数据库
     * 用户名和题目id都必须在先前数据库有存在过
     *
     * @param NewContest
     */
    public void insertDatabase(ContestMessage NewContest) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        int Key = 0;
        int count = 0;
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            //开启事务
            GetConnectionDatabase.setAutoCommit(false);
            String sql = "insert into t_contest (contest_name) values(?)";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            GetPreparedStatement.setString(1, NewContest.ContestName);
            GetPreparedStatement.executeUpdate();
            GetResultSet = GetPreparedStatement.getGeneratedKeys();
            if (GetResultSet.next()) {
                Key = GetResultSet.getInt(1);
            }
            //此时的key就是插入的contest_id;
            //插入比赛的细节信息
            sql = "insert into t_contest_detail (contest_id, start_time, end_time," +
                    "leader,type,password) values(?,?,?,?,?,?)";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, Key);
            GetPreparedStatement.setDate(2, NewContest.StartTime);
            GetPreparedStatement.setDate(3, NewContest.EndTime);
            GetPreparedStatement.setString(4, NewContest.leader);
            GetPreparedStatement.setInt(5, NewContest.mode.ordinal());
            GetPreparedStatement.setString(6, NewContest.ContestPassword);
            GetPreparedStatement.executeUpdate();
            //比赛题目插入到数据库
            for (Integer temp : NewContest.ProblemList) {
                sql = "insert into t_contest_problems(contest_id,problem_id) values(?,?)";
                GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
                GetPreparedStatement.setInt(1, Key);
                GetPreparedStatement.setInt(2, temp);
                GetPreparedStatement.executeUpdate();
            }
            //比赛有谁参加插入到数据库
            for (String temp : NewContest.ContestStuduent) {
                sql = "insert into t_contest_student_link(contest_id,user_name) values(?,?)";
                GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
                GetPreparedStatement.setInt(1, Key);
                GetPreparedStatement.setString(2, temp);
                GetPreparedStatement.executeUpdate();
            }
            GetConnectionDatabase.commit();
        } catch (SQLException e) {
            if (GetConnectionDatabase != null) {
                //提交插入不成功
                //回滚事务
                try {
                    GetConnectionDatabase.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
    }

    /**
     * @param contestID
     * @param newProblemLink newProblemLink 是指这个比赛新的全部的题目，就是更新contestID这个比赛的所有题目
     */
    public void updateContestProblemsLink(int contestID, List<Integer> newProblemLink) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            GetConnectionDatabase.setAutoCommit(false);
            String sql = "delete from t_contest_problems where contest_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, contestID);
            GetPreparedStatement.executeUpdate();
            //比赛题目插入到数据库
            for (Integer temp : newProblemLink) {
                sql = "insert into t_contest_problems(contest_id,problem_id) values(?,?)";
                GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
                GetPreparedStatement.setInt(1, contestID);
                GetPreparedStatement.setInt(2, temp);
                GetPreparedStatement.executeUpdate();
            }
            GetConnectionDatabase.commit();
        } catch (SQLException e) {
            if (GetConnectionDatabase != null) {
                //提交插入不成功
                //回滚事务
                try {
                    GetConnectionDatabase.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetPreparedStatement, GetConnectionDatabase);
        }
    }

    /**
     * @param contestID
     * @param newProblem 对于一个题目，保留原先有的题目加上一个额外的题目，题目ID是newProblemLink
     */
    public void insertContestProblem(int contestID, Integer newProblem) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            GetConnectionDatabase.setAutoCommit(false);
            //比赛题目插入到数据库
            String sql = "insert into t_contest_problems(contest_id,problem_id) values(?,?)";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, contestID);
            GetPreparedStatement.setInt(2, newProblem);
            GetPreparedStatement.executeUpdate();

            GetConnectionDatabase.commit();
        } catch (SQLException e) {
            if (GetConnectionDatabase != null) {
                //提交插入不成功
                //回滚事务
                try {
                    GetConnectionDatabase.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetPreparedStatement, GetConnectionDatabase);
        }
    }

    /**
     * 对于一个已经存在的比赛，删除一个原本就在的题目，deleteProblem就是要被删除的题目ID
     *
     * @param contestID
     * @param deleteProblem
     */
    public void deleteContestProblem(int contestID, Integer deleteProblem) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            GetConnectionDatabase.setAutoCommit(false);
            //比赛题目插入到数据库
            String sql = "delete from t_contest_problems where contest_id = ? and problem_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, contestID);
            GetPreparedStatement.setInt(2, deleteProblem);
            GetPreparedStatement.executeUpdate();
            GetConnectionDatabase.commit();
        } catch (SQLException e) {
            if (GetConnectionDatabase != null) {
                //提交插入不成功
                //回滚事务
                try {
                    GetConnectionDatabase.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetPreparedStatement, GetConnectionDatabase);
        }
    }

    /**
     * 更改所有能够参加比赛的人，
     * 原先能够参加的人已经被删除，newContestStudents就是现在能够参加这个比赛的所有人。
     *
     * @param contestID
     * @param newContestStudents
     */
    public void updateContestStudents(int contestID, List<String> newContestStudents) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            GetConnectionDatabase.setAutoCommit(false);
            String sql = "delete from t_contest_student_link where contest_id = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, contestID);
            GetPreparedStatement.executeUpdate();
            //比赛题目插入到数据库
            for (String temp : newContestStudents) {
                sql = "insert into t_contest_student_link(contest_id,user_name) values(?,?)";
                GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
                GetPreparedStatement.setInt(1, contestID);
                GetPreparedStatement.setString(2, temp);
                GetPreparedStatement.executeUpdate();
            }
            GetConnectionDatabase.commit();
        } catch (SQLException e) {
            if (GetConnectionDatabase != null) {
                //提交插入不成功
                //回滚事务
                try {
                    GetConnectionDatabase.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetPreparedStatement, GetConnectionDatabase);
        }
    }

    /**
     * 在原先已经有的参加比赛的人员基础上
     * 在额外加入一名学生newContestStudent
     *
     * @param contestID
     * @param newContestStudent
     */
    public void insertNewContestStudents(int contestID, String newContestStudent) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            GetConnectionDatabase.setAutoCommit(false);
            //比赛题目插入到数据库
            String sql = "insert into t_contest_student_link(contest_id,user_name) values(?,?)";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, contestID);
            GetPreparedStatement.setString(2, newContestStudent);
            GetPreparedStatement.executeUpdate();
            GetConnectionDatabase.commit();
        } catch (SQLException e) {
            if (GetConnectionDatabase != null) {
                //提交插入不成功
                //回滚事务
                try {
                    GetConnectionDatabase.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetPreparedStatement, GetConnectionDatabase);
        }
    }

    /**
     * 删除某个比赛的某一个学生
     * deletestudent 是即将被删除的学生的名字
     *
     * @param contestID
     * @param deletestudent
     */
    public void deleteConteststudent(int contestID, String deletestudent) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            GetConnectionDatabase.setAutoCommit(false);
            //比赛题目插入到数据库
            String sql = "delete from t_contest_student_link where contest_id = ? and user_name = ?";
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, contestID);
            GetPreparedStatement.setString(2, deletestudent);
            GetPreparedStatement.executeUpdate();
            GetConnectionDatabase.commit();
        } catch (SQLException e) {
            if (GetConnectionDatabase != null) {
                //提交插入不成功
                //回滚事务
                try {
                    GetConnectionDatabase.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetPreparedStatement, GetConnectionDatabase);
        }
    }

    /**
     * 查询某个学生是否参加了某个比赛
     * studentNmae 是查询学生的名字， contestID是比赛的id
     * 返回值是true表示该学生参加了该比赛
     * 返回值是false表示该学生没有参加该比赛
     *
     * @param contestID
     * @return
     */
    public boolean checkStudentContest(int contestID, String studentName) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        boolean flag = false;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            //在t_student表里找与UserName 和 password都对应上的行
            String sql = "select * from t_contest_student_link where user_name = ? and contest_id = ?";
            //获取数据库操作对象
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1, studentName);
            GetPreparedStatement.setInt(2, contestID);
            GetResultSet = GetPreparedStatement.executeQuery();
            //能找出一行，说明在数据库存在
            if (GetResultSet.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            flag = true;
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return flag;
    }

    /**
     * 传入一个学生的studentName
     * 返回一个List这个list里面含有所有的该学生参加过的比赛
     *
     * @param studentName
     * @return
     */
    public List<Integer> getStudentAllContest(String studentName) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        List<Integer> ansList = new ArrayList<>();
        boolean flag = false;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            String sql = "select contest_id from t_contest_student_link where user_name = ? ";
            //获取数据库操作对象
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setString(1, studentName);
            GetResultSet = GetPreparedStatement.executeQuery();
            //能找出一行，说明在数据库存在
            while (GetResultSet.next()) {
                ansList.add(GetResultSet.getInt("contest_id"));
            }
        } catch (SQLException e) {
            flag = true;
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return ansList;
    }


    /**
     * 传入比赛ID
     * 返回一个list这个list保存着所有参加该比赛的学生的名字
     *
     * @param contestID
     * @return
     */
    public List<String> getContestAllStudent(int contestID) {
        Connection GetConnectionDatabase = null;
        PreparedStatement GetPreparedStatement = null;
        ResultSet GetResultSet = null;
        List<String> ansList = new ArrayList<>();
        boolean flag = false;
        JdbcConnection JdbcLink = new JdbcConnection();
        try {
            GetConnectionDatabase = JdbcLink.Get_Connection();
            //在t_student表里找与UserName 和 password都对应上的行
            String sql = "select user_name from t_contest_student_link where contest_id = ? ";
            //获取数据库操作对象
            GetPreparedStatement = GetConnectionDatabase.prepareStatement(sql);
            GetPreparedStatement.setInt(1, contestID);
            GetResultSet = GetPreparedStatement.executeQuery();
            //能找出一行，说明在数据库存在
            while (GetResultSet.next()) {
                ansList.add(GetResultSet.getString("user_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcLink.Free(GetResultSet, GetPreparedStatement, GetConnectionDatabase);
        }
        return ansList;
    }


}
