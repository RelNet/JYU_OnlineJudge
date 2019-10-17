package CompileFile.Test;

import CompileFile.CompileGPP;
import CompileFile.CompileMain;

import java.io.File;
import java.util.List;

public class TestCompileGPP {
    public static void main(String[] args) {
        File f = new File("/home/h/Desktop/test.cpp");
        if (f.exists()) {
            System.out.println("exist!");
        } else {
            return;
        }
        CompileMain test = new CompileGPP(f, "1");

        test.compileIt();
        if (test.hasCompileError()) {
            List<String> strings = test.getErrorCodes();
            for (String i : strings) {
                System.out.println(i);
            }
        }
    }
}
