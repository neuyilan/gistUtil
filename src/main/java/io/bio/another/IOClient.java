package io.bio.another;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * @author HouliangQi(neuyilan @ 163.com)
 * @description
 * @since 2020-07-12 2:58 下午
 */
public class IOClient {
    private final static int THREAD_NUM = 10;

    private static void sendMsg() {
        for (int i = 0; i < THREAD_NUM; i++) {
            // TODO 创建多个线程，模拟多个客户端连接服务端
            new Thread(() -> {
                try {
                    Socket socket = new Socket("127.0.0.1", 3333);
                    while (true) {
                        try {
                            socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                            Thread.sleep(2000);
                            return;
                        } catch (Exception e) {
                        }
                    }
                } catch (IOException e) {
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        sendMsg();
    }
}
