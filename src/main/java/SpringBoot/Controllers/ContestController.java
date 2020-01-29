package SpringBoot.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ContestController {
    @GetMapping(path = "contest/{pagenumber}")
    public String toContests(@PathVariable("pagenumber") Integer pageNumber) {


        return "contests";
    }
}
