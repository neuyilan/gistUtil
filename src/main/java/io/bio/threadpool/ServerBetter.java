package io.bio.threadpool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import utils.TimeUtils;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2020-07-30 12:10
 */
public class ServerBetter {
    private static int DEFAULT_PORT = 12345;
    private static ServerSocket server;
    private static ExecutorService executorService = Executors.newFixedThreadPool(100);

    public static void start() throws IOException {
        start(DEFAULT_PORT);
    }

    public synchronized static void start(int port) throws IOException {
        if (server != null) {
            return;
        }
        try {
            server = new ServerSocket(port);
            System.out.println(TimeUtils.getNowTime() + "the server has started, port=" + port);
            Socket socket;
            while (true) {
                // 会被阻塞
                socket = server.accept();
                System.out.println(TimeUtils.getNowTime() + "accept an socket =" + socket);
                executorService.execute(new ServerHandler(socket));
            }
        } finally {
            if (server != null) {
                System.out.println(TimeUtils.getNowTime() + "the server has stopped");
                server.close();
                server = null;
            }
        }
    }
}
