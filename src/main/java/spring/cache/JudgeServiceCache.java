package spring.cache;

import judge.connect.JudgeConnect;
import judge.JudgeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * judge服务器配置缓存
 */
@Service
public class JudgeServiceCache {
    private static final Logger logger = LoggerFactory.getLogger(JudgeServiceCache.class);

    /**
     * 传入ip，向其发送存活询问
     * 如果存活，则询问其可用容器数量，方便分配任务
     *
     * @param ip
     * @return 如果服务器不存活，ip会更新为 ""
     */
    @Cacheable(cacheNames = "JudgeService", key = "#ip", sync = true, cacheManager = "JudgeServiceCacheManager")
    public JudgeConfig get(String ip) {
        JudgeConfig config = new JudgeConfig(ip);
        JudgeConnect connect = new JudgeConnect(config);
        if (connect.checkActive()) {
            config.setActiveDockers(connect.getActiveDockers());
        } else {
            config.setIp("");
        }

        return config;
    }

    /**
     * 用于更新服务器的任务数量
     *
     * @param ip
     * @return
     */
    @CachePut(cacheNames = "JudgeService", key = "#ip", cacheManager = "JudgeServiceCacheManager")
    public JudgeConfig update(String ip) {
        JudgeConfig config = get(ip);
        config.addTask();

        return config;
    }

    @CacheEvict(cacheNames = "JudgeService", key = "#ip", cacheManager = "JudgeServiceCacheManager")
    public void delete(String ip) {

    }
}
