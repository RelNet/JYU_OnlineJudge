package SpringBoot.Controllers;

import Data.Users.MainUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 用户控制面板控制器
@Controller
public class UserController {
    @GetMapping(path = "userpage/{username}")
    public String toUserPage(@PathVariable("username") String username, Model module) {
        // 缺少获取已经存在的用户的信息类
        MainUser user = null;

        module.addAttribute("user", user);

        return "userpage";
    }
}
