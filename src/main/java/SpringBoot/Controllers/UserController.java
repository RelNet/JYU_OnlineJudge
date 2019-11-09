package SpringBoot.Controllers;

import Data.Users.MainUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

// 用户控制面板控制器
@Controller
public class UserController {
    @GetMapping(path = "userpage/{username}")
    public String toUserPage(@PathVariable("username") String username, Model module, HttpSession session) {
        Object user = session.getAttribute("loginUser");
        String stringUsername = String.valueOf(user);
        if (user == null || !stringUsername.equals(username)) {
            username = "null";
        }

        module.addAttribute("user", username);

        return "userpage";
    }
}
