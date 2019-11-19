package JudgeSystem.run.program;

import JudgeSystem.CompileFile.CompileMain;

// 运行C++程序
public class RunCPP extends Run {
    public RunCPP(Integer time, String inID, String inProblemID, boolean hasIn, Integer maxMemory) {
        super(time, inID, inProblemID, hasIn, maxMemory);
        this.problemLanguage = "g++";
        this.commandStrings = new String[]{
                "/bin/sh", "-c",
                "./" + CompileMain.INITIAL_FILE_ADDRESS + "/" + submitID + "/t" + submitID
        };
        runIt();
    }
}
