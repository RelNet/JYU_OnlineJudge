package SpringBoot.Controllers;

import Data.Users.MainUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

//@Controller
//public class UserControlPanelController {
//    @PostMapping(path = "userpage/")
//    public String login(MainUser user,
//                        Map<String, Object> informationMap,
//                        HttpSession session){
//        // 此处缺少登陆检测接口
//        if (false){
//            session.setAttribute("loginUser", user.getUsername());
//            return "userpage";
//        }else{
//            return "redirect:/";
//        }
//    }
//}


// 测试登陆信息获取
@Controller
public class UserController{
    @GetMapping(path = "login")
    public String goLogin(){
        return "logintest";
    }

    @PostMapping(path = "userpage")
    public String (MainUser user){
        return "userpage";
    }
}
