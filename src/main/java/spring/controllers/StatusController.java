package spring.controllers;

import old.Data.Submit.MainSubmit;
import judge.connect.JudgeConnect;
import spring.error.JudgeServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 状态页控制器
 * 可以get状态页
 * 可以post表单到状态页
 */
@Controller
public class StatusController {
    private static final Logger logger = LoggerFactory.getLogger(StatusController.class);

    // 发送任务线程池
    private static int MAX_SEND_TASK_CORE_TASK_NUMBER = 1;
    private static int MAX_SEND_TASK_TASK_NUMBER = 1;
    private static long SEND_TASK_KEEP_ACTIVE_TIME = 1L;
    private static int MAX_WAITING_SEND_TASK_SIZE = 1000;
    public static ExecutorService sendTaskTreadPool = new ThreadPoolExecutor(MAX_SEND_TASK_CORE_TASK_NUMBER,
            MAX_SEND_TASK_TASK_NUMBER, SEND_TASK_KEEP_ACTIVE_TIME, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(MAX_WAITING_SEND_TASK_SIZE));

    @GetMapping(path = "status/{contestid}/{pagenumber}")
    public String toStatus(@PathVariable("contestid") Integer contestId, @PathVariable("pagenumber") Integer pageNumber
            , ModelMap model) {

//        StatusCache cache = new StatusCache();
//        model.addAttribute("statusList", cache.getStatusCache(pageNumber, contestId));
        return "status";
    }

    @PostMapping(path = "status/{contestId}/1")
    public String submit(@PathVariable("contestId") Integer contestId, MainSubmit submit, ModelMap model) {

        // 将任务发送到judge服务器
        sendTaskTreadPool.execute(new Runnable() {
            @Override
            public void run() {
                JudgeConnect connect = new JudgeConnect();
                try {
                    connect.sendTask(submit);
                } catch (JudgeServiceException e) {
                    e.printStackTrace();
                }
            }
        });

//        StatusCache cache = new StatusCache();
//        model.addAttribute("statusList", cache.getStatusCache(1, contestId));

        return "status";
    }
}
