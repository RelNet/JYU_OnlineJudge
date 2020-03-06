package spring.controllers.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping(path = "test")
    public String toTest(ModelMap model) {
        model.addAttribute("number", 2);
        model.addAttribute("test2", "test2");
        return "test/test";
    }
}
