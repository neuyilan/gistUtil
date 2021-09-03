package guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2021-08-02 14:13
 */
public class CacheTest {

  // 创建一个监听器
  private static class MyRemovalListener implements RemovalListener<Integer, Integer> {

    @Override
    public void onRemoval(RemovalNotification<Integer, Integer> notification) {
      String tips = String
          .format("key=%s,value=%s,reason=%s", notification.getKey(), notification.getValue(),
              notification.getCause());
      System.out.println(tips);
    }
  }

  public static void main(String[] args) {

    // 创建一个带有RemovalListener监听的缓存
    Cache<Integer, Integer> cache = CacheBuilder.newBuilder()
        .removalListener(new MyRemovalListener()).maximumSize(3).build();

    cache.put(1, 1);
    cache.put(2, 2);
    cache.put(3, 2);
    cache.put(4, 2);
    cache.put(5, 2);

    // 手动清除
    cache.invalidateAll();

    System.out.println(cache.getIfPresent(1));
  }
}
