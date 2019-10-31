package SpringMVC.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ProblemPageController {
    @RequestMapping(path = "status", method = RequestMethod.POST)
    public String submitCourseCodes(List<String> courseCodes){
        return "status";
    }

    @RequestMapping(path = "problempage", method = RequestMethod.GET)
    public String goProblemPage(){
        return "problempage";
    }
}
