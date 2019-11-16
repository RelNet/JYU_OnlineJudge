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
    @PostMapping(path = "status")
    public String submitCourseCodes(List<Object> submitDataList) {

        return "status";
    }

    @GetMapping(path = "problempage/{problemid}/{problemtype}")
    public String toTheProblem(@PathVariable("problemid") String problemID,@PathVariable("problemtype") Integer problemtype
            , Model model, HttpSession session) {

        model.addAttribute("problemid", problemID);

        MainProblemMessage problemMessage;
        /**
         * 0是选择题
         * 1是填空题
         * 2是编译题
         * 3是填空编译题
         */
        switch (problemtype){
            case 0:
                problemMessage = new MCQProblem();
                break;
            case 1:
                problemMessage = new FBProblem();
                break;
            case 2:
                problemMessage = new CFProblem();
                break;
            case 3:
                problemMessage=new FCFProblem();
                break;
            default:

        }
        
        return "problempage";
    }
}
