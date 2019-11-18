package JudgeSystem.run.program;

import JudgeSystem.CompileFile.CompileMain;
import JudgeSystem.JudgeSystemConstant;
import JudgeSystem.ResultJudgeComponent.ResultJudge;
import JudgeSystem.run.script.RunLinuxCMD;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import JudgeSystem.StartJudge;

public class Run {
    Integer timeout;
    String submitID;
    File compiledFile;
    String problemID;
    boolean hasInput;
    Integer maxMemory;

    // 样例中使用最多的内存和时间
    Integer usedTime = 0;
    Integer usedMemory = 0;

    // 运行程序的命令
    String[] commandStrings;

    public String getProblemLanguage() {
        return problemLanguage;
    }

    public void setProblemLanguage(String problemLanguage) {
        this.problemLanguage = problemLanguage;
    }

    String problemLanguage;

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getSubmitID() {
        return submitID;
    }

    public void setSubmitID(String submitID) {
        this.submitID = submitID;
    }

    public File getCompiledFile() {
        return compiledFile;
    }

    public void setCompiledFile(File compiledFile) {
        this.compiledFile = compiledFile;
    }

    public String getProblemID() {
        return problemID;
    }

    public void setProblemID(String problemID) {
        this.problemID = problemID;
    }

    public boolean isHasInput() {
        return hasInput;
    }

    public void setHasInput(boolean hasInput) {
        this.hasInput = hasInput;
    }

    public Integer getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(Integer maxMemory) {
        this.maxMemory = maxMemory;
    }

    public Integer getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Integer usedTime) {
        this.usedTime = usedTime;
    }

    public Integer getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(Integer usedMemory) {
        this.usedMemory = usedMemory;
    }

    public JudgeSystemConstant[] getControllerCodeList() {
        return controllerCodeList;
    }

    public void setControllerCodeList(JudgeSystemConstant[] controllerCodeList) {
        this.controllerCodeList = controllerCodeList;
    }


    // 判题结果列表
    volatile JudgeSystemConstant[] controllerCodeList;

    // 执行线程池
    static int MAX_RUNNING_CORE_TASK_NUMBER = 2;
    static int MAX_RUNNING_TASK_NUMBER = 2;
    // 单位毫秒
    static long RUNNING_KEEP_ALIVE_TIME = 500L;
    // 每一题最大的样例容量
    static int MAX_TASK_SIZE = 100;
    ExecutorService dealTaskThreadPool = new ThreadPoolExecutor(MAX_RUNNING_CORE_TASK_NUMBER, MAX_RUNNING_TASK_NUMBER, RUNNING_KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(MAX_TASK_SIZE));

    // 这个是暂定的问题存放路径
    public static final String PROBLEM_SET_PATH = "/problems";

    // 无限栈空间命令
    static final String UNLIMITED_MEMORY_COMMAND_STRING = "ulimit -s unlimited";
    // 限制内存使用命令 后面要加上具体点内存数字，单位KB
    static final String LIMIT_MEMORY_COMMAND_STRING = "ulimit -m ";

    Run(Integer time, String inID, String inProblemID, boolean hasIn, Integer maxMemory) {
        timeout = time;
        submitID = inID;
        problemID = inProblemID;
        hasInput = hasIn;
        this.maxMemory = maxMemory;
        compiledFile = new File(CompileMain.INITIAL_FILE_ADDRESS + "/" + submitID + "/t" + submitID);
    }

    public boolean existFile() {
        return compiledFile.exists();
    }

    File[] getInputFileList() {
        File dir = new File(PROBLEM_SET_PATH + "/" + problemID + "/in");
        return dir.listFiles();
    }

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
        // 将结果列表写入数据库

        return true;
    }


    // 处理任务
    private void dealTask(File inputFile, File outputFile, Integer fileNumber) {
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

                // order pid的String
                String pidString = order.getPid().toString();

                // 挂起进程命令
                String[] stopCommandStrings = {
                        "/bin/sh", "-c",
                        "kill -stop " + pidString
                };
                // 运行进程命令
                String[] keepRunCommandStrings = {
                        "/bin/sh", "-c",
                        "kill -cont " + pidString
                };
                // 检查内存命令
                String[] checkMemoryCommandStrings = {
                        "/bin/sh", "-c",
                        "ps -e -o 'pid,rsz' | grep -w " + pidString
                };

                try {
                    RunLinuxCMD action = new RunLinuxCMD(stopCommandStrings);
                    action.waitFor();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }


                // 循环次数
                long times = timeout / 50 + 1;
                // 通过返回值判断程序是否结束,并限制运行时间
                while (times-- != 0 && order.existValue() != 0) {
                    if (times == 0) {
                        // 写入TLE数据进入结果列表
                        controllerCodeList[Integer.parseInt(problemID)] = JudgeSystemConstant.TLE;
                        return;
                    }
                    try {
                        RunLinuxCMD action = new RunLinuxCMD(keepRunCommandStrings);
                        action.waitFor();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }

                    // 检查段错误
                    BufferedReader orderErrorMessageReader = new BufferedReader(new InputStreamReader(order.getErrorStream()));
                    try {
                        String errorString = orderErrorMessageReader.readLine();
                        if (errorString.contains("Segmentation Fault")) {
                            controllerCodeList[Integer.parseInt(problemID)] = JudgeSystemConstant.RE;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    // 让进程运行50ms
                    try {
                        Thread.sleep(50);
                        try {
                            RunLinuxCMD action = new RunLinuxCMD(stopCommandStrings);
                            action.waitFor();
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        RunLinuxCMD action = new RunLinuxCMD(checkMemoryCommandStrings);
                        action.waitFor();
                        BufferedReader memoryMessageReader = new BufferedReader(new InputStreamReader(action.getInputStream()));
                        String memoryMessage = memoryMessageReader.readLine();

                        // 检查内存
                        int indexOfPid = memoryMessage.indexOf(pidString);
                        if (indexOfPid != -1) {
                            int startIndexOfMemory = memoryMessage.length() - 1;
                            for (int i = indexOfPid + pidString.length(); i < memoryMessage.length(); i++) {
                                if (memoryMessage.charAt(i) != ' ') {
                                    startIndexOfMemory = i;
                                }
                            }
                            Integer runningMemory = Integer.parseInt(memoryMessage.substring(startIndexOfMemory));
                            if (runningMemory > maxMemory) {
                                controllerCodeList[Integer.parseInt(problemID)] = JudgeSystemConstant.MLE;
                                return;
                            } else {
                                if (runningMemory > usedMemory) {
                                    usedMemory = runningMemory;
                                }
                            }
                        } else {
                            break;
                        }
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                }

                File standardFile = new File("/problem/" + problemID + "/" + fileNumber.toString());
                // 判断结果的正确性，并写入结果列表
                ResultJudge resultJudge = new ResultJudge(outputFile, standardFile);
                controllerCodeList[Integer.parseInt(problemID)] = resultJudge.runJudge();
            }
        });
    }
}
