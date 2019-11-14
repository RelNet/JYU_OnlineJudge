package SpringBoot.Controllers;

import Data.Users.MainUser;
import Database.OjUserMessage.UserLogin;
import JudgeSystem.StartJudge;
import SpringBoot.SessionAndModelConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class HomeController {
    @GetMapping(path = "/")
    public String toHome(Model model, HttpSession session) {
//        // 检查有没有登陆
//        Object username = session.getAttribute(SessionAndModelConstant.LoginUserString);
//        model.addAttribute("path", "/");
//        if (username != null) {
//            // 登陆过就修改主页显示
//            model.addAttribute(username);
//        }
        return "index";
    }

    @PostMapping("/")
    public String login(MainUser user,
                        Model model, HttpSession session) {

        // 将登陆过的用户删除
        if (session.getAttribute(SessionAndModelConstant.LoginUserString) != null) {
            session.removeAttribute(SessionAndModelConstant.LoginUserString);
        }

        // 在数据库中检查登陆数据
        UserLogin userLogin = new UserLogin();
        if (userLogin.CheckUserName(user.getUsername())) {
            if (userLogin.CheckUserLogin(user.getUsername(), user.getPassword())) {
                session.setAttribute(SessionAndModelConstant.LoginUserString, user.getUsername());
            }
        } else {
            model.addAttribute(SessionAndModelConstant.ErrorMessageString, "用户名或者密码错误");
        }
        return "index";
    }

    // 提交登陆表单之后刷新
    @PostMapping("/{path}")
    public String login(@PathVariable String path, MainUser user,
                        Model model, HttpSession session) {

        if (session.getAttribute(SessionAndModelConstant.LoginUserString) != null) {
            session.removeAttribute(SessionAndModelConstant.LoginUserString);
        }

        UserLogin userLogin = new UserLogin();
        if (userLogin.CheckUserName(user.getUsername())) {
            if (userLogin.CheckUserLogin(user.getUsername(), user.getPassword())) {
                session.setAttribute(SessionAndModelConstant.LoginUserString, user.getUsername());
            }
        } else {
            model.addAttribute(SessionAndModelConstant.ErrorMessageString, "用户名或者密码错误");
        }

        // 检查路径以此选择显示页面
        switch (path) {
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
