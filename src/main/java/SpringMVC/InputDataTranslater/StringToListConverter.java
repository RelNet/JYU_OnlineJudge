package SpringMVC.InputDataTranslater;

import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;

public class StringToListConverter implements Converter<String, ArrayList<String>> {
    public ArrayList<String> convert(String courseCodes) {
        if (courseCodes == null) {
            throw new NullPointerException("Course codes string is null");
        }
        ArrayList<String> courseCodeList = new ArrayList<>();
        for (int i = 0; i < courseCodes.length(); i++) {
            int j = i;
            for (; j < courseCodes.length(); j++) {
                if (courseCodes.charAt(j) == '\n') {
                    break;
                }
            }
            courseCodeList.add(courseCodes.substring(i, j - 1));
            i = j + 1;
        }
        return courseCodeList;
    }
}
