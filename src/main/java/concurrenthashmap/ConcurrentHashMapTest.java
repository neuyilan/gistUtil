/**
 * @Description
 * @Author qihouliang@bonc.com.cn
 * @Date 2020-06-01 7:36 下午
 */

package concurrenthashmap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 本类是用来测试两个线程同时对同一个map里面的key进行访问，
 * 
 * @author houliangqi
 * @since 2020/6/1 7:36 下午
 */
public class ConcurrentHashMapTest {
    private static int THREAD_POOL_SIZE = 5;
    private static ExecutorService pool = new ScheduledThreadPoolExecutor(THREAD_POOL_SIZE);
    private static Map<String, Boolean> map = new ConcurrentHashMap<>();
    private static int totalCount = 100;

    public static void putMap() {
        map.put("key", false);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        map.put("key", true);
        System.out.println("game over");
    }

    public static void getMap() {
        while (true) {
            if (map.get("key")) {
                System.out.println("the value is true");
                break;
            }
        }
    }

    public static void main(String[] args) {
        pool.execute(() -> putMap());
        pool.execute(() -> getMap());
    }

}
