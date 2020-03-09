package data.status;

import data.submit.Submit;

import java.io.Serializable;
import java.util.ArrayList;

public class Status implements Serializable {
    private ArrayList<Submit> submits;

    public ArrayList<Submit> getSubmits() {
        return submits;
    }

    public void setSubmits(ArrayList<Submit> submits) {
        this.submits = submits;
    }
}
