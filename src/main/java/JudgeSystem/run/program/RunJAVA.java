package JudgeSystem.run.program;

import JudgeSystem.CompileFile.CompileMain;
import JudgeSystem.JudgeSystemConstant;
import JudgeSystem.ResultJudgeComponent.ResultJudge;
import JudgeSystem.run.script.RunLinuxCMD;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.concurrent.*;

@Getter
@Setter
public class RunJAVA extends Run {
    public RunJAVA(long time, String inID, String inProblemID, boolean hasIn, Long maxMemory) {
        super(time, inID, inProblemID, hasIn);
        this.maxMemory = maxMemory * 2;
    }

    @Override
    public boolean runIt() {
        if (hasInput) {     // 如果有输入文件
            File[] inputFileList = getInputFileList();
            // 初始化结果列表
            controllerCodeList = new JudgeSystemConstant[inputFileList.length];
            Integer count = 1;
            // 遍历输入文件夹中的文件
            for (Integer i = 0; i < inputFileList.length; i++) {
                File outputFile = new File("/submit/" + submitID + "/" + i.toString() + ".out");
                try {
                    outputFile.createNewFile();
                    dealTask(inputFileList[i], outputFile, i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {    // 没有输入文件
            File outputFile = new File("/submit/" + submitID + "/0.out");
            controllerCodeList = new JudgeSystemConstant[1];
            try {
                outputFile.createNewFile();
                dealTask(null, outputFile, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    // 处理任务
    private void dealTask(File inputFile, File outputFile, Integer fileNumber) {
        String[] commandStrings = {"/bin/sh", "-c",
                LIMIT_MEMORY_COMMAND_STRING + maxMemory.toString(),
                "java " + CompileMain.INITIAL_FILE_ADDRESS + "/" + submitID + "/t" + submitID
        };
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

                // 循环次数
                long times = timeout / 50;
                // 通过返回值判断程序是否结束,并限制运行时间
                while (times-- != 0 && order.existValue() != 0) {
                    // 让进程运行50ms
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // 检查返回值
                if (order.existValue() != 0) {
                    order.forceKill();
                } else {
                    File standardFile = new File("/problem/" + problemID + "/" + fileNumber.toString());
                    ResultJudge resultJudge = new ResultJudge(outputFile, standardFile);
                    // 判断结果的正确性，并写入结果列表
                    controllerCodeList[Integer.parseInt(problemID)] = resultJudge.runJudge();
                }
            }
        });
    }
}
