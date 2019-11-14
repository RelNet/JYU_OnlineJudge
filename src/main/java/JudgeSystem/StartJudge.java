package JudgeSystem;

import Data.JudgeTask.MainTask;
import Data.Submit.MainSubmit;
import JudgeSystem.CompileFile.CompileGPP;
import JudgeSystem.CompileFile.CompileJAVA;
import JudgeSystem.CompileFile.CompileMain;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

@Getter
@Setter
public class StartJudge {
    private static volatile Queue<MainSubmit> submitQueue = new ArrayDeque<>();     // 提交的队列

    // 这个是编译线程池的相关设置
    private static final Integer MAX_COMPILE_THREADS = 3;
    private static final Integer MAX_COMPILE_QUEUE_SIZE = 4000;
    private static final Long COMPILE_KEEP_ALIVE_TIME = 2L;
    private static final Integer MAX_COMPILE_CORE_POOL_SIZE = 1;
    ExecutorService compileThreadPool = new ThreadPoolExecutor(MAX_COMPILE_CORE_POOL_SIZE, MAX_COMPILE_THREADS, COMPILE_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(MAX_COMPILE_QUEUE_SIZE));

    // 提交答案线程池，要与数据库进行交互
    private static final Integer MAX_WRITE_THREADS = 100;
    private static final Integer MAX_WRITE_QUEUE_SIZE = 5000;
    private static final Long WRITE_KEEP_ALIVE_TIME = 60L;
    private static final Integer MAX_WRITE_CORE_POOL_SIZE = 30;
    ExecutorService writeAnswerToDatabaseThreadPool = new ThreadPoolExecutor(MAX_WRITE_CORE_POOL_SIZE, MAX_WRITE_THREADS, WRITE_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(MAX_WRITE_QUEUE_SIZE));

    // 编译型任务线程池设置
    ExecutorService runProgramThreadPool;

    // 不同问题类型分类器
    void classifier(MainSubmit submit) {
        switch (submit.getProblemType()) {
            case MCQ:
            case FB:
                // 从数据库获取答案集
                List<String> answerList = null;
                List<String> userAnswerList = submit.getCourseCodes();
                if (answerList.size() != userAnswerList.size()) {
                    // 写入结果到数据库

                    return;
                }
                // 正确答案标记
                boolean noteIsRight = true;
                // 遍历并比较两个List是不是一样的
                for (int i = 0; i < answerList.size() && noteIsRight; i++) {
                    if (!answerList.get(i).equals(userAnswerList.get(i))) {
                        noteIsRight = false;
                    }
                }
                // 写入数据库
                if (noteIsRight) {

                } else {

                }
                break;
            case FCF:
            case CF:
            default:
                throw new NullPointerException("未定义的题目类型");
        }
    }

    void compile() {

    }

    void judgeProgram(MainSubmit submit, ProblemType type) {

    }
}


