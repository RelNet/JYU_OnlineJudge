package data.problem;

import java.io.Serializable;

public class InputOutput implements Serializable {
    private String input;
    private String output;

    @Override
    public String toString() {
        return "InputOutput{" +
                "input='" + input + '\'' +
                ", output='" + output + '\'' +
                '}';
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
