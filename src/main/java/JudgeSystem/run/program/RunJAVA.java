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
        this.timeout = time * 2;
        this.problemLanguage = "java";
        this.commandStrings = new String[]{
                "/bin/sh", "-c",
                "java " + CompileMain.INITIAL_FILE_ADDRESS + "/" + submitID + "/t" + submitID
        };
    }
}
