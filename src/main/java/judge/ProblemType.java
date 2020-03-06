package judge;

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

    public static int EnumToInt(ProblemType type) {
        switch (type){
            case MCQ:
                return 0;
            case FB:
                return 1;
            case CF:
                return 2;
            case FCF:
                return 3;
            default:
                return -1;
        }
    }
}
