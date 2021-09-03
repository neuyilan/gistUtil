package ReentrantReadWriteLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2021-09-03 15:28
 */
public class ReentrantReadWriteLockTest {

  public static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

  public static void main(String[] args) {
    ExecutorService service = Executors.newFixedThreadPool(10);
    for (int i = 0; i < 10; i++) {
      if (i % 2 == 0) {
        service.submit(new Runnable() {
          @Override
          public void run() {
            getReadLock();
            System.out.println("Thread=" + Thread.currentThread() + " get read lock");
//            try {
//              Thread.sleep(10_000);
//            } catch (InterruptedException e) {
//              e.printStackTrace();
//            }
//            readUnLock();
          }
        });
      }
    }

    service.submit(new Runnable() {
      @Override
      public void run() {
        getWriteLock();
        System.out.println("Thread=" + Thread.currentThread() + " get write lock");
        try {
          Thread.sleep(10_000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
//        writeUnLock();
      }
    });

    for (int i = 0; i < 10; i++) {
      if (i % 2 == 1) {
        service.submit(new Runnable() {
          @Override
          public void run() {
            lock.readLock().lock();
          }
        });
      }
    }
  }

  public static void getReadLock() {
    lock.readLock().lock();
  }

  public static void readUnLock() {
    lock.readLock().unlock();
  }

  public static void getWriteLock() {
    lock.writeLock().lock();
  }

  public static void writeUnLock() {
    lock.writeLock().unlock();
  }

}
