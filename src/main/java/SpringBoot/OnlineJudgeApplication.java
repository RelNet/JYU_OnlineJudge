package SpringBoot;

import Database.JdbcConnection;
import JudgeSystem.StartJudge;
import SpringBoot.WebCache.HomeCache;
import SpringBoot.WebCache.ProblemSetCache;
import SpringBoot.WebCache.StatusCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class OnlineJudgeApplication {
    // 默认有4个核心
    private static Integer CPUs = 4;
    // 默认单位是MB
    private static Integer Memory = 4096;

    public static void main(String[] args) {
        SpringApplication.run(OnlineJudgeApplication.class, args);
        dealArgs(args);
        init();
        System.out.println(JdbcConnection.getDatabasename() + "   " + JdbcConnection.getIp() + "   " + JdbcConnection.getPassword() + "   " + JdbcConnection.getPort() + "   " + JdbcConnection.getUser());
        HomeCache homeCache = new HomeCache();
        ProblemSetCache problemSetCache = new ProblemSetCache();
        StatusCache statusCache = new StatusCache();
        homeCache.start();
        problemSetCache.start();
        statusCache.start();
    }

    // 处理命令行参数
    private static void dealArgs(String... args) {
        for (String arg : args) {
            dealArg(arg);
        }
    }

    // 根据参数不同进行不同的配置
    private static void dealArg(String arg) {
        // '='的位置
        int indexOfEqual = arg.indexOf('=');
        if (indexOfEqual == -1 || indexOfEqual == arg.length() - 1) {
            return;
        }
        String messageString = arg.substring(indexOfEqual + 1);
        String settingMessageString = arg.substring(0, indexOfEqual);

        switch (settingMessageString) {
            case "CUPs":
                try {
                    Integer message = Integer.getInteger(messageString);
                    CPUs = message;
                    break;
                } catch (SecurityException e) {
                    e.printStackTrace();
                    return;
                }
            case "Memory":
                try {
                    Integer message = Integer.getInteger(messageString);
                    Memory = message;
                    break;
                } catch (SecurityException e) {
                    e.printStackTrace();
                    return;
                }
            case "username":
                JdbcConnection.setUser(messageString);
                break;
            case "password":
                JdbcConnection.setPassword(messageString);
                break;
            case "ip":
                if (isIP(messageString)) {
                    JdbcConnection.setIp(messageString);
                }
                break;
            case "databasename":
                JdbcConnection.setDatabasename(messageString);
                break;
            case "port":
                try {
                    Integer message = Integer.getInteger(messageString);
                    JdbcConnection.setPort(messageString);
                    break;
                } catch (SecurityException e) {
                    e.printStackTrace();
                    return;
                }
            default:

        }
    }

    // 初始化一些配置
    private static void init() {
        // 根据CPU性能配置计算型线程池大小

        // 根据Memory大小配置IO型线程池大小

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
