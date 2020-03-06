package judge.connect;

import old.Data.Submit.MainSubmit;
import judge.JudgeConfig;
import spring.cache.JudgeServiceCache;
import spring.error.JudgeServiceException;
import spring.OnlineJudgeApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * 连接judge服务器
 */
public class JudgeConnect {
    public static final Logger logger = LoggerFactory.getLogger(JudgeConnect.class);

    static final String HELLO = "Hello";
    static final String GET_ACTIVE_DOCKERS = "Get active dockers";
    static final String SEDN_TASK = "Send task";
    static final String SEND_COMPLETE = "Send complete";
    static final String OK = "OK";

    JudgeConfig config;
    Socket socket;

    public JudgeConnect(JudgeConfig config) {
        this.config = config;
    }

    public JudgeConnect() {

    }

    /**
     * 发送打招呼信息到服务器，等待服务器响应
     *
     * @return 如果服务器响应，return true，否则 false
     * @throws IOException
     */
    public boolean checkActive() {
        try {
            socket = new Socket(config.getIp(), JudgeConfig.port);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            byte[] inputBytes = new byte[1024];

            outputStream.write(HELLO.getBytes(StandardCharsets.UTF_8));

            int length = inputStream.read(inputBytes);
            String judgeMessage = new String(inputBytes, 0, length, StandardCharsets.UTF_8);

            outputStream.close();
            inputStream.close();
            socket.close();
            return HELLO.equals(judgeMessage);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取活动docker数量
     *
     * @return
     */
    public Integer getActiveDockers() {
        try {
            socket = new Socket(config.getIp(), JudgeConfig.port);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            byte[] inputBytes = new byte[1024];

            outputStream.write(GET_ACTIVE_DOCKERS.getBytes(StandardCharsets.UTF_8));

            inputStream.read(inputBytes);

            outputStream.close();
            inputStream.close();
            socket.close();
            return (int) inputBytes[0];
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 发送任务到任务压力最小的服务器中
     *
     * @param submit
     */
    public void sendTask(MainSubmit submit) throws JudgeServiceException {
        JudgeServiceCache cache = new JudgeServiceCache();

        // 轮询服务器压力
        TemplateJudgeData templateJudgeData = new TemplateJudgeData();
        for (String ip : OnlineJudgeApplication.judgeServiceList) {
            JudgeConfig tempConfig = cache.get(ip);
            int tempBusyness = tempConfig.getTask() / tempConfig.getActiveDockers();
            if (tempBusyness < templateJudgeData.busyness) {
                templateJudgeData.ip = ip;
                templateJudgeData.busyness = tempBusyness;
            }
        }

        try {
            socket = new Socket(templateJudgeData.ip, JudgeConfig.port);
        } catch (IOException e) {
            e.printStackTrace();
            deleteJudge(templateJudgeData.ip, cache);
            throw new JudgeServiceException();
        }

        // 先发送 SEDN_TASK 告知judge服务器即将发送任务
        // 服务器会发送 OK
        // 再发送任务
        // SEND_COMPLETE 告知服务器发送完毕
        // 服务器会发送OK
        try {
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            byte[] buf = new byte[1024];
            int length;

            outputStream.write(SEDN_TASK.getBytes(StandardCharsets.UTF_8));
            length = inputStream.read(buf);
            if (OK.equals(new String(buf, 0, length))) {
                outputStream.write(submit.toString().getBytes(StandardCharsets.UTF_8));
                outputStream.write(SEND_COMPLETE.getBytes(StandardCharsets.UTF_8));
                length = inputStream.read(buf);
                if (!OK.equals(new String(buf, 0, length))) {
                    deleteJudge(templateJudgeData.ip, cache);
                    throw new JudgeServiceException();
                }
                cache.update(templateJudgeData.ip);
            } else {
                deleteJudge(templateJudgeData.ip, cache);
                throw new JudgeServiceException();
            }
            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            deleteJudge(templateJudgeData.ip, cache);
            throw new JudgeServiceException();
        }
    }

    private void deleteJudge(String ip, JudgeServiceCache cache) {
        cache.delete(ip);
        OnlineJudgeApplication.judgeServiceList.remove(ip);
        logger.error("judge服务器" + ip + "已从轮询列表中删除");
    }
}

class TemplateJudgeData {
    String ip;
    int busyness = Integer.MAX_VALUE;
}