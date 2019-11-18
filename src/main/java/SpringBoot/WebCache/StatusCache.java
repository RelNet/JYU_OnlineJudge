package SpringBoot.WebCache;

import Database.OJProblemStatus.ProblemStatus;

import java.util.ArrayList;
import java.util.List;

// 状态页面缓存类
public class StatusCache extends Thread {
    // 更新缓存时间间隔
    static Long refreshRate = 1000L;

    // 默认缓存页数
    public static Long cacheLength = 2L;

    public static volatile List<List<ProblemStatus>> cache;

    public Long getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(Long refreshRate) {
        this.refreshRate = refreshRate;
    }

    public Long getCacheLength() {
        return cacheLength;
    }

    public void setCacheLength(Long cacheLength) {
        this.cacheLength = cacheLength;
    }

    @Override
    public void run() {
        // 每隔refreshRate毫秒从数据库中获取新的缓存
        while (true) {
            cache = new ArrayList<>();
            ProblemStatus problemStatus = new ProblemStatus();
            for (int i = 1; i <= cacheLength; i++) {
                cache.add(problemStatus.GetStatuspage(i, 0));
            }
            try {
                Thread.sleep(refreshRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
