package Data.Problem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// 存放problem数据
public class MainProblem implements Serializable {
    private ArrayList<String> courseCodes;
    private Integer submitMode;
    private String username;
    private Long submitID;
    private Date submitDate;

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public ArrayList<String> getCourseCodes() {
        return courseCodes;
    }

    public void setCourseCodes(ArrayList<String> courseCodes) {
        this.courseCodes = courseCodes;
    }

    public void setSubmitMode(Integer submitMode) {
        this.submitMode = submitMode;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSubmitID(Long submitID) {
        this.submitID = submitID;
    }

    public Integer getSubmitMode() {
        return submitMode;
    }

    public String getUsername() {
        return username;
    }

    public Long getSubmitID() {
        return submitID;
    }
}
