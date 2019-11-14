package JudgeSystem.CompileFile;

import JudgeSystem.run.script.RunLinuxCMD;

import java.util.ArrayList;
import java.util.List;

/*
    此抽象类是编译源代码文件的父类。
 */
public abstract class CompileMain {
    // 提交ID
    Long submitID;

    // 接收编译信息
    List<String> errorCodes;

    RunLinuxCMD order;

    static final public String INITIAL_FILE_ADDRESS = "/submit";

    CompileMain(Long inSubmitID) {
        submitID = inSubmitID;
        errorCodes = new ArrayList<String>();
    }

    public abstract void setErrorCodes();

    // 编译方法
    public abstract void compileIt();

    // 判断是否出现编译错误
    public abstract boolean hasCompileError();

    public RunLinuxCMD getOrder() {
        return order;
    }

    // 返回编译信息
    public List<String> getErrorCodes() {
        return errorCodes;
    }
}
