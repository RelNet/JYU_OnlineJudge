package SpringBoot.Controllers;

import Database.Team.Team;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class TeamController {
    /**
     * Get某个用户的团队主页
     *
     * @param username
     * @param model
     * @param session
     * @return 页面路径
     */
    @GetMapping(path = "userpage/{username}/team")
    public String toTeam(@PathVariable(value = "username") String username
            , ModelMap model, HttpSession session) {

        Object usernameObject = session.getAttribute("loginUser");
        if (usernameObject != null) {
            String usernameString = usernameObject.toString();
            if (usernameString.equals(username)) {
                model.addAttribute("teamMessage", new Team().getStudentAllTeam(username));
            }else {
                model.addAttribute("errorMessage", "你非法操作了");
                return "error/4xx";
            }
        } else {
            model.addAttribute("errorMessage", "你没有登陆");
            return "error/4xx";
        }

        return "team";
    }
}
