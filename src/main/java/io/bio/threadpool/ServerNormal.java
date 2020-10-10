package io.bio.threadpool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import utils.TimeUtils;

public final class ServerNormal {
    private static int DEFAULT_PORT = 12345;
    private static ServerSocket server;

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
                socket = server.accept();
                new Thread(new ServerHandler(socket)).start();
            }
        } finally {
            if (server != null) {
                System.out.println(TimeUtils.getNowTime() + "the server closed");
                server.close();
                server = null;
            }
        }
    }
}