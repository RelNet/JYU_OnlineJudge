package Data.Submit;

import Data.Problems.MainProblem;
import Data.Users.MainUser;
import JudgeSystem.JudgeLanguage;
import JudgeSystem.JudgeMode;
import JudgeSystem.JudgeSystemConstant;
import JudgeSystem.ProblemType;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

// 存放submit数据
public class MainSubmit implements Serializable {
    private List<String> courseCodes;
    private JudgeMode judgeMode;     // 记录是ACM还是IO判断方式
    private MainUser user;
    private Long submitID;
    private Date submitDate;
    private JudgeLanguage languageType;
    private MainProblem problem;
    private JudgeSystemConstant controlCode;
    private ProblemType problemType;
    private Long contestID;

    public JudgeMode getJudgeMode() {
        return judgeMode;
    }

    public void setJudgeMode(JudgeMode judgeMode) {
        this.judgeMode = judgeMode;
    }

    public ProblemType getProblemType() {
        return problemType;
    }

    public void setProblemType(ProblemType problemType) {
        this.problemType = problemType;
    }

    public MainProblem getProblem() {
        return problem;
    }

    public JudgeSystemConstant getControlCode() {
        return controlCode;
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

    public void setSubmitID(Long submitID) {
        this.submitID = submitID;
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
