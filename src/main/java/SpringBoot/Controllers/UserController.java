package SpringBoot.Controllers;

import Data.Users.Admin;
import Data.Users.MainUser;
import Data.Users.StudentUser;
import Data.Users.TeacherUser;
import Database.OjUserMessage.GetUserMessage;
import Database.OjUserMessage.UpdateUserMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;

// 用户控制面板控制器
@Controller
public class UserController {
    private void getUserDataOnDatabase(Model module, String username) {
        String subUsername = username.substring(0, 2);
        MainUser user;
        // 链接数据库
        GetUserMessage getter = new GetUserMessage();

        // username开头以"t_"是老师,"s_"是学生
        switch (subUsername) {
            case "t_":
                user = new TeacherUser();
                user.setRealName(getter.GetUserRealname(username));
                user.setCollegeName(getter.GetUserAcademy(username));
                break;
            case "s_":
                user = new StudentUser();
                user.setClassName(getter.GetUserClass(username));
                user.setCollegeName(getter.GetUserAcademy(username));
                user.setRealName(getter.GetUserRealname(username));
                break;
            default:
                if (username.equals("admin")) {
                    user = new Admin();
                    user.setRealName("管理员爸爸");
                } else {
                    user = null;
                }
        }
        module.addAttribute("userData", user);
    }

    // 控制面板主页
    @GetMapping(path = "userpage/{username}")
    public String toUserPage(@PathVariable("username") String username, Model module, HttpSession session) {
        Object loginUser = session.getAttribute("loginUser");
        String stringUsername = String.valueOf(loginUser);

        // 如果没有登陆或路径有误
        if (loginUser == null || !stringUsername.equals(username)) {
            return "4xx";
        } else {  // 从数据库获取用户信息
            getUserDataOnDatabase(module, username);
        }

        return "userpage";
    }


    // 控制面板修改页面
    @GetMapping(path = "userpage/{username}/change")
    public String toChangeUserData(@PathVariable("username") String username, Model module, HttpSession session) {
        MainUser userData = null;
        Object loginUser = session.getAttribute("loginUser");
        String stringUsername = String.valueOf(loginUser);

        // 如果没有登陆或路径有误
        if (loginUser == null || !stringUsername.equals(username)) {
            return "4xx";
        } else {  // 从数据库获取用户信息
            getUserDataOnDatabase(module, username);
        }

        return "userpage_change";
    }

    // 控制面板修改页面提交后转到控制面板首页
    @PutMapping(path = "userpage/{username}")
    public String putUserPage(@PathVariable("username") String userName, Model model, HttpSession session
            , String oldpassword, String newpassword, String classname, String college
            , String realname) {

        Object loginUser = session.getAttribute("loginUser");
        String stringUsername = String.valueOf(loginUser);
        if (loginUser == null || !stringUsername.equals(userName)) {
            model.addAttribute("errorMessage", "你的路径和你的登陆用户名不匹配");
            return "4xx";
        } else {
            UpdateUserMessage updateUserMessage = new UpdateUserMessage();
            // 存在此用户
            if (updateUserMessage.CheckUserName(userName)) {
                updateUserMessage.UpdatePassword(userName, newpassword);
            } else { // 不存在此用户
                model.addAttribute("errorMessage", "数据库中找不到此用户");
                return "4xx";
            }
        }

        return "userpage";
    }
}
