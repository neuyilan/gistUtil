package io.reactor;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2020-07-31 17:03
 */
public class WriteEventHandler implements EventHandler {
    @Override
    public void handleEvent(SelectionKey handle) throws Exception {
        System.out.println("===== Write Event Handler =====");

        SocketChannel socketChannel = (SocketChannel)handle.channel();
        ByteBuffer inputBuffer = ByteBuffer.wrap("Hello Client!\n".getBytes());
        // ByteBuffer inputBuffer = (ByteBuffer)handle.attachment();
        socketChannel.write(inputBuffer);
        socketChannel.close();
    }
}
