package SpringBoot.WebCache;

import org.springframework.ui.context.Theme;

// 状态页面缓存类
public class StatusCache extends Thread{
    // 更新缓存时间间隔
    Long refreshRate = 1000L;
    // 默认缓存页数
    Long cacheLength = 2L;

    @Override
    public void run() {
        // 从数据库中获取新的缓存
        while(true) {
            
            try {
                Thread.sleep(refreshRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
