package SpringBoot.Controllers;

import Data.Users.MainUser;
import Database.OJProblemStatus.ProblemStatus;
import SpringBoot.WebCache.StatusCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class StatusController {
    @GetMapping(path = "status/{contestid}/{pagenumber}")
    public String toStatus(@PathVariable("contestid") Integer contestId, @PathVariable("pagenumber") Integer pageNumber
            , ModelMap model) {

//        if (contestId == 0 && pageNumber <= StatusCache.cacheLength) {
//            model.addAttribute("statusMessage", StatusCache.cache.get(pageNumber - 1));
//        } else {
            model.addAttribute("statusMessage", new ProblemStatus().GetStatuspage(pageNumber, contestId));
//        }
        return "status";
    }
}
