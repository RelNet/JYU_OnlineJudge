package spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ContestSetController {
    @GetMapping(path = "contestset/{page}")
    public String toContests(@PathVariable("page") Integer page) {


        return "contests";
    }
}
