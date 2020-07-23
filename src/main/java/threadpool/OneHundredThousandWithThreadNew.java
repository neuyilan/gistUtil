package threadpool;

/**
 * @author houliangqi
 * @since 2020/7/2 8:13 下午
 */
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

public class OneHundredThousandWithThreadNew {
    private ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
    // private ArrayList<String> queue = new ArrayList<String>();
    // private CyclicBarrier barrier = new CyclicBarrier(10000000);
    private CountDownLatch latch = new CountDownLatch(100000);

    public static void main(String[] args) {
        OneHundredThousandWithThreadNew test001 = new OneHundredThousandWithThreadNew();
        long timeStart = System.currentTimeMillis();
        test001.start();
        System.out.println(String.format("total time cost = %s ms", System.currentTimeMillis() - timeStart));
    }

    public void start() {
        for (int i = 0; i < 100000; i++) {
            OneHundredThousandWithThreadNew.Runnable001 runnable001 = this.new Runnable001(i);
            // excutor.submit(runnable001);
            new Thread(runnable001).start();
        }
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
