package SpringBoot;

import Database.JdbcConnection;
import JudgeSystem.JudgeConfig;
import SpringBoot.Error.ConfigFileNotFind;
import SpringBoot.WebCache.HomeCache;
import SpringBoot.WebCache.ProblemSetCache;
import SpringBoot.WebCache.StatusCache;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class OnlineJudgeApplication {
    public static Map<String, JudgeConfig> judgeConfigMap = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(OnlineJudgeApplication.class, args);
        try {
            dealJson();
        } catch (ConfigFileNotFind configFileNotFind) {
            configFileNotFind.printStackTrace();
            return;
        }
        dealArgs();
        System.out.println(JdbcConnection.getDatabasename() + "   " + JdbcConnection.getIp() + "   " + JdbcConnection.getPassword() + "   " + JdbcConnection.getPort() + "   " + JdbcConnection.getUser());
        HomeCache homeCache = new HomeCache();
        ProblemSetCache problemSetCache = new ProblemSetCache();
        StatusCache statusCache = new StatusCache();
//        homeCache.start();
//        problemSetCache.start();
        statusCache.start();
    }

    // 处理命令行参数
    private static void dealArgs() {

    }

    // 读取json进行配置
    private static void dealJson() throws ConfigFileNotFind {
        File databaseJsonFile = new File(OnlineJudgeApplication.class.getResource("/SystemConfig/data_server.json").getPath());
        if (!databaseJsonFile.exists()) {
            throw new ConfigFileNotFind("数据库配置");
        }

        String databaseConfigString;
        try {
            databaseConfigString = FileUtils.readFileToString(databaseJsonFile, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取database配置文件出错");
            return;
        }
        JSONObject databaseJson = new JSONObject(databaseConfigString);
        JdbcConnection.setDatabasename(databaseJson.getString("database_name"));
        JdbcConnection.setIp(databaseJson.getString("ip"));
        JdbcConnection.setPort(databaseJson.getString("port"));
        JdbcConnection.setUser(databaseJson.getString("username"));
        JdbcConnection.setPassword(databaseJson.getString("password"));


        File judgeServerJsonFile = new File(OnlineJudgeApplication.class.getResource("/SystemConfig/judge_server.json").getPath());
        if (!judgeServerJsonFile.exists()) {
            throw new ConfigFileNotFind("judge服务器配置");
        }

        String judgeConfigString;
        try {
            judgeConfigString = FileUtils.readFileToString(judgeServerJsonFile, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取judge配置文件出错");
            return;
        }
        JSONObject judgeJson = new JSONObject(judgeConfigString);
        JSONArray judgeServerArray = judgeJson.getJSONArray("server");
        for (int i = 0; i < judgeServerArray.length(); i++) {
            JSONObject tempJudge = judgeServerArray.getJSONObject(i);
            String tempJudgeIp = tempJudge.getString("ip");
            String tempJudgePort = tempJudge.getString("port");
            JudgeConfig tempConfig = new JudgeConfig(tempJudgeIp, tempJudgePort);
            judgeConfigMap.put(tempJudgeIp, tempConfig);
        }
    }

    // 判断IPv4格式地址是否合法
    public static boolean isIP(String addr) {
        if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

        Pattern pat = Pattern.compile(rexp);

        Matcher mat = pat.matcher(addr);

        boolean ipAddress = mat.find();

        return ipAddress;
    }

}
