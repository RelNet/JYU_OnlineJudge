package data.problem;

import judge.ProblemType;

import java.io.Serializable;
import java.util.ArrayList;

public class ProblemInfo implements Serializable {
    private ProblemType type;
    private String description;
    private Integer memory;
    private Integer timeout;
    private ArrayList<InputOutput> inputOutputs;
    private ArrayList<Integer> spaces;
    private ArrayList<Option> options;
    private String inputDescription;
    private String outputDescription;

    public void setSpaces(int spacesNum) {
        spaces = new ArrayList<>();
        for (int i = 1; i <= spacesNum; i++) {
            spaces.add(i);
        }
    }

    public void setInputOutputs(String input, String output) {
        inputOutputs = new ArrayList<>();
        int end = 0;
        while ((end = input.indexOf("%%%")) != -1) {
            InputOutput inputOutput = new InputOutput();
            inputOutput.setInput(input.substring(0, end));
            inputOutputs.add(inputOutput);
            input = input.substring(end + 3);
        }
        int index = 0;
        while ((end = output.indexOf("%%%")) != -1) {
            InputOutput inputOutput = inputOutputs.get(index++);
            inputOutput.setOutput(output.substring(0, end));
            output = output.substring(end + 3);
        }
    }

    public void setOptions(String optionsStr) {
        options = new ArrayList<>();
        int end = 0;
        while ((end = optionsStr.indexOf("%%%")) != -1) {
            Option option = new Option();
            option.setDescription(optionsStr.substring(0, end));
            options.add(option);
            optionsStr = optionsStr.substring(end + 3);
        }
        for (int i = 0; i < options.size(); i++) {
            options.get(i).setIndex(i + 1);
        }
    }

    @Override
    public String toString() {
        return "ProblemInfo{" +
                "type=" + type +
                ", description='" + description + '\'' +
                ", memory=" + memory +
                ", timeout=" + timeout +
                ", inputOutputs=" + inputOutputs +
                ", spaces=" + spaces +
                ", options=" + options +
                ", inputDescription='" + inputDescription + '\'' +
                ", outputDescription='" + outputDescription + '\'' +
                '}';
    }

    public String getInputDescription() {
        return inputDescription;
    }

    public void setInputDescription(String inputDescription) {
        this.inputDescription = inputDescription;
    }

    public String getOutputDescription() {
        return outputDescription;
    }

    public void setOutputDescription(String outputDescription) {
        this.outputDescription = outputDescription;
    }

    public ProblemType getType() {
        return type;
    }

    public void setType(ProblemType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public ArrayList<InputOutput> getInputOutputs() {
        return inputOutputs;
    }

    public void setInputOutputs(ArrayList<InputOutput> inputOutputs) {
        this.inputOutputs = inputOutputs;
    }

    public ArrayList<Integer> getSpaces() {
        return spaces;
    }

    public void setSpaces(ArrayList<Integer> spaces) {
        this.spaces = spaces;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }
}
