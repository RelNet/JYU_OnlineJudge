package SpringBoot.Controllers;

import Data.Problems.*;
import Data.Submit.MainSubmit;
import Database.OJProblemStatus.ProblemStatus;
import JudgeSystem.*;
import SpringBoot.Cache.ProblemPageCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProblemPageController {
    private static final Logger logger = LoggerFactory.getLogger(ProblemPageController.class);

    // 从problempage提交之后，转到状态页面
    @PostMapping(path = "status/0/1")
    public String submitCourseCodes(String courseCode, Integer languageType, Integer problemId
            , ModelMap model, HttpSession session) {

        Object username = session.getAttribute("loginUser");
        if (username != null) {
            MainSubmit submit = new MainSubmit();
            submit.setUsername(username.toString());
            submit.setProblemType(ProblemType.CF);
            submit.setContestID(0);
            submit.setProblemId(problemId);
            List<String> courseCodes = new ArrayList<>();
            courseCodes.add(courseCode);
            submit.setCourseCodes(courseCodes);
            switch (languageType) {
                case 0:
                    submit.setLanguageType(JudgeLanguage.GPP);
                    break;
                case 1:
                    submit.setLanguageType(JudgeLanguage.JAVA);
                    break;
                case 2:
                    submit.setLanguageType(JudgeLanguage.PYTHON);
                    break;
                default:
                    return "4xx";
            }
        } else {
            model.addAttribute("errorMessage", "你没有权限提交");
            return "4xx";
        }
        model.addAttribute("statusMessage", new ProblemStatus().GetStatuspage(1, 0));

        return "status";
    }

    /**
     * 0是选择题
     * 1是填空题
     * 2是编译题
     * 3是填空编译题
     */
    @GetMapping(path = "problempage/{contestId}/{problemId}/0")
    public String toMCQProblemPage(@PathVariable("problemId") Integer problemID
            , ModelMap model) {

        // 注入题目详细信息
        ProblemPageCache cache = new ProblemPageCache();
        MCQProblem problem = cache.getMCQProblem(problemID);
        model.addAttribute("problemMessage", problem);

        return "problem/mcq";
    }

    @GetMapping(path = "problempage/{contestId}/{problemId}/1")
    public String toFBProblem(@PathVariable("problemId") Integer problemId
            , @PathVariable("contestId") Integer contestId, ModelMap model) {

        ProblemPageCache cache = new ProblemPageCache();
        FBProblem problem = cache.getFBProblem(problemId);
        model.addAttribute("problemMessage", problem);

        return "problem/fb";
    }

    @GetMapping(path = "problempage/{contestId}/{problemId}/2")
    public String toCFProblem(@PathVariable("problemId") Integer problemId
            , @PathVariable("contestId") Integer contestId, ModelMap model) {

        ProblemPageCache cache = new ProblemPageCache();
        CFProblem problem = cache.getCFProblem(problemId);
        model.addAttribute("problemMessage", problem);

        return "problem/cf";
    }
}
