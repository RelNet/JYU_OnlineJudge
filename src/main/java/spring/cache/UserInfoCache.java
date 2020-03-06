package spring.cache;

import data.user.UserInfo;
import database.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class UserInfoCache {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private Duration TIMEOUT = Duration.ofMinutes(3);

    private final String USER_INFO_CACHE_NAME = "user_info";

    public UserInfo get(Integer id) {
        UserInfo userInfo = (UserInfo) redisTemplate.opsForValue().get(USER_INFO_CACHE_NAME + id.toString());
        if (null == userInfo) {
            UserInfoService service = UserInfoService.copy();
            userInfo = service.get(id);
            redisTemplate.opsForValue().set(USER_INFO_CACHE_NAME + id.toString(), userInfo, TIMEOUT);
        }
        return userInfo;
    }
}
