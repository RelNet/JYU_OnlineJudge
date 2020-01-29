package SpringBoot.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping(path = "test")
    public String toTest(ModelMap model) {
        model.addAttribute("number", 2);
        return "test";
    }
}
