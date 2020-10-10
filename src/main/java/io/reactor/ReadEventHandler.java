package io.reactor;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2020-07-31 17:02
 */
public class ReadEventHandler implements EventHandler {

    private Selector demultiplexer;
    private ByteBuffer inputBuffer = ByteBuffer.allocate(2048);

    public ReadEventHandler(Selector demultiplexer) {
        this.demultiplexer = demultiplexer;
    }

    @Override
    public void handleEvent(SelectionKey handle) throws Exception {
        System.out.println("===== Read Event Handler =====");

        SocketChannel socketChannel = (SocketChannel)handle.channel();

        // Read data from client
        socketChannel.read(inputBuffer);

        inputBuffer.flip();
        // Rewind the buffer to start reading from the beginning

        byte[] buffer = new byte[inputBuffer.limit()];
        inputBuffer.get(buffer);

        System.out.println("Received message from client : " + new String(buffer));
        inputBuffer.flip();
        // Rewind the buffer to start reading from the beginning
        // Register the interest for writable readiness event for
        // this channel in order to echo back the message

        socketChannel.register(demultiplexer, SelectionKey.OP_WRITE, inputBuffer);

    }
}
