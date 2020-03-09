package spring.cache;

import data.problem.Problem;
import database.problem.SetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;

/**
 * 获取ProblemSet缓存
 */
@Service
public class ProblemSetCache {
    private static final Logger logger = LoggerFactory.getLogger(ProblemSetCache.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private Duration TIMEOUT = Duration.ofMinutes(5);

    private String SET_CACHE_NAME = "problem_set";
    private String MAX_SET_CACHE_NAME = "problem_set_max";

    /**
     * 缓存第page页
     *
     * @param page
     * @return
     */
    public ArrayList<Problem> get(Integer page) {
        ArrayList<Problem> list = (ArrayList<Problem>) redisTemplate.opsForValue().get(SET_CACHE_NAME + page.toString());
        if (null == list) {
            SetService service = SetService.copy();
            list = service.get(page);
            redisTemplate.opsForValue().set(SET_CACHE_NAME + page.toString(), list, TIMEOUT);
        }
        return list;
    }

    /**
     * 获取页数最大值
     *
     * @return
     */
    public ArrayList<Integer> pages() {
        ArrayList<Integer> max = (ArrayList<Integer>) redisTemplate.opsForValue().get(MAX_SET_CACHE_NAME);
        if (null == max) {
            SetService service = SetService.copy();
            max = makeList(service.maxPage());
            redisTemplate.opsForValue().set(MAX_SET_CACHE_NAME, max, TIMEOUT);
        }
        return max;
    }

    private ArrayList<Integer> makeList(int max) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= max; i++) {
            list.add(i);
        }
        return list;
    }
}
