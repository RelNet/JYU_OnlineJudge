package JudgeSystem.ResultJudgeComponent;

import JudgeSystem.JudgeSystemConstant;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ResultJudge {
    private File unCheckFile;
    private File standardFile;

    public ResultJudge(File unCheckFile, File standardFile) {
        this.unCheckFile = unCheckFile;
        this.standardFile = standardFile;
    }

    public JudgeSystemConstant runJudge() {
        JudgeSystemConstant controlCode = JudgeSystemConstant.AC;
        try {
            BufferedReader unCheckFileReader = new BufferedReader(new FileReader(unCheckFile));
            List<String> unCheckFileStrings = new ArrayList<>();
            readerToArray(unCheckFileReader, unCheckFileStrings);
            unCheckFileReader.close();
            BufferedReader standardFileReader = new BufferedReader(new FileReader(standardFile));
            List<String> standardFileStrings = new ArrayList<>();
            readerToArray(standardFileReader, standardFileStrings);
            standardFileReader.close();


            int unCheckFileStringIndex = 0, standardFileStringIndex = 0;
            int unCheckFileStringCharAt = 0, standardFileStringCharAt = 0;
            for (; standardFileStringIndex < standardFileStrings.size(); standardFileStringIndex++) {
                if (unCheckFileStringIndex >= unCheckFileStrings.size()) {
                    return JudgeSystemConstant.WA;
                }
                standardFileStringCharAt = 0;
                if (!unCheckFileStrings.get(unCheckFileStringIndex).equals(standardFileStrings.get(standardFileStringIndex))) {
                    controlCode = JudgeSystemConstant.PE;
                    for (; standardFileStringCharAt < standardFileStrings.get(standardFileStringIndex).length(); standardFileStringCharAt++) {
                        if (unCheckFileStringIndex >= unCheckFileStrings.size() && standardFileStringCharAt < standardFileStrings.get(standardFileStringIndex).length()) {
                            return JudgeSystemConstant.WA;
                        }
                        if (unCheckFileStrings.get(unCheckFileStringIndex).charAt(unCheckFileStringCharAt) != standardFileStrings.get(standardFileStringIndex).charAt(standardFileStringCharAt)) {
                            return JudgeSystemConstant.WA;
                        }
                        if (unCheckFileStringCharAt < unCheckFileStrings.get(unCheckFileStringIndex).length()) {
                            unCheckFileStringCharAt++;
                        }
                        if (unCheckFileStringCharAt == unCheckFileStrings.get(unCheckFileStringIndex).length()) {
                            unCheckFileStringCharAt = 0;
                            unCheckFileStringIndex++;
                        }
                        if (standardFileStringCharAt + 1 == standardFileStrings.get(standardFileStringIndex).length() && standardFileStringIndex + 1 == standardFileStrings.size()) {
                            for (;unCheckFileStringIndex<unCheckFileStrings.size ();unCheckFileStringIndex++) {

                                for(;unCheckFileStringCharAt < unCheckFileStrings.get(unCheckFileStringIndex).length();unCheckFileStringCharAt++){
                                    if (!Character.isSpaceChar(unCheckFileStrings.get(unCheckFileStringIndex).charAt(unCheckFileStringCharAt))) {
                                        return JudgeSystemConstant.WA;
                                    }
                                }
                                unCheckFileStringCharAt=0;
                            }
                        }
                    }

                } else {
                    unCheckFileStringIndex++;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
            return JudgeSystemConstant.RJ;           // 文件读写异常
        }
        return controlCode;
    }

    void readerToArray(BufferedReader bufferedReader, List<String> array) {
        String string;
        try {
            while ((string = bufferedReader.readLine()) != null) {
                array.add(string);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
