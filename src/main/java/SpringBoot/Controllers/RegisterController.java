package SpringBoot.Controllers;

import Data.Users.MainUser;
import Database.OjUserMessage.UserRegister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Controller
public class RegisterController {
    // 注册信息写入数据库的线程池
    static int MAX_REGISTER_CORE_TASK_NUMBER = 1;
    static int MAX_REGISTER_TASK_NUMBER = 30;
    static long REGISTER_KEEP_ALIVE_TIME = 5L;
    // 等待注册的队列长度
    static int MAX_WAITING_REGISTER_SIZE = 100;
    static ExecutorService registerThreadPool = new ThreadPoolExecutor(MAX_REGISTER_CORE_TASK_NUMBER, MAX_REGISTER_TASK_NUMBER
            , REGISTER_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(MAX_WAITING_REGISTER_SIZE));

    static final Object registerThreadPoolLock = new Object();

    @GetMapping(path = "register")
    public String toRegister() {
        return "register/register";
    }

    @PostMapping(path = "welcome")
    public String registerUser(MainUser studentInformation, ModelMap model, HttpSession session) {
        System.out.println("?????????" + "s_" + studentInformation.getID() +
                studentInformation.getPassword() + studentInformation.getClassName() + studentInformation.getRealName() +
                studentInformation.getCollegeName() + studentInformation.getID());

        synchronized (registerThreadPoolLock) {
            registerThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    // 插入数据库
                    new UserRegister().register("s_" + studentInformation.getID(),
                            studentInformation.getPassword(), studentInformation.getClassName(), studentInformation.getRealName(),
                            studentInformation.getCollegeName(), studentInformation.getID());
                }
            });
        }

        session.setAttribute("loginUser", studentInformation.getUsername());

        return "register/welcome_to_join_us";
    }

//    @GetMapping(path = "welcome")
//    public String toWelcome() {
//        return "welcome_to_join_us";
//    }
}
