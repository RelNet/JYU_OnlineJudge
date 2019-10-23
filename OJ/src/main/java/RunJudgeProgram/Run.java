package java.RunJudgeProgram;

import java.CompileFile.CompileMain;

import java.io.File;

abstract public class Run {
    long timeout;

    String submitID;

    File compiledFile;

    String problemID;

    boolean hasInput;

    public static final String PROBLEM_SET_ADDRESS = "/problems";

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
        File dir = new File(PROBLEM_SET_ADDRESS + "/" + problemID + "/in");
        return dir.listFiles();
    }

    abstract public boolean runIt();
}
