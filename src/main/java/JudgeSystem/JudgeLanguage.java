package JudgeSystem;

public enum JudgeLanguage {
    JAVA,
    GPP,
    PYTHON;

    public static JudgeLanguage IntToEnum(int value) {
        switch (value) {
            case 0:
                return JAVA;
            case 1:
                return GPP;
            case 2:
                return PYTHON;
            default:
                return null;
        }
    }

}
