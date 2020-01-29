package JudgeSystem;

public enum ProblemType {
    MCQ,    // 选择题
    FB,     // 填空题
    CF,     // 编译题
    FCF;     // 填空编译题

    public static ProblemType IntToEnum(int value) {
        switch (value) {
            case 0:
                return MCQ;
            case 1:
                return FB;
            case 2:
                return CF;
            case 3:
                return FCF;
            default:
                return null;
        }
    }
}
