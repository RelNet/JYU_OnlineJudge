package spring.cache;

import data.contest.Contest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ContestCache {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public ArrayList<Contest> getSet(Integer page) {
        return null;
    }

    public Contest get(Integer id) {
        return null;
    }
}
