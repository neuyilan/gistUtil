package io.bio.likenio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2020-08-07 16:28
 */
public class Server {
    public static void main(String[] args) throws InterruptedException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
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
                }
                if (socketChannel != null) {
                    socketChannel.configureBlocking(true);
                    // 设置为⾮阻塞
                    byteBuffer.flip();
                    // 切换模式 写-->读
                    int effective = socketChannel.read(byteBuffer);
                    if (effective != 0) {
                        String content = Charset.forName("utf-8").decode(byteBuffer).toString();
                        System.out.println(content);
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

// 不难看出，在这种解决方案下，虽然在接收客户端消息时不会阻塞，但是又开始重新接收服务器请求，用户根本来不及输入消息，
// 服务器就转向接收别的客户端请求了，换言之，服务器弄丢了当前客户端的请求