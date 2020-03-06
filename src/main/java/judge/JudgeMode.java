package judge;

public enum JudgeMode {
    OI,
    ACM;

    public static JudgeMode IntToEnum(int value) {
        switch (value) {
            case 0:
                return OI;
            case 1:
                return ACM;
            default:
                return null;
        }
    }
}
