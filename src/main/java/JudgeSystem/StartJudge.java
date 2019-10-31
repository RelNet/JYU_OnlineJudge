package JudgeSystem;

import Data.Submit.MainSubmit;
import JudgeSystem.CompileFile.CompileGPP;
import JudgeSystem.CompileFile.CompileJAVA;
import JudgeSystem.CompileFile.CompileMain;

import java.util.Queue;

public class StartJudge {
    private volatile Queue<MainSubmit> submitQueue;
    private Object lockCompile = new Object();
    CompileMain compiler;


    /*
        已经在CompileMain里面约定了用户提交的文件存放在哪里
        所以只需要提交submitID就可以执行编译
     */
    void compile() {
        Object lockErrorStream = new Object();
        MainSubmit topSubmit = submitQueue.remove();
        synchronized (lockCompile) {
            synchronized (lockErrorStream) {
                new Thread(() -> {
                    switch (topSubmit.getLanguage()) {
                        case GPP:
                            compiler = new CompileGPP(topSubmit.getSubmitID());
                            break;
                        case JAVA:
                            compiler = new CompileJAVA(topSubmit.getSubmitID());
                        case PYTHON:
                        default:
                            throw new NullPointerException("未定义的语言类型");
                    }
                    try {
                        compiler.getOrder().waitFor();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        synchronized (lockErrorStream) {
            new Thread(() -> {
                compiler.setErrorCodes();
            });
        }
    }

}
