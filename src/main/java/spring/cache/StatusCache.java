package spring.cache;

import data.status.Status;
import database.status.StatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;

@Service
public class StatusCache {
    private static final Logger logger = LoggerFactory.getLogger(StatusCache.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ContestCache contestCache;

    private final String STATUS_CACHE_NAME = "status";
    private final Duration TIMEOUT = Duration.ofSeconds(5);

    public Status get(Integer contestId, Integer page) {
        String theCaheName= STATUS_CACHE_NAME + "_" + contestId.toString() + "_" + page.toString();
        Status status = (Status) redisTemplate.opsForValue().get(theCaheName);
        if (null == status) {
            StatusService service = StatusService.copy();
            status = service.get(contestId, page);
            redisTemplate.opsForValue().set(theCaheName, status, TIMEOUT);
        }
        return status;
    }

    public ArrayList<Integer> pages(Integer contest) {
        return null;
    }
}
