package data.school;

import org.springframework.context.annotation.Bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class School implements Serializable {
    private ArrayList<String> schoolList = new ArrayList<>();

    @Override
    public String toString() {
        return "School{" +
                "schoolList=" + schoolList +
                '}';
    }

    public ArrayList<String> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(ArrayList<String> schoolList) {
        this.schoolList = schoolList;
    }
}
