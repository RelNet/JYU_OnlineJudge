package Database.Iinitialize;
import Database.JdbcConnection;

import java.sql.*;

public class InitializeDatabase {
    static String username = null;
    static String ip = null;
    static String port = null;
    static String password = null;
    static String databasename = null;
    public static void main(String[] args){
        //username=******
        //password=******
        //databasename=***
        //ip=***
        //port=**
        //3306
        //ojtext_database
        String temp = null;
        for(int i = 0 ; i < args.length ;i++){
             temp = args[i].substring(0,2);
             if(temp.equals((String) "ip")){
                 ip = args[i].substring(3,args[i].length());
             }
             temp = args[i].substring(0,4);
             if(temp.equals((String) "port")){
                 port = args[i].substring(5,args[i].length());
             }

            if(args[i].length() <= 8)continue;


             temp = args[i].substring(0,8);
            if(temp.equals((String) "username")){
                username = args[i].substring(9,args[i].length());
            }
            if(temp.equals((String) "password")){
                password = args[i].substring(9,args[i].length());
            }
            if(args[i].length() <= 12)continue;
            temp = args[i].substring(0,12);
            if(temp.equals((String) "databasename"))
            databasename = args[i].substring(13,args[i].length());
        }
        CreatDatabase(username, password);
        JdbcConnection.use = username;
        JdbcConnection.password = password;
        JdbcConnection.port = port;
        JdbcConnection.ip = ip;
        JdbcConnection.databasename = databasename;
    }

    public static boolean CreatDatabase(String user,String password) {
        Connection GetConnectionDatabase = null;
        Statement GetStatement = null;
        ResultSet GetResultSet = null;
        boolean flag = true;
        //获取链接
        try {
            /**
             *利用原本存在的mysql数据库，创建一个新数据库
             */
            Class.forName("com.mysql.cj.jdbc.Driver");
            GetConnectionDatabase = DriverManager.getConnection("jdbc:mysql://"+ip+":"+port+"/mysql?serverTimezone=UTC",
                    user, password);
            String sql = " create database ojtext_database";
            GetStatement = GetConnectionDatabase.createStatement();
            GetStatement.executeUpdate(sql);
            GetStatement.close();
            GetConnectionDatabase.close();
            //连接新的数据库,用新的数据库创建表
            GetConnectionDatabase = DriverManager.getConnection("jdbc:mysql://"+ip+":"+port+"/"+databasename+"?serverTimezone=UTC",
                    user, password);
            GetStatement = GetConnectionDatabase.createStatement();
            //开启事务，只有建表语句全部成功才执行
            GetConnectionDatabase.setAutoCommit(false);
            sql = " CREATE TABLE `t_student` (\n" +
                    "  `user_name` varchar(255) NOT NULL,\n" +
                    "  `password` varchar(255) NOT NULL,\n" +
                    "  PRIMARY KEY (`user_name`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            GetStatement.executeUpdate(sql);

            sql = "CREATE TABLE `t_student_detail` (\n" +
                    "  `user_name` varchar(255) NOT NULL,\n" +
                    "  `class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,\n" +
                    "  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,\n" +
                    "  `academy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,\n" +
                    "  `student_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`user_name`),\n" +
                    "  CONSTRAINT `t_student_detail_ibfk_1` FOREIGN KEY (`user_name`) REFERENCES `t_student` (`user_name`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci" ;
            GetStatement.executeUpdate(sql);

            sql = "CREATE TABLE `t_team` (\n" +
                    "  `team_id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `leader` varchar(255) NOT NULL,\n" +
                    "  `team_name` varchar(255) NOT NULL,\n" +
                    "  `hash` varchar(255) NOT NULL,\n" +
                    "  PRIMARY KEY (`team_id`)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            GetStatement.executeUpdate(sql);

            sql = "CREATE TABLE `t_ts_link` (\n" +
                    "  `user_name` varchar(255) NOT NULL,\n" +
                    "  `team_id` int(11) NOT NULL,\n" +
                    "  KEY `user_name` (`user_name`),\n" +
                    "  KEY `team_id` (`team_id`),\n" +
                    "  CONSTRAINT `t_ts_link_ibfk_1` FOREIGN KEY (`user_name`) REFERENCES `t_student` (`user_name`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                    "  CONSTRAINT `t_ts_link_ibfk_2` FOREIGN KEY (`team_id`) REFERENCES `t_team` (`team_id`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            GetStatement.executeUpdate(sql);

            sql = "CREATE TABLE `t_problem` (\n" +
                    "  `problem_id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `title` varchar(255) NOT NULL,\n" +
                    "  `source` varchar(255) DEFAULT NULL,\n" +
                    "  `problem_type` int(11) NOT NULL,\n" +
                    "  `accept_number` int(10) unsigned zerofill DEFAULT NULL,\n" +
                    "  `attempt_number` int(10) unsigned zerofill DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`problem_id`)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            GetStatement.executeUpdate(sql);

            sql = "CREATE TABLE `t_cf_fcf_problem_detail` (\n" +
                    "  `problem_id` int(11) NOT NULL,\n" +
                    "  `problem_describe` text NOT NULL,\n" +
                    "  `input_describe` text NOT NULL,\n" +
                    "  `output_describe` text NOT NULL,\n" +
                    "  `time_limit` varchar(255) NOT NULL,\n" +
                    "  `memory_limit` varchar(255) NOT NULL,\n" +
                    "  PRIMARY KEY (`problem_id`),\n" +
                    "  CONSTRAINT `t_cf_fcf_problem_detail_ibfk_1` FOREIGN KEY (`problem_id`) REFERENCES `t_problem` (`problem_id`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            GetStatement.executeUpdate(sql);

            sql = "CREATE TABLE `t_cf_fcf_problem_sample` (\n" +
                    "  `problem_id` int(11) NOT NULL,\n" +
                    "  `input` varchar(255) NOT NULL,\n" +
                    "  `output` varchar(255) NOT NULL,\n" +
                    "  KEY `problem_id` (`problem_id`),\n" +
                    "  CONSTRAINT `t_cf_fcf_problem_sample_ibfk_1` FOREIGN KEY (`problem_id`) REFERENCES `t_problem` (`problem_id`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            GetStatement.executeUpdate(sql);

            sql = "CREATE TABLE `t_mcq_detail` (\n" +
                    "  `problem_id` int(11) NOT NULL,\n" +
                    "  `problem_option` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n" +
                    "  KEY `problem_id` (`problem_id`),\n" +
                    "  CONSTRAINT `t_mcq_detail_ibfk_1` FOREIGN KEY (`problem_id`) REFERENCES `t_problem` (`problem_id`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            GetStatement.executeUpdate(sql);

            sql = "CREATE TABLE `t_mcq_fb_detail` (\n" +
                    "  `problem_id` int(11) NOT NULL,\n" +
                    "  `problem_describe` text NOT NULL,\n" +
                    "  KEY `problem_id` (`problem_id`),\n" +
                    "  CONSTRAINT `t_mcq_fb_detail_ibfk_1` FOREIGN KEY (`problem_id`) REFERENCES `t_problem` (`problem_id`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            GetStatement.executeUpdate(sql);

            sql = "CREATE TABLE `t_status` (\n" +
                    "  `submit_id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `problem_id` int(11) NOT NULL,\n" +
                    "  `submit_time` date NOT NULL,\n" +
                    "  `language` int(11) DEFAULT NULL,\n" +
                    "  `run_time` int(10) unsigned zerofill DEFAULT NULL,\n" +
                    "  `run_memory` int(10) unsigned zerofill DEFAULT NULL,\n" +
                    "  `code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,\n" +
                    "  `status` int(11) NOT NULL,\n" +
                    "  PRIMARY KEY (`submit_id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            GetStatement.executeUpdate(sql);

            sql = "CREATE TABLE `t_sp_link` (\n" +
                    "  `user_name` varchar(255) NOT NULL,\n" +
                    "  `problem_id` int(11) NOT NULL,\n" +
                    "  `submit_id` int(11) NOT NULL,\n" +
                    "  KEY `user_name` (`user_name`),\n" +
                    "  KEY `problem_id` (`problem_id`),\n" +
                    "  KEY `submit_id` (`submit_id`),\n" +
                    "  CONSTRAINT `t_sp_link_ibfk_1` FOREIGN KEY (`user_name`) REFERENCES `t_student` (`user_name`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                    "  CONSTRAINT `t_sp_link_ibfk_2` FOREIGN KEY (`problem_id`) REFERENCES `t_problem` (`problem_id`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                    "  CONSTRAINT `t_sp_link_ibfk_3` FOREIGN KEY (`submit_id`) REFERENCES `t_status` (`submit_id`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            GetStatement.executeUpdate(sql);

            sql = "CREATE TABLE `t_mcq_fb_answer` (\n" +
                    "  `problem_id` int(11) NOT NULL,\n" +
                    "  `answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n" +
                    "  `number` int(11) DEFAULT NULL,\n" +
                    "  KEY `problem_id` (`problem_id`),\n" +
                    "  CONSTRAINT `t_mcq_fb_answer_ibfk_1` FOREIGN KEY (`problem_id`) REFERENCES `t_problem` (`problem_id`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            GetStatement.executeUpdate(sql);

            sql = "CREATE TABLE `t_fcf_samplecode` (\n" +
                    "  `problem_id` int(11) NOT NULL,\n" +
                    "  `sample_code` text NOT NULL,\n" +
                    "  KEY `problem_id` (`problem_id`),\n" +
                    "  CONSTRAINT `t_fcf_samplecode_ibfk_1` FOREIGN KEY (`problem_id`) REFERENCES `t_problem` (`problem_id`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            GetStatement.executeUpdate(sql);
            //提交事务
            GetConnectionDatabase.commit();
        } catch (SQLException e) {
            flag = false;
            if (GetConnectionDatabase != null) {
                //不成功
                //回滚事务
                try {
                    GetConnectionDatabase.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            flag = false;
            if (GetConnectionDatabase != null) {
                //不成功
                //回滚事务
                try {
                    GetConnectionDatabase.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (GetStatement != null) {
                try {
                    GetStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (GetConnectionDatabase != null) {
                try {
                    GetConnectionDatabase.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }
        return flag;
    }
}