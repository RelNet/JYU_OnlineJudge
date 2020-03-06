package judge;

import old.Data.Submit.MainSubmit;
import judge.connect.JudgeConnect;
import spring.error.JudgeServiceException;
import spring.OnlineJudgeApplication;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestJudgeConnect {
    @Test
    public void testConnect() {
        JudgeConfig config = new JudgeConfig("127.0.0.1");
        JudgeConnect connect = new JudgeConnect(config);
        System.out.println(connect.checkActive());
    }

    @Test
    public void testGetActiveDockers() {
        JudgeConfig config = new JudgeConfig("127.0.0.1");
        JudgeConnect connect = new JudgeConnect(config);
        System.out.println(connect.getActiveDockers());
    }

    @Test
    public void testSendTask() throws JudgeServiceException {
        JudgeConfig config = new JudgeConfig("127.0.0.1");
        JudgeConnect connect = new JudgeConnect(config);
        OnlineJudgeApplication.judgeServiceList.add("127.0.0.1");
        MainSubmit submit = new MainSubmit();
        submit.setContestID(0);
        submit.setControlCode(null);
        submit.setJudgeMode(JudgeMode.ACM);
        submit.setUsername("root");
        submit.setSubmitDate(new Date(System.currentTimeMillis()));
        submit.setLanguageType(JudgeLanguage.GPP);
        submit.setSource("nihaoSource");
        submit.setProblemId(233);
        submit.setControlCode(null);
        submit.setProblemType(ProblemType.CF);
        List<String> courseCodes = new ArrayList<>();
        courseCodes.add("nihuai");
        courseCodes.add("测试");
        submit.setCourseCodes(courseCodes);

        connect.sendTask(submit);
    }
}
