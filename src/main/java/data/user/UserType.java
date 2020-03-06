package data.user;

public enum UserType {
    ADMIN,
    TEACHER,
    STUDENT;

    public static int EnumToInt(UserType type) {
        switch (type) {
            case ADMIN:
                return 0;
            case TEACHER:
                return 1;
            case STUDENT:
                return 2;
            default:
                return -1;
        }
    }

    public static UserType IntToEnum(int type) {
        switch (type) {
            case 0:
                return ADMIN;
            case 1:
                return TEACHER;
            case 2:
                return STUDENT;
            default:
        }
        return null;
    }
}
