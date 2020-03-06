package spring;

import database.Connect;
import old.Database.JdbcConnection;
import spring.cache.JudgeServiceCache;
import spring.error.ConfigFileNotFind;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
@EnableCaching
@EnableAsync
public class OnlineJudgeApplication {
    private static final Logger logger = LoggerFactory.getLogger(OnlineJudgeApplication.class);

    public static ArrayBlockingQueue<String> judgeServiceList = new ArrayBlockingQueue<>(16);

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

    }

    // 处理命令行参数
    private static void dealArgs() {

    }

    /**
     * 读取json配置
     *
     * @throws ConfigFileNotFind
     */
    private static void dealJson() throws ConfigFileNotFind {

        loadDatabase();
        loadJudgeServer();
    }

    private static void loadDatabase() throws ConfigFileNotFind {
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
        Connect.init(databaseJson.getString("ip"), databaseJson.getString("username"),
                databaseJson.getString("password"), databaseJson.getString("port"),
                databaseJson.getString("database_name"));
    }

    private static void loadJudgeServer() throws ConfigFileNotFind {
        File judgeServerJsonFile = new File(OnlineJudgeApplication.class.getResource("/SystemConfig/judge_server.json").getPath());
        if (!judgeServerJsonFile.exists()) {
            throw new ConfigFileNotFind("judge服务器配置");
        }

        JudgeServiceCache judgeServiceCache = new JudgeServiceCache();
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
            // 如果ip被更新为 "" ，那么不插入到judgeList， 并且删除对应缓存
            if (!"".equals(judgeServiceCache.get(tempJudgeIp).getIp())) {
                judgeServiceList.add(tempJudgeIp);
            } else {
                judgeServiceCache.delete(tempJudgeIp);
            }
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
