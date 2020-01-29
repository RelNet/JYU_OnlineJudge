package Data.Contest;

import Database.OJProblemStatus.ProblemStatus;

public class ContestStatusMessage extends ProblemStatus {
    //该提交的题目通过了多少测试数据
    public int passCases;
    //该提交的题目总共有多少测试数据
    public int totalCases;
}
