package JudgeSystem.run.program;

import JudgeSystem.CompileFile.CompileMain;
import JudgeSystem.JudgeSystemConstant;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

abstract public class Run {
    long timeout;
    String submitID;
    File compiledFile;
    String problemID;
    boolean hasInput;
    Long maxMemory;

    // 判题结果列表
    volatile JudgeSystemConstant[] controllerCodeList;

    // 执行线程池
    static int MAX_RUNNING_CORE_TASK_NUMBER = 1;
    static int MAX_RUNNING_TASK_NUMBER = 3;
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

    Run(long time, String inID, String inProblemID, boolean hasIn) {
        timeout = time;
        submitID = inID;
        problemID = inProblemID;
        hasInput = hasIn;
        compiledFile = new File(CompileMain.INITIAL_FILE_ADDRESS + "/" + submitID + "/t" + submitID);
    }

    public boolean existFile() {
        return compiledFile.exists();
    }

    File[] getInputFileList() {
        File dir = new File(PROBLEM_SET_PATH + "/" + problemID + "/in");
        return dir.listFiles();
    }

    abstract public boolean runIt();
}
