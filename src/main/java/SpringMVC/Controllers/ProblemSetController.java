package SpringMVC.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProblemSetController {
    @RequestMapping(path = "/problemset")
    public String redirect(){
        return "problemset";
    }
}
