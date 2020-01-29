package Data.Team;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TeamMessage  implements Serializable {
    public String teamName = null;//团队名字
    public int teamID;//该团队的ID
    public List<String> studentList = new ArrayList<>();//这个团队所有人的用户名
    public String leader = null;//团队发起人
    public String hash;
}
