package CompileFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
    此抽象类是编译源代码文件的父类。
 */
public abstract class CompileMain {
    /*
        从客户端读取的源代码将写入到一个源代码文件中
        将其传入到sourceCodeFile
     */
    File sourceCodeFile;

    // 提交ID
    String submitID;

    // 接收编译信息
    List<String> errorCodes;

    static final String INITIAL_FILE_ADDRESS = "/submit";
//    static final String INITIAL_FILE_ADDRESS = "C:\\User\\H\\Desktop\\OJTest\\submit";

    CompileMain(File inFile, String inSubmitID) {
        sourceCodeFile = inFile;
        submitID = inSubmitID;
        errorCodes = new ArrayList<>();

        // 这个应该放在读取那里
//        File dir = new File(INITIAL_FILE_ADDRESS + "/" + submitID);
//        if (dir.exists()) {
//            System.out.println("提交ID冲突，可能已经存在此ID：" + submitID);
//        } else {
//            if (!dir.mkdir()) {
//                System.out.println("无法创建提交ID的文件夹，ID：" + submitID);
//            }
//        }
    }

    // 编译方法
    public abstract void compileIt();

    // 判断是否出现编译错误
    public abstract boolean hasCompileError();

    // 返回编译信息
    public List<String> getErrorCodes() {
        return errorCodes;
    }
}
