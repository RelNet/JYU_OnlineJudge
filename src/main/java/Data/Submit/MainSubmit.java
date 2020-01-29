package Data.Submit;

import Data.Problems.MainProblem;
import Data.Users.MainUser;
import JudgeSystem.JudgeLanguage;
import JudgeSystem.JudgeMode;
import JudgeSystem.JudgeSystemConstant;
import JudgeSystem.ProblemType;
import lombok.Getter;
import org.attoparser.dom.INestableNode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

// 存放submit数据
public class MainSubmit implements Serializable {
    private List<String> courseCodes;
    private JudgeMode judgeMode;     // 记录是ACM还是IO判断方式
    private String username;
    private Integer submitID;
    private Date submitDate;
    private JudgeLanguage languageType;
    private String source;
    private Integer problemId;

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    private JudgeSystemConstant controlCode;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    private ProblemType problemType;
    private Integer contestID;

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

    public JudgeSystemConstant getControlCode() {
        return controlCode;
    }

    public void setControlCode(JudgeSystemConstant controlCode) {
        this.controlCode = controlCode;
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

    public Integer getContestID() {
        return contestID;
    }

    public void setContestID(Integer contestID) {
        this.contestID = contestID;
    }

    public void setSubmitID(Integer submitID) {
        this.submitID = submitID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public JudgeLanguage getLanguageType() {
        return languageType;
    }

    public void setLanguageType(JudgeLanguage languageType) {
        this.languageType = languageType;
    }

    public Integer getSubmitID() {
        return submitID;
    }
}
