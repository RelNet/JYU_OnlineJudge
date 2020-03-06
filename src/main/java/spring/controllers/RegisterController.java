package spring.controllers;

import data.school.School;
import data.user.User;
import data.user.UserInfo;
import database.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spring.cache.SchoolCache;

@Controller
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private SchoolCache cache;

    @GetMapping(path = "register")
    public String toRegister(ModelMap modelMap) {

        School school = cache.get();
        modelMap.addAttribute("schools", school.getSchoolList());

        return "login_register/register";
    }

    @PostMapping(path = "welcome")
    public String register(User user, UserInfo userInfo, ModelMap modelMap) {

        // 插入数据库
        UserService service = UserService.copy();
        Integer id = service.insert(user, userInfo);
        modelMap.addAttribute("id", id);

        return "login_register/welcome";
    }
}
