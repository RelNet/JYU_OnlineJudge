package SpringBoot.Controllers;

import Data.Users.MainUser;
import Database.ProblemPage;
import SpringBoot.SessionAndModelConstant;
import SpringBoot.WebCache.ProblemSetCache;
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

        // 等于1就调用缓存
        if (pageNumber == 1) {

        } else {
            // 如果已经登陆
            Object usernameObject = session.getAttribute(SessionAndModelConstant.LoginUserString);
            String username = String.valueOf(usernameObject);
            if (username != null) {
                model.addAttribute("problemsetMessage", new ProblemPage().GetProblemPage(pageNumber, username));
            } else {

            }
        }

        // 检查有没有登陆
        Object username = session.getAttribute("loginUser");
        if (username != null) {
            // 登陆过就修改主页显示
            model.addAttribute(username);
        }

        return "problemset";
    }
}
