package old.Data.Home;

import old.Data.Contest.MainContest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeInfo implements Serializable {
    // 正在进行的比赛
    public List<MainContest> topContests = new ArrayList<>();

    // 通知
    public List<String> notice = new ArrayList<>();

    @Override
    public String toString() {
        return "HomeMessage{" +
                "topContests=" + topContests +
                ", notice=" + notice +
                '}';
    }

    public List<String> getNotice() {
        return notice;
    }

    public void setNotice(List<String> notice) {
        this.notice = notice;
    }

    public List<MainContest> getTopContests() {
        return topContests;
    }

    public void setTopContests(List<MainContest> topContests) {
        this.topContests = topContests;
    }

    public HomeInfo() {

    }
}
