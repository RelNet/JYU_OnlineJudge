package JudgeSystem.RunJudgeProgram;

import JudgeSystem.CompileFile.CompileMain;
import JudgeSystem.JudgeSystemConstant;
import JudgeSystem.RunScript.RunLinuxCMD;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static JudgeSystem.JudgeSystemConstant.AC;
import static JudgeSystem.JudgeSystemConstant.RE;

@Getter
@Setter
public class RunJAVA extends Run {
    public RunJAVA(long time, String inID, String inProblemID, boolean hasIn) {
        super(time, inID, inProblemID, hasIn);
    }

    String[] commandStrings = {"/bin/sh", "-c",
            UNLIMITED_MEMORY_COMMAND_STRING,
            "java " + CompileMain.INITIAL_FILE_ADDRESS + "/" + submitID + "/t" + submitID
    };

    public int threadPoolSize = 3;
    // 判题结果
    List<JudgeSystemConstant> controllerCodeList = new ArrayList<>();

    // 执行线程池
    ExecutorService dealTaskThreadPool = Executors.newFixedThreadPool(threadPoolSize);

    @Override
    public boolean runIt() {
        if (hasInput) {     // 如果有输入文件
            File[] inputFileList = getInputFileList();
            Integer count = 1;
            // 遍历输入文件夹中的文件
            for (File i : inputFileList) {

            }
        } else {

        }
        return true;
    }

    // 处理某任务
    private void dealTask(File inputFile, File outputFile) {
        dealTaskThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                RunLinuxCMD order;

                // 运行用户程序
                try {
                    order = new RunLinuxCMD(commandStrings, inputFile, outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                // 挂起进程
                String[] stopCommandStrings = {
                        "/bin/sh", "-c",
                        "kill -stop " + order.getPid()
                };
                // 继续执行进程
                String[] runCommandStrings = {
                        "/bin/sh", "-c",
                        "kill -cont " + order.getPid()
                };

                RunLinuxCMD orderController;
                try {
                    orderController = new RunLinuxCMD(stopCommandStrings);
                    orderController.waitFor();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                    return;
                }

                // 循环次数
                long times = timeout / 50;
                // 通过返回值判断程序是否结束并限制运行时间
                while (times-- != 0) {
                    try {
                        orderController = new RunLinuxCMD(runCommandStrings);
                        orderController.waitFor();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // 如果返回值不为零，则继续执行
                if (order.existValue() != 0) {
                    try {
                        orderController = new RunLinuxCMD(stopCommandStrings);
                        orderController.waitFor();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{

                }
            }
        });
    }
}
