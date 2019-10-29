package SpringMVC.Controllers;

import Data.Users.MainUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
    @RequestMapping(path = "userpage", method = {RequestMethod.POST})
    public String login(MainUser user){
        return "userpage";
    }
}
