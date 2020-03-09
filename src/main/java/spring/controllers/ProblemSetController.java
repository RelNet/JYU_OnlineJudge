package spring.controllers;

import data.problem.Problem;
import data.user.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import spring.cache.ProblemSetCache;
import spring.cache.UserInfoCache;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;

@Controller
public class ProblemSetController {
    private static final Logger logger = LoggerFactory.getLogger(ProblemSetController.class);

    @Autowired
    private ProblemSetCache problemSetCache;

    @Autowired
    private UserInfoCache userInfoCache;

    @GetMapping(path = "set/{page}")
    public String toRefreshProblemSet(@PathVariable("page") Integer page, ModelMap modelMap, HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId");
        if (null == userId) {
            return "error/401";
        }
        ArrayList<Problem> list = problemSetCache.get(page);
        UserInfo userInfo = userInfoCache.get(userId);
        getPrivateSet(list, userInfo.getAccept());
        modelMap.addAttribute("problems", list);
        modelMap.addAttribute("pages", problemSetCache.pages());

        return "problem/set";
    }

    /**
     * 筛选已经做过的题目
     *
     * @param list
     * @param accept
     */
    private void getPrivateSet(ArrayList<Problem> list, String accept) {
        StringBuilder stringBuilder = new StringBuilder();
        HashSet<String> acceptSet = new HashSet<>();

        if (null == accept) {
            return;
        }
        for (int i = 0; i < accept.length(); i++) {
            if (accept.charAt(i) == ' ') {
                acceptSet.add(stringBuilder.toString());
                stringBuilder.delete(0, stringBuilder.length());
            } else {
                stringBuilder.append(accept.charAt(i));
            }
        }
        for (Problem i : list) {
            if (acceptSet.contains(i.getId().toString())) {
                i.setPass(true);
            }
        }
    }
}
