package SpringBoot.Controllers;

import Data.Problems.*;
import Data.Users.MainUser;
import JudgeSystem.ProblemType;
import JudgeSystem.StartJudge;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class ProblemPageController {
    static StartJudge startJudge = new StartJudge();

    // 从problempage提交之后，转到状态页面
    @PostMapping(path = "status/{contestid}")
    public String submitCourseCodes(@PathVariable("contestid") Integer contestID, MainProblem problem
            ,Model model, HttpSession session) {


        return "status";
    }

    @GetMapping(path = "problempage/{problemid}/{problemtype}")
    public String toProblemPage(@PathVariable("problemid") Integer problemID, @PathVariable("problemtype") Integer problemtype
            , Model model, HttpSession session) {

        model.addAttribute("problemid", problemID);

        /**
         * 0是选择题
         * 1是填空题
         * 2是编译题
         * 3是填空编译题
         */
        // 注入题目详细信息
        switch (problemtype) {
            case 0:
                MCQProblem problemMessage0 = new MCQProblem();
                model.addAttribute("problemDescribe", problemMessage0.GetProblemDescribe(problemID));
                model.addAttribute("problemOption", problemMessage0.GetProblemOption(problemID));
                model.addAttribute("problemTitle", problemMessage0.GetTitle(problemID));
                break;
            case 1:
                FBProblem problemMessage1 = new FBProblem();
                model.addAttribute("problemDescribe", problemMessage1.GetProblemDescribe(problemID));
                model.addAttribute("problemSpaceNumber", problemMessage1.GetAns(problemID).size());
                model.addAttribute("problemTitle", problemMessage1.GetTitle(problemID));
                break;
            case 2:
                CFProblem problemMessage2 = new CFProblem();
                // 获取封装好的问题信息
                List<String> problemMessageList1 = problemMessage2.GetCFPMessage(problemID);
                model.addAttribute("problemDescribe", problemMessageList1.get(0));
                model.addAttribute("problemInputDescribe", problemMessageList1.get(1));
                model.addAttribute("problemOutputDescribe", problemMessageList1.get(2));
                model.addAttribute("problemTimeLimit", problemMessageList1.get(3));
                model.addAttribute("problemMemoryLimit", problemMessageList1.get(4));

                model.addAttribute("problemTitle", problemMessage2.GetTitle(problemID));
                break;
            case 3:
                FCFProblem problemMessage3 = new FCFProblem();
                // 获取封装好的问题信息
                List<String> problemMessageList2 = problemMessage3.GetCFPMessage(problemID);
                model.addAttribute("problemDescribe", problemMessageList2.get(0));
                model.addAttribute("problemInputDescribe", problemMessageList2.get(1));
                model.addAttribute("problemOutputDescribe", problemMessageList2.get(2));
                model.addAttribute("problemTimeLimit", problemMessageList2.get(3));
                model.addAttribute("problemMemoryLimit", problemMessageList2.get(4));

                model.addAttribute("problemTitle", problemMessage3.GetTitle(problemID));
                model.addAttribute("problemSpaceNumber", problemMessage3.GetProblemSpaceNumber(problemID));
                break;
            default:
                return "4xx";
        }

        return "problempage";
    }
}
