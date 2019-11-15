package SpringBoot.Controllers;

import Data.Users.MainUser;
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

    @GetMapping(path = "problempage/{problemid}")
    public String toTheProblem(@PathVariable("problemid") String problemID, Model model, HttpSession session) {
        model.addAttribute("problemid", problemID);
        
        return "problempage";
    }
}
