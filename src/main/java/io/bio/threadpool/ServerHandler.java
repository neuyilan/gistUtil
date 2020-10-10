package io.bio.threadpool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import utils.TimeUtils;

public class ServerHandler implements Runnable {
    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            // 模拟数据被阻塞
            System.out.println(TimeUtils.getNowTime() + "start read msg from client");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            String expression;
            String result;
            while (true) {
                if ((expression = in.readLine()) == null) {
                    break;
                }
                System.out.println(TimeUtils.getNowTime() + "the server received msg=" + expression);
                result = "hello " + expression;
                // try {
                // result = Calculator.Instance.cal(expression).toString();
                // } catch (Exception e) {
                // result = "calculate error:" + e.getMessage();
                // }
                // 模拟服务时间
                Thread.sleep(5000);
                out.println(result);
            }
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