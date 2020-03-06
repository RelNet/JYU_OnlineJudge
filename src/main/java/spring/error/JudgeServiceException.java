package spring.error;

import java.net.SocketException;

public class JudgeServiceException extends SocketException {
    @Override
    public void printStackTrace() {
        System.out.println("丢失judge服务器连接或无可用服务器");
        super.printStackTrace();
    }
}
