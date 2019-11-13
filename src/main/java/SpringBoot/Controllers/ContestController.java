package SpringBoot.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContestController {
    @GetMapping(path = "contests")
    public String toContests() {
        return "contests";
    }
}
