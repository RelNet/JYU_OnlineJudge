package data.problem;

import java.io.Serializable;

public class Option implements Serializable {
    private String description;
    private Integer index;

    @Override
    public String toString() {
        return "Option{" +
                "description='" + description + '\'' +
                ", index=" + index +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
