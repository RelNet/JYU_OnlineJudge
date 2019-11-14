package JudgeSystem.run.script;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/*
    用于运行脚本和命令行指令
 */
public class RunLinuxCMD extends Thread {
    private String[] commands;

    // 运行脚本的进程
    private Process course;
    private ProcessBuilder processBuilder;
    private Long pid;

    public RunLinuxCMD(String[] inCommands) throws IOException {
        commands = inCommands;
        course = Runtime.getRuntime().exec(commands);
        pid = course.pid();
    }

    public RunLinuxCMD(String[] inCommands, File inputFile, File outputFile) throws IOException {
        commands = inCommands;
        processBuilder = new ProcessBuilder(inCommands);
        processBuilder.redirectInput(inputFile);
        processBuilder.redirectOutput(outputFile);
        course = processBuilder.start();
        pid = course.pid();
        processBuilder = null;
    }

    public Long getPid() {
        return pid;
    }

    public void kill() {
        course.destroy();
    }

    public Process forceKill() {
        return course.destroyForcibly();
    }

    public int existValue() {
        return course.exitValue();
    }

    public InputStream getErrorStream() {
        return course.getErrorStream();
    }

    public InputStream getInputStream() {
        return course.getInputStream();
    }

    public OutputStream getOutputStream() {
        return course.getOutputStream();
    }

    public ProcessHandle.Info info() {
        return course.info();
    }

    public boolean waitFor(long timeout, TimeUnit unit) throws InterruptedException {
        return course.waitFor(timeout, unit);
    }

    public int waitFor() throws InterruptedException {
        return course.waitFor();
    }

    public String[] getCommands() {
        return commands;
    }
}
