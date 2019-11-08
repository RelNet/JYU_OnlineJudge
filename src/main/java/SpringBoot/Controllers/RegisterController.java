package SpringBoot.Controllers;

import Data.Users.MainUser;
import Data.Users.StudentUser;
import Database.OjUserMessage.UserRegister;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    @PostMapping(path = "")
    public String registerUser(StudentUser studentInformation, @PathVariable("username") String username) {
        // 插入数据库
        boolean checkDatabaseAction = new UserRegister().register(studentInformation.getUsername(),
                studentInformation.getPassword(), studentInformation.getClassName(), studentInformation.getRealName(),
                studentInformation.getCollegeName(), studentInformation.getStudentID());

        // 检查是否成功
        if (checkDatabaseAction) {
            return "userpage";
        }else {
            return "register";
        }
    }
}
