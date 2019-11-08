package SpringBoot.Controllers;

import Data.Users.MainUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class HomeController {
    @GetMapping(path = "problemset")
    public String toProblemSet() {
        return "problemset";
    }

    @GetMapping(path = "contests")
    public String toContests() {
        return "contests";
    }

    @GetMapping(path = "status")
    public String toStatus() {
        return "status";
    }

    @GetMapping(path = "userpage")
    public String toUserPage() {
        return "userpage";
    }

    @GetMapping(path = "register")
    public String toRegister(){
        return "register";
    }

    @GetMapping(path = "/")
    public String toHome(){
        return "index";
    }

    // 提交登陆表单之后刷新
    @PostMapping("/")
    public String login(MainUser user,
                              Map<String, Object> informationMap, HttpSession session) {
        session.setAttribute("loginUser", user.getUsername());
        return "index";
    }
}
