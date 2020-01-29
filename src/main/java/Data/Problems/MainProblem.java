package Data.Problems;

import JudgeSystem.ProblemType;

import java.util.List;

public class MainProblem {
    private Integer problemID;
    private List<String> problemContent;
    private List<ProblemIOInfo> IOList;
    private ProblemType problemType;

    public Integer getProblemID() {
        return problemID;
    }

    public ProblemType getProblemType() {
        return problemType;
    }

    public void setProblemType(ProblemType problemType) {
        this.problemType = problemType;
    }

    public void setProblemID(Integer problemID) {
        this.problemID = problemID;
    }

    public List<String> getProblemContent() {
        return problemContent;
    }

    public void setProblemContent(List<String> problemContent) {
        this.problemContent = problemContent;
    }

    public List<ProblemIOInfo> getIOList() {
        return IOList;
    }

    public void setIOList(List<ProblemIOInfo> IOList) {
        this.IOList = IOList;
    }
}

// 存放I/O信息
class ProblemIOInfo {
    List<String> inputInfo;
    List<String> outputInfo;

    public List<String> getInputInfo() {
        return inputInfo;
    }

    public void setInputInfo(List<String> inputInfo) {
        this.inputInfo = inputInfo;
    }

    public List<String> getOutputInfo() {
        return outputInfo;
    }

    public void setOutputInfo(List<String> outputInfo) {
        this.outputInfo = outputInfo;
    }
}
