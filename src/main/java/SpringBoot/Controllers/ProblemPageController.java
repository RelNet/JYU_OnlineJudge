package SpringBoot.Controllers;

import Data.Problems.*;
import Data.Submit.MainSubmit;
import Database.OJProblemStatus.ProblemStatus;
import JudgeSystem.*;
import SpringBoot.WebCache.StatusCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProblemPageController {
    static final Object addMissionLock = new Object();

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
//        model.addAttribute(StatusCache.cache.get(0));
        model.addAttribute("statusMessage", new ProblemStatus().GetStatuspage(1, 0));

        return "status";
    }

    @GetMapping(path = "problempage/{problemid}")
    public String toProblemPage(@PathVariable("problemid") Integer problemID
            , ModelMap model, HttpSession session) {

        model.addAttribute("problemid", problemID);

        /**
         * 0是选择题
         * 1是填空题
         * 2是编译题
         * 3是填空编译题
         */
        // 注入题目详细信息
//        switch (problemtype) {
//            case 0:
//                MCQProblem problemMessage0 = new MCQProblem();
//                model.addAttribute("problemDescribe", problemMessage0.GetProblemDescribe(problemID));
//                model.addAttribute("problemOption", problemMessage0.GetProblemOption(problemID));
//                model.addAttribute("problemTitle", problemMessage0.GetTitle(problemID));
//                break;
//            case 1:
//                FBProblem problemMessage1 = new FBProblem();
//                model.addAttribute("problemDescribe", problemMessage1.GetProblemDescribe(problemID));
//                Integer size1 = problemMessage1.GetAns(problemID).size();
//                List<Integer> answerNumber1 = new ArrayList<>();
//                for (Integer i = 0; i < size1; i++) {
//                    answerNumber1.add(i);
//                }
//                model.addAttribute("iterator", answerNumber1);
//                model.addAttribute("problemTitle", problemMessage1.GetTitle(problemID));
//                break;
//            case 2:
        CFProblem problemMessage2 = new CFProblem();
        // 获取封装好的问题信息
        List<String> problemMessageList1 = problemMessage2.GetCFPMessage(problemID);
        model.addAttribute("problemDescribe", problemMessageList1.get(0));
        model.addAttribute("problemInputDescribe", problemMessageList1.get(1));
        model.addAttribute("problemOutputDescribe", problemMessageList1.get(2));
        model.addAttribute("problemTimeLimit", problemMessageList1.get(3));
        model.addAttribute("problemMemoryLimit", problemMessageList1.get(4));
        model.addAttribute("sampleIOs", problemMessage2.GetSampleInputOutput(problemID));
        model.addAttribute("problemTitle", problemMessage2.GetTitle(problemID));
        model.addAttribute("problemId", problemID);
//                break;
//            case 3:
//                FCFProblem problemMessage3 = new FCFProblem();
//                // 获取封装好的问题信息
//                List<String> problemMessageList2 = problemMessage3.GetCFPMessage(problemID);
//                model.addAttribute("problemDescribe", problemMessageList2.get(0));
//                model.addAttribute("problemInputDescribe", problemMessageList2.get(1));
//                model.addAttribute("problemOutputDescribe", problemMessageList2.get(2));
//                model.addAttribute("problemTimeLimit", problemMessageList2.get(3));
//                model.addAttribute("problemMemoryLimit", problemMessageList2.get(4));
//
//                model.addAttribute("problemTitle", problemMessage3.GetTitle(problemID));
//                model.addAttribute("problemSpaceNumber", problemMessage3.GetProblemSpaceNumber(problemID));
//                break;
//            default:
//                return "4xx";
//        }

        return "problem/problempage";
    }
}
