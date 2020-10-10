package io.bio.block;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2020-08-07 15:59
 */
public class Consumer {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8080);
            String message = null;
            Scanner sc = new Scanner(System.in);
            message = sc.next();
            socket.getOutputStream().write(message.getBytes());
            socket.close();
            sc.close();
        } catch (IOException e) { // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
