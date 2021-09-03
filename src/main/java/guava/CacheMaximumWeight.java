package guava;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2021-08-19 15:08
 */

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.cache.Weigher;
import java.util.Random;

public class CacheMaximumWeight {

  private static Random random = new Random();
  private static int total = 0;

  // 创建一个监听器
  private static class MyRemovalListener implements RemovalListener<Integer, Integer> {

    @Override
    public void onRemoval(RemovalNotification<Integer, Integer> notification) {
      String tips = String
          .format("key=%s,value=%s,reason=%s was removed, total=%s", notification.getKey(),
              notification.getValue(),
              notification.getCause(), total);
      System.out.println(tips);
    }
  }

  public static void main(String[] args) {

    // 创建一个带有RemovalListener监听的缓存
    Cache<Integer, Integer> cache = CacheBuilder.newBuilder().maximumWeight(100)
        .weigher(new Weigher<Integer, Integer>() {
          @Override
          public int weigh(Integer integer, Integer integer2) {
            int weight = random.nextInt(10) + 5;
            total += weight;
            System.out.println("weight = " + weight);
            return weight;
          }
        }).concurrencyLevel(30).removalListener(new MyRemovalListener()).recordStats().build();

    for (int i = 0; i < 10; i++) {
      cache.put(i, i);
      System.out.println(cache.stats() + ", total=" + total);
    }

//    cache.put(2, 2);
//    cache.put(3, 2);
//    cache.put(4, 2);
//    cache.put(5, 2);

    // 手动清除
//    cache.invalidateAll();

//    System.out.println(cache.getIfPresent(1));
  }
}
