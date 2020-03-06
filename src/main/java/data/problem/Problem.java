package data.problem;

import java.io.Serializable;

public class Problem implements Serializable {
    private Integer id;
    private String title;
    private Integer submit;
    private Integer accept;
    private boolean pass;
    private String source;

    private ProblemInfo problemInfo;

    @Override
    public String toString() {
        return "Problem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", submit=" + submit +
                ", accept=" + accept +
                ", pass=" + pass +
                ", source='" + source + '\'' +
                ", problemInfo=" + problemInfo +
                '}';
    }

    public ProblemInfo getProblemInfo() {
        return problemInfo;
    }

    public void setProblemInfo(ProblemInfo problemInfo) {
        this.problemInfo = problemInfo;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSubmit() {
        return submit;
    }

    public void setSubmit(Integer submit) {
        this.submit = submit;
    }

    public Integer getAccept() {
        return accept;
    }

    public void setAccept(Integer accept) {
        this.accept = accept;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
