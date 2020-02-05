package SpringBoot.Controllers;

import Data.Users.MainUser;
import Database.OjUserMessage.UserLogin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    // 发起主页get请求
    @GetMapping(path = "/")
    public String toHome(ModelMap model, HttpSession session) {
        model.addAttribute("topTags", HomeCache.topTags);
        model.addAttribute("topContests", HomeCache.topContests);
        model.addAttribute("comingContests", HomeCache.comingContests);
        return "index";
    }

    @PostMapping("/")
    public String login(MainUser user,
                        ModelMap model, HttpSession session) {

        // 将登陆过的用户删除
        if (session.getAttribute("loginUser") != null) {
            session.removeAttribute("loginUser");
        }

        // 在数据库中检查登陆数据
        UserLogin userLogin = new UserLogin();
        if (userLogin.CheckUserName(user.getUsername())) {
            if (userLogin.CheckUserLogin(user.getUsername(), user.getPassword())) {
                session.setAttribute("loginUser", user.getUsername());
                System.out.println(session.getAttribute("loginUser") + "成功登陆!!!!!!!!!");
            } else {
                model.addAttribute("errorMessage", "用户名或者密码错误");
            }
        } else {
            model.addAttribute("errorMessage", "用户名或者密码错误");
        }

        model.addAttribute(HomeCache.topTags);
        model.addAttribute(HomeCache.topContests);
        model.addAttribute(HomeCache.comingContests);

        return "index";
    }

    // 提交登陆表单之后刷新
    @PostMapping("/{path}")
    public String login(@PathVariable String path, MainUser user,
                        ModelMap model, HttpSession session) {

        if (session.getAttribute("loginUser") != null) {
            session.removeAttribute("loginUser");
        }

        UserLogin userLogin = new UserLogin();
        if (userLogin.CheckUserName(user.getUsername())) {
            if (userLogin.CheckUserLogin(user.getUsername(), user.getPassword())) {
                session.setAttribute("loginUser", user.getUsername());
            } else {
                model.addAttribute("errorMessage", "用户名或者密码错误");

            }
        } else {
            model.addAttribute("errorMessage", "用户名或者密码错误");
        }

        // 检查路径以此选择显示页面
        switch (path) {
            case "/":
            case "":
                return "index";
            case "problemset":
            case "/problemset":
                return "problemset";
            case "status":
            case "/status":
                return "status";
            case "contest":
            case "/contest":
                return "contest";
            case "team":
            case "/team":
                return "team";
            default:
                return "4xx";
        }
    }
}
