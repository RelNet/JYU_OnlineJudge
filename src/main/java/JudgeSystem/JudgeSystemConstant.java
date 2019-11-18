package JudgeSystem;


// OJ判题结果枚举类
public enum JudgeSystemConstant {
    AC,
    WA,
    PE,
    TLE,
    OLE,
    MLE,
    RE,
    CE,
    RJ,// Rejudge
    WAITING;

    public static JudgeSystemConstant IntToEnum(int value) {
        switch (value) {
            case 1:
                return AC;
            case 2:
                return WA;
            case 3:
                return PE;
            case 4:
                return TLE;
            case 5:
                return OLE;
            case 6:
                return MLE;
            case 7:
                return RE;
            case 8:
                return CE;
            case 9:
                return RJ;
            case 10:
                return WAITING;
            default:
                return null;
        }
    }
}
