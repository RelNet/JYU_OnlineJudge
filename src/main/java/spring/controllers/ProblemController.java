package spring.controllers;

import data.problem.Problem;
import judge.JudgeLanguage;
import judge.ProblemType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import spring.cache.ProblemCache;

import javax.servlet.http.HttpSession;

@Controller
public class ProblemController {
    private static final Logger logger = LoggerFactory.getLogger(ProblemController.class);

    @Autowired
    private ProblemCache cache;

    /**
     * 0是选择题
     * 1是填空题
     * 2是编译题
     * 3是填空编译题
     */
    @GetMapping(path = "problem/{contestId}/{problemId}")
    public String toMCQProblemPage(@PathVariable("contestId") Integer contestId, @PathVariable("problemId") Integer problemID
            , HttpSession session, ModelMap modelMap) {

        Integer userId = (Integer) session.getAttribute("userId");
        if (null == userId) {
            return "error/401";
        }

        Problem problem =cache.get(problemID);
        // 注入题目详细信息
        modelMap.addAttribute("contestId", contestId);
        modelMap.addAttribute("type", ProblemType.EnumToInt(problem.getProblemInfo().getType()));
        modelMap.addAttribute("problem",problem);
        modelMap.addAttribute("languages", JudgeLanguage.languages);

        return "problem/problem";
    }
}
