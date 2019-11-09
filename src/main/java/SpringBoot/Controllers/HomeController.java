package SpringBoot.Controllers;

import Data.Users.MainUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class HomeController {
    @GetMapping(path = "contests")
    public String toContests() {
        return "contests";
    }

    @GetMapping(path = "status")
    public String toStatus() {
        return "status";
    }

    @GetMapping(path = "register")
    public String toRegister() {
        return "register";
    }

    @GetMapping(path = "/")
    public String toHome(Model model, HttpSession session) {
        // 检查有没有登陆
        Object username = session.getAttribute("loginUser");
        model.addAttribute("path", "/");
        if (username != null) {
            // 登陆过就修改主页显示
            model.addAttribute(username);
        }
        return "index";
    }

    @PostMapping("/")
    public String login(MainUser user,
                        Model model, HttpSession session) {

        if (session.getAttribute("loginUser") != null) {
            session.removeAttribute("loginUser");
        }
        session.setAttribute("loginUser", user.getUsername());
        return "index";
    }

    // 提交登陆表单之后刷新
    @PostMapping("/{path}")
    public String login(@PathVariable String path, MainUser user,
                        Model model, HttpSession session) {

        if (session.getAttribute("loginUser") != null) {
            session.removeAttribute("loginUser");
        }
        session.setAttribute("loginUser", user.getUsername());
        switch (path){
            case "/":
            case "":
                return "index";
            case "problemset":
            case "/problemset":
                return "problemset";
            default:
                return null;
        }
    }
}
