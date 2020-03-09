package judge;


import java.io.Serializable;

// OJ判题结果枚举类
public enum JudgeSystemConstant {
    WAITING,
    AC,
    WA,
    PE,
    TLE,
    OLE,
    MLE,
    RE,
    CE,
    RJ;// Rejudge

    public static JudgeSystemConstant IntToEnum(int value) {
        switch (value) {
            case 0:
                return WAITING;
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
            default:
                return null;
        }
    }

    public static Integer EnumToInt(JudgeSystemConstant constant) {
        switch (constant) {
            case WAITING:
                return 0;
            case AC:
                return 1;
            case WA:
                return 2;
            case PE :
                return 3;
            case TLE:
                return 4;
            case OLE:
                return 5;
            case MLE:
                return 6;
            case RE:
                return 7;
            case CE:
                return 8;
            case RJ:
                return 9;
            default:
                return -1;
        }
    }
}
