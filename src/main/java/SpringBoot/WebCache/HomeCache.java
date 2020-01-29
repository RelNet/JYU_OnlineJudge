package SpringBoot.WebCache;

import Data.Contest.MainContest;
import Data.Problems.MainProblemMessage;

import java.util.ArrayList;
import java.util.List;

// 主页缓存
public class HomeCache extends Thread {
    // 热门题目
    public static volatile List<MainProblemMessage> topTags = new ArrayList<>();

    // 热门比赛
    public static volatile List<MainContest> topContests = new ArrayList<>();

    // 即将进行的比赛
    public static volatile List<MainContest> comingContests = new ArrayList<>();

    // 默认60分钟刷新
    static Long refreshRate = 3600000L;

    @Override
    public void run() {
        // 从数据库中获取数据
        while (true) {

            try {
                Thread.sleep(refreshRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
