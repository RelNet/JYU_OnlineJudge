package JudgeSystem;

import Data.JudgeTask.MainTask;
import Data.Submit.MainSubmit;
import JudgeSystem.CompileFile.CompileGPP;
import JudgeSystem.CompileFile.CompileJAVA;
import JudgeSystem.CompileFile.CompileMain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class StartJudge {
    private static volatile Queue<MainSubmit> submitQueue = new LinkedList<>();     // 提交的队列
    private static final Object lockCompile = new Object();
    private static volatile Queue<MainTask> waitingJudgeQueue = new LinkedList<>();     // 等待判定队列
    private static volatile Queue<MainSubmit> finishedQueue = new LinkedList<>();       // 完成了的队列，等待写入数据库

    private static final Long MAX_NUMBER_OF_JUDGING_TASK = 10L;    // 允许同时judge的列表容量
    private volatile List<MainTask> judgingList = new ArrayList<>();    // 正在判断的列表


    /*
        已经在CompileMain里面约定了用户提交的文件存放在哪里
        所以只需要提交submitID就可以执行编译
     */
    void compile() {
        MainSubmit topSubmit = submitQueue.remove();
        CompileMain compiler;
        switch (topSubmit.getLanguageType()) {
            case GPP:
                compiler = new CompileGPP(topSubmit.getSubmitID());
                break;
            case JAVA:
                compiler = new CompileJAVA(topSubmit.getSubmitID());
                break;
            case PYTHON:
            default:
                throw new NullPointerException("未定义的语言类型");
        }

        final Object lockJudgeCE = new Object();
        new Thread(() -> {
            synchronized (lockCompile) {
                synchronized (lockJudgeCE) {
                    try {
                        compiler.compileIt();
                        compiler.getOrder().waitFor();
                        compiler.setErrorCodes();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (lockJudgeCE) {
                if (compiler.hasCompileError()) {
                    topSubmit.setControlCode(JudgeSystemConstant.CE);
                    finishedQueue.add(topSubmit);
                } else {
                    waitingJudgeQueue.add(new MainTask());
                }
            }
        }).start();
    }

    void judgeSample() {

    }
}


