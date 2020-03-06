package judge;

import java.io.Serializable;

/**
 * judge服务器使用7781端口
 */
public class JudgeConfig implements Serializable {
    public static final Integer port = 7781;

    String ip;
    Integer activeDockers;
    Integer task;

    @Override
    public String toString() {
        return "JudgeConfig{" +
                "ip='" + ip + '\'' +
                ", activeDockers=" + activeDockers +
                ", task=" + task +
                '}';
    }

    public Integer getActiveDockers() {
        return activeDockers;
    }

    public void setActiveDockers(Integer activeDockers) {
        this.activeDockers = activeDockers;
    }

    public Integer getTask() {
        return task;
    }

    public void setTask(Integer task) {
        this.task = task;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void addTask() {
        this.task++;
    }

    public JudgeConfig(String ip) {
        this.ip = ip;
        this.task = 0;
    }
}
