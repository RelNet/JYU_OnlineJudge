package spring.cache;

import data.school.School;
import database.school.SchoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 学校名称缓存
 */
@Service
public class SchoolCache {
    private static final Logger logger = LoggerFactory.getLogger(SchoolCache.class);

    private final String SCHOOL_CACHE_NAME = "schools";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public School get() {
        School school = (School) redisTemplate.opsForValue().get(SCHOOL_CACHE_NAME);
        if (school == null) {
            SchoolService schoolService = SchoolService.copy();
            school = schoolService.get();
            redisTemplate.opsForValue().set(SCHOOL_CACHE_NAME, school);
        }
        return school;
    }

    public School update(School school) {
        SchoolService schoolService = SchoolService.copy();
        redisTemplate.opsForValue().set(SCHOOL_CACHE_NAME, school);
        schoolService.update(school);
        return school;
    }
}
