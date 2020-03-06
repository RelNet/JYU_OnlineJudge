package spring.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping(path = "home")
    public String toHome(HttpSession session, ModelMap modelMap) {

        Integer userId = (Integer) session.getAttribute("userId");
        if (null == userId) {
            return "error/401";
        }
//        HomeCache cache = new HomeCache();
//        modelMap.addAttribute("homeInfo", cache.get());

        return "home/home";
    }
}
