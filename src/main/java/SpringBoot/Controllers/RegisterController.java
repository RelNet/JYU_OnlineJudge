package SpringBoot.Controllers;

import Data.Users.MainUser;
import Data.Users.StudentUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    @PostMapping(path = "userpage")
    public String registerUser(StudentUser studentInformation){
        return "userpage";
    }
}
