package CompileFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CompileJAVA extends CompileMain {
    public CompileJAVA(File inFile, String inSubmitID) {
        super(inFile, inSubmitID);
    }

    @Override
    public void compileIt() {
        Process order;
        try {
            order = Runtime.getRuntime().exec("javac " + INITIAL_FILE_ADDRESS + "/" + submitID + "/t" + submitID + " " + sourceCodeFile.toString());
            BufferedReader inError = new BufferedReader(new InputStreamReader(order.getInputStream()));
            String str;
            while ((str = inError.readLine()) != null) {
                errorCodes.add(str);
            }
            inError.close();
        } catch (IOException e) {
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
