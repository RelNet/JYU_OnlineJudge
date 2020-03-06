package data.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UserInfo implements Serializable {
    private String school;
    private Integer studentId;
    private String classId;
    private String submit;
    private String accept;
    private UserType type;
    private ArrayList<String> acceptList;
    private ArrayList<String> submitList;
    private ArrayList<String> failedList;

    public void createAcceptList() {
        acceptList = new ArrayList<>();
        if (null == accept) {
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < accept.length(); i++) {
            if (' ' == accept.charAt(i)) {
                acceptList.add(stringBuilder.toString());
                stringBuilder.delete(0, stringBuilder.length());
            } else {
                stringBuilder.append(accept.charAt(i));
            }
        }
    }

    public void createSubmitList() {
        submitList = new ArrayList<>();
        if (null == submit) {
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < submit.length(); i++) {
            if (' ' == submit.charAt(i)) {
                submitList.add(stringBuilder.toString());
                stringBuilder.delete(0, stringBuilder.length());
            } else {
                stringBuilder.append(submit.charAt(i));
            }
        }
    }

    public void createFailedList() {
        failedList = new ArrayList<>();
        HashSet<String> acceptSet = new HashSet<>(acceptList);
        for (String i : submitList) {
            if (!acceptSet.contains(i)) {
                failedList.add(i);
            }
        }
    }

    public ArrayList<String> getAcceptList() {
        return acceptList;
    }

    public void setAcceptList(ArrayList<String> acceptList) {
        this.acceptList = acceptList;
    }

    public ArrayList<String> getSubmitList() {
        return submitList;
    }

    public void setSubmitList(ArrayList<String> submitList) {
        this.submitList = submitList;
    }

    public ArrayList<String> getFailedList() {
        return failedList;
    }

    public void setFailedList(ArrayList<String> failedList) {
        this.failedList = failedList;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
