package spring.controllers;

import data.user.User;
import database.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping(path = "/")
    public String toLogin(HttpSession session) {
        if (session.getAttribute("userId") != null) {
            session.removeAttribute("username");
            session.removeAttribute("userId");
        }
        return "login_register/login";
    }

    @PostMapping(path = "home")
    public String login(User user, HttpSession session, ModelMap modelMap) {

        // 检查数据库
        UserService service = UserService.copy();
        if (service.check(user)) {
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
        } else {
            return "error/401";
        }

        return "home/home";
    }
}
