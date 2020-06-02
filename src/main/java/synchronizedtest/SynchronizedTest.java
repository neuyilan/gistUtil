
package synchronizedtest;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 本类主要演示了synchronized中notify的使用
 * 
 * @author houliangqi
 * @since 2020/6/2 4:33 下午
 */
public class SynchronizedTest {
    private static final Deque<String> availableMemTables = new ArrayDeque<>();
    private static final int WAIT_TIME = 1000;
    private static int THREAD_POOL_SIZE = 5;
    private static ExecutorService pool = new ScheduledThreadPoolExecutor(THREAD_POOL_SIZE);

    public static String getAvailableString() {
        synchronized (availableMemTables) {
            // wait until some one has released a memtable
            int waitCount = 1;
            while (true) {
                if (!availableMemTables.isEmpty()) {
                    System.out.println("available memory table is not empty, return one");
                    return availableMemTables.pop();
                }
                try {
                    availableMemTables.wait(WAIT_TIME);
                } catch (InterruptedException e) {
                    System.out.println("fails to wait fot memtables continue to wait" + e);
                    Thread.currentThread().interrupt();
                }
                System.out.println("has waited for a memtable for " + waitCount++ * WAIT_TIME + "ms");
            }
        }
    }

    public static void putBack(String memTable) {
        synchronized (availableMemTables) {
            availableMemTables.push(memTable);
            // 这行代码是重点，如果没有这行代码，则其他的线程就不会得到通知
            availableMemTables.notify();
            System.out.println("return a memtable, stack size=" + availableMemTables.size());
        }
    }

    public static void putTest() {
        int count = 10;
        for (int i = 0; i < count; i++) {
            putBack("memtable" + i);
        }
    }

    public static void main(String[] args) {
        pool.submit(() -> getAvailableString());
        pool.submit(() -> putTest());
    }
}
