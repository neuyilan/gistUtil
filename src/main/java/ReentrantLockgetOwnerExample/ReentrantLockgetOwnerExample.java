package ReentrantLockgetOwnerExample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author HouliangQi(neuyilan @ 163.com)
 * @description
 * @since 2020-07-23 9:39 上午
 */
public class ReentrantLockgetOwnerExample {
    private static ExecutorService commitLogPool =
        new ThreadPoolExecutor(10, 100, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    ReentrantLockClass lock1 = new ReentrantLockClass();
    private int count = 0;

    public static void main(String args[]) throws InterruptedException {
        ReentrantLockgetOwnerExample example = new ReentrantLockgetOwnerExample();
        for (int i = 0; i < 100; i++) {
            commitLogPool.submit(() -> example.doTask());
        }
        commitLogPool.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println(example.count);
    }

    public void doTask() {
        System.out.println(Thread.currentThread().getName() + "," + lock1.owner());
        lock1.lock();
        for (int i = 0; i < 10; i++) {
            count++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock1.unlock();
    }
}

class ReentrantLockClass extends ReentrantLock {
    String owner() {
        Thread t = this.getOwner();
        if (t != null) {
            return t.getName();
        } else {
            return "none";
        }
    }
}