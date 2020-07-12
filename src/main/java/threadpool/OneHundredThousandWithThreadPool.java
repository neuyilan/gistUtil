
package threadpool;

/**
 * @author houliangqi
 * @since 2020/7/2 8:03 下午
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class OneHundredThousandWithThreadPool {

    private ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
    private CountDownLatch latch = new CountDownLatch(100000);

    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();

    /**
     * Common Thread Pool
     */
    ExecutorService excutor = new ThreadPoolExecutor(100, 200, 0L, TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<Runnable>(102400), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        OneHundredThousandWithThreadPool test001 = new OneHundredThousandWithThreadPool();
        long timeStart = System.currentTimeMillis();
        test001.start();
        System.out.println(String.format("total time cost = %s ms", System.currentTimeMillis() - timeStart));
    }

    public void start() {
        for (int i = 0; i < 100000; i++) {
            Runnable001 runnable001 = this.new Runnable001(i);
            excutor.submit(runnable001);
        }
        excutor.shutdown();
        try {
            // 等待latch计数为0
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(queue.size());
    }

    private class Runnable001 implements Runnable {
        private int value;

        public Runnable001(int value) {
            this.value = value;
        }

        @Override
        public void run() {
            try {
                // barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            queue.offer(value + "");
            latch.countDown();// latch计数减一
        }

    }
}
