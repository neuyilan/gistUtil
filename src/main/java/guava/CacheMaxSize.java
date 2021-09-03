package guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2021-08-23 11:31
 */
public class CacheMaxSize {

  // 创建一个监听器
  private static class MyRemovalListener implements RemovalListener<Integer, Integer> {

    @Override
    public void onRemoval(RemovalNotification<Integer, Integer> notification) {
      String tips = String
          .format("key=%s,value=%s,reason=%s was removed", notification.getKey(),
              notification.getValue(),
              notification.getCause());
      System.out.println(tips);
    }
  }

  public static void main(String[] args) {

    // 创建一个带有RemovalListener监听的缓存
    Cache<Integer, Integer> cache = CacheBuilder.newBuilder().maximumSize(100)
        .removalListener(new CacheMaxSize.MyRemovalListener()).recordStats().build();

    for (int i = 0; i < 100; i++) {
      cache.put(i, i);
      System.out.println(cache.stats());
    }
  }
}
