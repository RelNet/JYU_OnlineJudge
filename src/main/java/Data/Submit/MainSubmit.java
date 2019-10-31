package Data.Submit;

import Data.Problems.MainProblem;
import Data.Users.MainUser;
import JudgeSystem.JudgeLanguage;
import JudgeSystem.JudgeSystemConstant;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

// 存放problem数据
public class MainSubmit implements Serializable {
    private List<String> courseCodes;
    private Integer submitMode;     // 记录是ACM还是IO判断方式
    private MainUser user;
    private Long submitID;
    private Date submitDate;
    private JudgeLanguage languageType;
    private MainProblem problem;
    private JudgeSystemConstant controlCode;
    private List<JudgeSystemConstant> sampleControlCodeList;

    public MainProblem getProblem() {
        return problem;
    }

    public JudgeSystemConstant getControlCode() {
        return controlCode;
    }

    public List<JudgeSystemConstant> getSampleControlCodeList() {
        return sampleControlCodeList;
    }

    public void setSampleControlCodeList(List<JudgeSystemConstant> sampleControlCodeList) {
        this.sampleControlCodeList = sampleControlCodeList;
    }

    public void setControlCode(JudgeSystemConstant controlCode) {
        this.controlCode = controlCode;
    }

    public void setProblem(MainProblem problem) {
        this.problem = problem;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public List<String> getCourseCodes() {
        return courseCodes;
    }

    public void setCourseCodes(List<String> courseCodes) {
        this.courseCodes = courseCodes;
    }

    public void setSubmitMode(Integer submitMode) {
        this.submitMode = submitMode;
    }

    public void setSubmitID(Long submitID) {
        this.submitID = submitID;
    }

    public Integer getSubmitMode() {
        return submitMode;
    }

    public MainUser getUser() {
        return user;
    }

    public void setUser(MainUser user) {
        this.user = user;
    }

    public JudgeLanguage getLanguageType() {
        return languageType;
    }

    public void setLanguageType(JudgeLanguage languageType) {
        this.languageType = languageType;
    }

    public Long getSubmitID() {
        return submitID;
    }
}
