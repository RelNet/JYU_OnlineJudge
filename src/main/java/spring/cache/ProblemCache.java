package spring.cache;

import data.problem.Problem;
import database.problem.ProblemService;
import old.Data.Problems.CFProblem;
import old.Data.Problems.FBProblem;
import old.Data.Problems.FCFProblem;
import old.Data.Problems.MCQProblem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ProblemCache {
    private static final Logger logger = LoggerFactory.getLogger(ProblemCache.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private Duration TIMEOUT = Duration.ofMinutes(5);

    private final String PROBLEM_CACHE_NAME = "problem";

    public Problem get(Integer id) {
        Problem problem = (Problem) redisTemplate.opsForValue().get(PROBLEM_CACHE_NAME + id.toString());
        if (null == problem) {
            ProblemService service = ProblemService.copy();
            problem = service.get(id);
            problem.setId(id);
            redisTemplate.opsForValue().set(PROBLEM_CACHE_NAME + id.toString(), problem);
        }
        return problem;
    }
}
