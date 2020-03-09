package judge;

public enum JudgeMode {
    ACM,
    OI;

    public static JudgeMode IntToEnum(int value) {
        switch (value) {
            case 0:
                return ACM;
            case 1:
                return OI;
            default:
                return null;
        }
    }

    public static int EnumToInt(JudgeMode mode) {
        switch (mode) {
            case ACM:
                return 0;
            case OI:
                return 1;
            default:
                return -1;
        }
    }
}
