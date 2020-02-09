package SpringBoot.Controllers;

import SpringBoot.Cache.ProblemSetCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class ProblemSetController {
    @GetMapping(path = "problemset/{pagenumber}")
    public String toRefreshProblemSet(@PathVariable("pagenumber") Integer pageNumber, ModelMap model, HttpSession session) {

        Object usernameObject = session.getAttribute("loginUser");
        if (null == usernameObject) {
            model.addAttribute("pageMessage", new ProblemSetCache().getProblemSet(pageNumber, null));
        } else {
            String username = usernameObject.toString();
            model.addAttribute("pageMessage", new ProblemSetCache().getProblemSet(pageNumber, username));
        }

        return "problemset";
    }
}
