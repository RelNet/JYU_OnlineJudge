package spring.controllers

import data.contest.Contest
import data.status.Status
import judge.JudgeMode
import spring.cache.ContestCache
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import spring.cache.StatusCache
import java.util.ArrayList
import javax.servlet.http.HttpSession

@Controller
public class StatusController {
    private companion object {
        val logger = LoggerFactory.getLogger(StatusController.javaClass)
    }

    @Autowired
    private lateinit var statusCache: StatusCache

    @Autowired
    private lateinit var contestCache: ContestCache

    @GetMapping(path = ["status/{contestId}/{page}"])
    public fun toStatus(@PathVariable("contestId") contestId: Int, @PathVariable("page") page: Int
                        , modelMap: ModelMap, session: HttpSession): String {

        var userId: Int = session.getAttribute("userId") as Int ?: return "error/401"
        val contest = contestCache.get(contestId)
        var status: Status? = null
        var pages: ArrayList<Int>? = null

        // 检查用户有没有权限访问
        // 会检查是不是ACM比赛，或者比赛已经关闭，或者当前用户是创建者
        // 如果有，就执行if中的语句
        if (contest.mode == JudgeMode.ACM || contest.isClose || contest.info.masterId != userId) {
            status = statusCache.get(contestId, page)
            pages = statusCache.pages(contestId)
        }

        modelMap.addAttribute("status", status)
        modelMap.addAttribute("pages", pages)

        return "status/status"
    }
}