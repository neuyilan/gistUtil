package io.bio.threadpool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import utils.TimeUtils;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description 阻塞式I/O创建的客户端
 * @since 2020-07-30 12:07
 */
public class Client {
    /**
     * 默认的端口号
     */
    private static int DEFAULT_SERVER_PORT = 12345;
    private static String DEFAULT_SERVER_IP = "127.0.0.1";

    public static void send(String clientName, String expression) {
        send(clientName, DEFAULT_SERVER_PORT, expression);
    }

    public static void send(String clientName, int port, String expression) {
        // System.out.println(TimeUtils.getNowTime() + "the expression = " + expression);
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            System.out.println(TimeUtils.getNowTime() + clientName + " the client start connect the server");
            // 模拟accept阻塞
            Thread.sleep(5000);
            socket = new Socket(DEFAULT_SERVER_IP, port);
            // 模拟数据阻塞
            Thread.sleep(5000);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(clientName);
            System.out
                .println(TimeUtils.getNowTime() + clientName + " the result returned from server = " + in.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if (out != null) {
                out.close();
                out = null;
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }
}
