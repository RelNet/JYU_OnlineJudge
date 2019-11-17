package SpringBoot.Controllers;

import Data.Users.MainUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class ProblemSetController {
    @GetMapping(path = "problemset/{pagenumber}")
    public String toRefreshProblemSet(@PathVariable("pagenumber") Integer pageNumber, Model model, HttpSession session) {



        // 检查有没有登陆
        Object username = session.getAttribute("loginUser");
        if (username != null) {
            // 登陆过就修改主页显示
            model.addAttribute(username);
        }
        return "problemset";
    }
}
