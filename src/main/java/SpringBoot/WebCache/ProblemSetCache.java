package SpringBoot.WebCache;

import Database.ProblemPage;

import java.util.List;

// 题集页缓存
public class ProblemSetCache extends Thread {
    // 只缓存一页
    public volatile List<ProblemPage> firstProblemSet;
    // 默认10分钟刷新
    public static Long refreshRate = 3600000L;

    @Override
    public void run() {
        // 从数据库获取problemset第一页的数据
        while (true) {
            firstProblemSet = new ProblemPage().GetProblemPage(1, null);
            try {
                Thread.sleep(refreshRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
