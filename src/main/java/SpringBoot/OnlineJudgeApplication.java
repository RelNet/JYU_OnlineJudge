package SpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineJudgeApplication {
    private static Integer CPUs = 4;

    public static void main(String[] args) {
        SpringApplication.run(OnlineJudgeApplication.class, args);
        dealArgs(args);
    }

    // 处理命令行参数
    private static void dealArgs(String... args){
        for (String arg : args) {
            dealArg(arg);
        }
    }

    // 根据参数不同进行不同的配置
    private static  void dealArg(String arg){
//        switch (arg){
//            case :
//        }
    }
}
