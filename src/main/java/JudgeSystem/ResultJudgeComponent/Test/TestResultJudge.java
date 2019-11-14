package JudgeSystem.ResultJudgeComponent.Test;

import JudgeSystem.JudgeSystemConstant;
import JudgeSystem.ResultJudgeComponent.ResultJudge;

import java.io.File;

public class TestResultJudge {
    public static void main(String[] args) {
        ResultJudge AAA = new ResultJudge(new File("D:/text.txt"), new File("D:/Right.txt"));
        JudgeSystemConstant MM = AAA.runJudge();
        System.out.println(MM);
    }
}
