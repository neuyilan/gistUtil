package io.reactor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2020-07-31 17:01
 */
public class Client {
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();

    /**
     * Common Thread Pool
     */
    private static ExecutorService excutor = new ThreadPoolExecutor(100, 200, 0L, TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<Runnable>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws Exception {
        // 1. 模拟第一处卡住的问题 (解决BIO中第一处accept卡住的问题)
         test1();

        // 2. 模拟第儿处卡住的问题 (解决BIO中第二处读数据卡住的问题)
//        test2();

        /**
         * 3. 模拟单Reactor单线程卡住的问题
         * 
         * test1();
         *
         * 在 {@link ReadEventHandler#handleEvent(SelectionKey)}代码中添加如下代码} 进行模拟server处理较慢问题 Thread.sleep(5000);
         */

    }

    /**
     * 模拟第一处卡住的问题 (解决BIO中第一处accept卡住的问题)
     */
    public static void test1() throws Exception {
        for (int i = 0; i < 10; i++) {
            excutor.submit(() -> {
                try {
                    String sentence;
                    String modifiedSentence;
                    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
                    Socket clientSocket = new Socket("localhost", 7070);

                    // 1. 模拟第一处卡住的问题 (解决BIO中第一处卡住的问题)
                    Thread.sleep(5000);

                    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

                    // BufferedReader inFromServer = new BufferedReader(new
                    // InputStreamReader(clientSocket.getInputStream()));
                    // sentence = inFromUser.readLine();
                    Thread.sleep(5000);
                    outToServer.writeBytes("Hello ..." + '\n');
                    InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
                    BufferedReader inFromServer = new BufferedReader(inputStreamReader);
                    sentence = inFromServer.readLine();
                    System.out.println("Response from Server : " + sentence);
                    clientSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * 模拟第儿处卡住的问题 (解决BIO中第二处读数据卡住的问题)
     */
    public static void test2() throws Exception {
        {
            excutor.submit(() -> {
                try {
                    String sentence;
                    String modifiedSentence;
                    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
                    Socket clientSocket = new Socket("localhost", 7070);
                    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

                    // 模拟第二处卡住的问题
                    Thread.sleep(30000);
                    outToServer.writeBytes("Hello ..." + '\n');
                    InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
                    BufferedReader inFromServer = new BufferedReader(inputStreamReader);
                    sentence = inFromServer.readLine();
                    System.out.println("Response from Server : " + sentence);
                    clientSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        // 保证上面的代码先执行
        Thread.sleep(1000);

        for (int i = 0; i < 5; i++) {
            excutor.submit(() -> {
                try {
                    String sentence;
                    String modifiedSentence;
                    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
                    Socket clientSocket = new Socket("localhost", 7070);

                    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

                    // BufferedReader inFromServer = new BufferedReader(new
                    // InputStreamReader(clientSocket.getInputStream()));
                    // sentence = inFromUser.readLine();
                    outToServer.writeBytes("Hello ..." + '\n');
                    InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
                    BufferedReader inFromServer = new BufferedReader(inputStreamReader);
                    sentence = inFromServer.readLine();
                    System.out.println("Response from Server : " + sentence);
                    clientSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void sendMsg() throws Exception {
        String sentence;
        String modifiedSentence;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket("localhost", 7070);

        // 1. 模拟第一处卡住的问题 (解决BIO中第一处卡住的问题)
        Thread.sleep(5000);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        // BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        // sentence = inFromUser.readLine();
        Thread.sleep(5000);
        outToServer.writeBytes("Hello ..." + '\n');
        // InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
        // BufferedReader inFromServer = new BufferedReader(inputStreamReader);
        // sentence = inFromServer.readLine();
        // System.out.println("Response from Server : " + sentence);
        // clientSocket.close();
    }
}
