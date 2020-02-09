package SpringBoot.Cache;

import Database.ProblemPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 获取ProblemSet缓存
 */
public class ProblemSetCache {
    private static final Logger logger = LoggerFactory.getLogger(ProblemSetCache.class);

    /**
     * 缓存第page页、用户名为username的题集页信息
     * @param page
     * @param username
     * @return
     */
    @Cacheable(cacheNames = "ProblemSet", condition = "#page == 1", cacheManager = "ProblemSetCacheManager")
    public List<ProblemPage> getProblemSet(Integer page, String username) {
        return new ProblemPage().GetProblemPage(page, username);
    }
}
