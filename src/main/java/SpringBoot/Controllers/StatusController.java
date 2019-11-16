package SpringBoot.Controllers;

import Data.Users.MainUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class StatusController {
    @GetMapping(path = "status/{pagenumber}")
    public String toStatus(@PathVariable("pagenumber") Integer pageNumber) {

        return "status";
    }
}
