package io.bio.likenio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2020-08-07 16:41
 */
public class Server1 {
    public static void main(String[] args) throws InterruptedException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        List<SocketChannel> socketList = new ArrayList<SocketChannel>();
        try {
            // Java为⾮阻塞设置的类
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8080));
            // 设置为⾮阻塞
            serverSocketChannel.configureBlocking(false);
            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel == null) {
                    // 表示没⼈连接
                    System.out.println("正在等待客户端请求连接...");
                    Thread.sleep(5000);
                } else {
                    System.out.println("当前接收到客户端请求连接...");
                    socketList.add(socketChannel);
                }
                for (SocketChannel socket : socketList) {
                    socket.configureBlocking(false);
                    int effective = socket.read(byteBuffer);
                    System.out.println("effiective = " + effective);
                    if (effective > 0) {
                        byteBuffer.flip();
                        // 切换模式 写-->读
                        String content = Charset.forName("UTF-8").decode(byteBuffer).toString();
                        System.out.println("接收到消息:" + content);
                        byteBuffer.clear();
                    } else {
                        System.out.println("当前未收到客户端消息");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
