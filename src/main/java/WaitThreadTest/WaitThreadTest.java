package WaitThreadTest;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @Description
 * @Author qihouliang
 * @Date 2020-06-28 10:23 上午
 */
public class WaitThreadTest {
    public static Map<String, Deque<String>> clientCaches = new ConcurrentHashMap<>();

    static long WAIT_CLIENT_TIMEOUT_MS = 10 * 1000;

    public static int THREAD_POOL_SIZE = 5;
    public static ExecutorService pool = new ScheduledThreadPoolExecutor(THREAD_POOL_SIZE);

    public String getTest(String key) {
        Deque<String> clientStack = clientCaches.computeIfAbsent(key, n -> new ArrayDeque<>());
        // actually not the real address
        System.out.println("getTest, clientStack address=" + clientStack.hashCode());
        System.out.println(clientStack.size());
        for (String str : clientStack) {
            System.out.println(str);
        }
        long waitStart = System.currentTimeMillis();
        System.out.println(waitStart);
        synchronized (this) {
            if (clientStack.isEmpty()) {
                while (clientStack.isEmpty()) {
                    try {
                        // 重点：当别的线程调用notify()或是notifyAll()方法的时候，就会通知这个地方
                        this.wait(WAIT_CLIENT_TIMEOUT_MS);
                        if (System.currentTimeMillis() - waitStart >= WAIT_CLIENT_TIMEOUT_MS) {
                            System.out.println(String.format(
                                "Cannot get an available value after %s ms, create a new one", WAIT_CLIENT_TIMEOUT_MS));
                            return null;
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Interrupted when waiting for an available value");
                    }
                }
                String result = clientStack.pop();
                System.out.println(
                    String.format("result=%s, time cost=%d ms", result, (System.currentTimeMillis() - waitStart)));
                return result;
            }
        }
        return null;
    }

    public void fillTest(String key) {
        Deque<String> clientStack = clientCaches.computeIfAbsent(key, n -> new ArrayDeque<>());
        // actually not the real address
        System.out.println("fillTest, clientStack address=" + clientStack.hashCode());
        synchronized (this) {
            clientStack.push("string1");
            // 重点：通知wait那个地方，可以释放了
            this.notifyAll();
        }
    }

    public static void main(String[] args) {
        WaitThreadTest test = new WaitThreadTest();
        String key = "key";
        pool.submit(() -> test.getTest(key));
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.submit(() -> test.fillTest(key));
    }
}
