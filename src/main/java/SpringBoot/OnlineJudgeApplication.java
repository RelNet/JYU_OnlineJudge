package SpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineJudgeApplication {
    // 默认有4个核心
    private static Integer CPUs = 4;
    // 默认单位是MB
    private static Integer Memory = 4096;

    public static void main(String[] args) {
        SpringApplication.run(OnlineJudgeApplication.class, args);
        init();
        dealArgs(args);
    }

    // 处理命令行参数
    private static void dealArgs(String... args) {
        for (String arg : args) {
            dealArg(arg);
        }
    }

    // 根据参数不同进行不同的配置
    private static void dealArg(String arg) {
//        switch (arg){
//            case :
//        }
    }

    // 初始化一些配置
    private static void init() {

    }
}
