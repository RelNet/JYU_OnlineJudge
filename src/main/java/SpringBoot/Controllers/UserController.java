package SpringBoot.Controllers;

import Data.Users.Admin;
import Data.Users.MainUser;
import Data.Users.StudentUser;
import Data.Users.TeacherUser;
import Database.OjUserMessage.GetUserMessage;
import Database.OjUserMessage.UpdateUserMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;

// 用户控制面板控制器
@Controller
public class UserController {
    private void getUserDataOnDatabase(ModelMap model, String username) {
        String subUsername = username.substring(0, 2);
        // 链接数据库

        // username开头以"t_"是老师,"s_"是学生
        switch (subUsername) {
            case "t_":
            case "s_":
                break;
            default:
                if (username.equals("admin")) {
                    model.addAttribute("realName", "管理员爸爸");
                }
        }
    }

    // 控制面板主页
    @GetMapping(path = "userpage/{username}")
    public String toUserPage(@PathVariable("username") String username, ModelMap model, HttpSession session) {
        Object loginUser = session.getAttribute("loginUser");
        String stringUsername = String.valueOf(loginUser);
        Boolean isYourPermission = false;

        // 如果已经登陆并且这是你的账号
        if (loginUser != null && stringUsername.equals(username)) {
            isYourPermission = true;
        }
        getUserDataOnDatabase(model, username);
        model.addAttribute("isYourPermission", isYourPermission);
        GetUserMessage getter = new GetUserMessage();
        model.addAttribute("academy", getter.GetUserAcademy(username));
        model.addAttribute("realName", getter.GetUserRealname(username));
        model.addAttribute("id", getter.GetUserID(username));
        model.addAttribute("class", getter.GetUserClass(username));

        return "userpage/userpage";
    }


    // 控制面板修改页面
    @GetMapping(path = "userpage/{username}/change")
    public String toChangeUserData(@PathVariable("username") String username, ModelMap model, HttpSession session) {
        MainUser userData = null;
        Object loginUser = session.getAttribute("loginUser");
        String stringUsername = String.valueOf(loginUser);

        // 如果没有登陆或路径有误
        if (loginUser == null || !stringUsername.equals(username)) {
            return "4xx";
        } else {  // 从数据库获取用户信息
            getUserDataOnDatabase(model, username);
            model.addAttribute("username", username);
        }

        return "userpage/ChangeInformation";
    }

    // 控制面板修改页面提交后转到控制面板首页
    @PostMapping(path = "userpage/{username}")
    public String putUserPage(@PathVariable("username") String userName, ModelMap model, HttpSession session
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
                updateUserMessage.UpdateAcademy(userName,college);
                updateUserMessage.UpdateClass(userName,classname);
                updateUserMessage.UpdateRealName(userName,realname);
            } else { // 不存在此用户
                model.addAttribute("errorMessage", "数据库中找不到此用户");
                return "4xx";
            }
        }

        return "userpage/userpage";
    }
}
