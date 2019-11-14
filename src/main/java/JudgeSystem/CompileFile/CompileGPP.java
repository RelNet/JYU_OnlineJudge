package JudgeSystem.CompileFile;

import JudgeSystem.run.script.RunLinuxCMD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CompileGPP extends CompileMain {
    public CompileGPP(Long inSubmitID) {
        super(inSubmitID);
    }

    @Override
    public void compileIt() {
        try {
            String[] commandStrings = {"/bin/sh", "-c",
                    "g++ -o " + INITIAL_FILE_ADDRESS + "/" + submitID.toString() + "/" + submitID.toString() + ".cpp"};

            order = new RunLinuxCMD(commandStrings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    // 检查errorCodes，如果出现子串"error"则说明编译出错
    public boolean hasCompileError() {
        for (String errorCode : errorCodes) {
            if (errorCode.contains("error")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setErrorCodes() {
        try {
            BufferedReader inError = new BufferedReader(new InputStreamReader(order.getErrorStream()));
            String str;
            while ((str = inError.readLine()) != null) {
                errorCodes.add(str);
            }
            inError.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
