package guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockHang {

  private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
  private static int cacheMaxSize = 10;

  /**
   * 创建一个带有RemovalListener监听的缓存
   */
  private static Cache<Integer, Integer> cache;

  public static void main(String[] args) {
    cacheInit();
    putCache();
    readWork();
    System.exit(0);
  }

  private static void cacheInit() {
    cache = CacheBuilder.newBuilder()
        .removalListener(new MyRemovalListener()).maximumSize(cacheMaxSize).build();
  }

  private static void putCache() {
    for (int i = 0; i < cacheMaxSize; i++) {
      cache.put(i, i);
    }
  }

  private static void readWork() {
    // get read lock
    lock.readLock().lock();
    System.out.println("Thread={}" + Thread.currentThread() + " get read lock");
    try {
      // do something

      cache.invalidate(1);
    } finally {
      lock.readLock().unlock();
      System.out.println("Thread={}" + Thread.currentThread() + " unlock the read lock");
    }
  }


  /**
   * 创建一个监听器，当cache被remove的时候，需要加写锁做是一些事情。
   */
  private static class MyRemovalListener implements RemovalListener<Integer, Integer> {
    @Override
    public void onRemoval(RemovalNotification<Integer, Integer> notification) {
      System.out.println("Thread={}" + Thread.currentThread() + " try to get write lock");
      lock.writeLock().lock();
      System.out.println("Thread={}" + Thread.currentThread() + " get write lock");
      try {
        String tips = String
            .format("key=%s,value=%s,reason=%s was removed", notification.getKey(),
                notification.getValue(), notification.getCause());
        System.out.println(tips);
      } finally {
        lock.writeLock().unlock();
        System.out.println("Thread={}" + Thread.currentThread() + " unlock the write lock");
      }

    }
  }

}

