package SpringBoot.Controllers;

import Database.ProblemPage;
import SpringBoot.OnlineJudgeApplication;
import SpringBoot.WebCache.ProblemSetCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class ProblemSetController {
    @GetMapping(path = "problemset/{pagenumber}")
    public String toRefreshProblemSet(@PathVariable("pagenumber") Integer pageNumber, ModelMap model, HttpSession session) {

        Object usernameObject = model.getAttribute("loginUser");
        if (null == usernameObject) {
            if (pageNumber == 1) {
                model.addAttribute("pageMessage", ProblemSetCache.firstProblemSet);
            } else {
                model.addAttribute("pageMessage", new ProblemPage().GetProblemPage(pageNumber));
            }
        } else {
            String username = usernameObject.toString();
            model.addAttribute("pageMessage", new ProblemPage().GetProblemPage(pageNumber, username));
        }

        return "problemset";
    }
}
