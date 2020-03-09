package spring.controllers.test

import judge.JudgeSystemConstant
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping

@Controller
public class TestController {
    @GetMapping(path = ["test"])
    public fun toTest(modelMap: ModelMap): String {
        modelMap.addAttribute("test", JudgeSystemConstant.AC)
        return "test/test"
    }
}