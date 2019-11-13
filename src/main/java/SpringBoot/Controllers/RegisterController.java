package SpringBoot.Controllers;

import Data.Users.MainUser;
import Data.Users.StudentUser;
import Database.OjUserMessage.UserRegister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpSession;

@Controller
public class RegisterController {
    @GetMapping(path = "register")
    public String toRegister() {
        return "register";
    }

    @PostMapping(path = "welcome")
    public String registerUser(StudentUser studentInformation, Model model, HttpSession session) {
        // 插入数据库
        boolean checkDatabaseAction = new UserRegister().register(studentInformation.getUsername(),
                studentInformation.getPassword(), studentInformation.getClassName(), studentInformation.getRealName(),
                studentInformation.getCollegeName(), studentInformation.getID());

        // 检查是否成功
        if (checkDatabaseAction) {
            session.setAttribute("loginUser", studentInformation.getUsername());
        } else {
            model.addAttribute("errorMessage", "不知道出了什么问题，用户创建失败了，请重试");
        }
        return "welcome_to_join_us";
    }

    @GetMapping(path = "welcome")
    public String toWelcome() {
        return "welcome_to_join_us";
    }
}
