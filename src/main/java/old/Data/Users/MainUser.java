package old.Data.Users;

import java.io.Serializable;

// 存放用户数据
public class MainUser implements Serializable {
    String username;
    String password;
    private String collegeName;
    private String ID;
    private String realName;
    String className;

    @Override
    public String toString() {
        return "MainUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", collegeName='" + collegeName + '\'' +
                ", ID='" + ID + '\'' +
                ", realName='" + realName + '\'' +
                ", className='" + className + '\'' +
                '}';
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
