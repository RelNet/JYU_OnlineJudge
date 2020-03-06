package spring.controllers;

import data.user.UserInfo;
import data.user.UserType;
import old.Data.Users.MainUser;
import old.Database.OjUserMessage.GetUserMessage;
import old.Database.OjUserMessage.UpdateUserMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import spring.cache.UserInfoCache;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;

// 用户控制面板控制器
@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserInfoCache cache;

    @GetMapping(path = "user/{id}")
    public String toUser(@PathVariable(value = "id") Integer id, HttpSession session, ModelMap modelMap) {

        Integer userId = (Integer) session.getAttribute("userId");
        if (null == userId || !userId.equals(id)) {
            return "error/401";
        }

        UserInfo info = cache.get(userId);
        modelMap.addAttribute("userType", UserType.EnumToInt(info.getType()));
        modelMap.addAttribute("user", info);

        return "user/user";
    }
}
