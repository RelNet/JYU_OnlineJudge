package SpringBoot.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
    @RequestMapping(path = "problemset", method = RequestMethod.GET)
    public String goProblemSet() {
        return "problemset";
    }

    @RequestMapping(path = "contests", method = RequestMethod.GET)
    public String goContests() {
        return "contests";
    }

    @RequestMapping(path = "status", method = RequestMethod.GET)
    public String goStatus() {
        return "status";
    }
}
