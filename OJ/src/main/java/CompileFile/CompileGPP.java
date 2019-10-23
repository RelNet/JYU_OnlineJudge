package java.CompileFile;

import java.RunScript.RunLinuxCMD;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CompileGPP extends CompileMain {
    public CompileGPP(File inFile, String inSubmitID) {
        super(inFile, inSubmitID);
    }

    @Override
    public void compileIt() {
        RunLinuxCMD order;
        try {
            String[] commandStrings = {"/bin/sh", "-c",
                    "g++ -o " + INITIAL_FILE_ADDRESS + "/" + submitID + "/t" + submitID + " " + sourceCodeFile.toString()};

            order = new RunLinuxCMD(commandStrings);
            order.waitFor();
            BufferedReader inError = new BufferedReader(new InputStreamReader(order.getErrorStream()));
            String str;
            while ((str = inError.readLine()) != null) {
                errorCodes.add(str);
            }
            inError.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    // 检查errorCodes，如果出现子串"error"则说明编译出错
    public boolean hasCompileError() {
        for (var errorCode : errorCodes) {
            if (errorCode.contains("error")) {
                return true;
            }
        }
        return false;
    }
}
