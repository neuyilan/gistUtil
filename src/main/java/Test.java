import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import utils.RamUsageEstimator;

/**
 * @author HouliangQi(neuyilan @ 163.com)
 * @description
 * @since 2020-07-21 7:46 下午
 */
public class Test {

  public static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

  public static void main(String[] args)
      throws IOException, ExecutionException, InterruptedException {
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

//    service.submit(new Runnable() {
//      @Override
//      public void run() {
//        getWriteLock();
//        System.out.println("Thread=" + Thread.currentThread() +" get write lock");
//        try {
//          Thread.sleep(10_000);
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
////        writeUnLock();
//      }
//    });

    while (true) {

    }

//    for (int i = 0; i < 10; i++) {
//      if (i % 2 == 1) {
//        service.submit(new Runnable() {
//          @Override
//          public void run() {
//            lock.readLock().lock();
//          }
//        });
//      }
//    }
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

  /**
   * 测试深浅拷贝
   */
  private static void hashMapTest1() {
    Map<String, String> map = new HashMap<>();
    map.put("k1", "v1");
    map.put("k2", "v2");
    map.put("k3", "v3");

    Map<String, String> map1 = map;
    map1.put("k4", "v4");
    System.out.println(map);
    System.out.println(map1);
  }

  /**
   * 测试深浅拷贝
   */
  private static void hashMapTest() {
    Data data = new Data();
    Map<String, String> map = new HashMap<>();
    map.put("k1", "v1");
    map.put("k2", "v2");
    map.put("k3", "v3");
    data.setMapV(map);

    Map<String, String> newMap = data.getMapV();
    newMap.put("k4", "v4");
    newMap.remove("k2");
    System.out.println(newMap);
    System.out.println(data.getMapV());
  }


  public static void testList() {
    List<String> list = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      list.add("ss_" + i);
    }

    Iterator<String> it = list.iterator();
    while (it.hasNext()) {
      String tmp = it.next();
      if (tmp.equals("ss_1") || tmp.equals("ss_5")) {
        it.remove();
      }
    }
    System.out.println(list);

  }

  public static boolean lessEqualThan(String str1, String str2) {
    if (str1.length() != str2.length()) {
      return str1.length() < str2.length();
    }
    return (str1.compareTo(str2) <= 0);
  }

  private static boolean startCheck() throws UnknownHostException, SocketException {
    boolean checkOk = false;
//    String addr = InetAddress.getLocalHost().getHostAddress();
//    System.out.println(addr);
//    InetAddress ip;
//    String hostname;
//    ip = InetAddress.getLocalHost();
//    hostname = ip.getHostName();
//    System.out.println("Your current IP address : " + ip);
//    System.out.println("Your current Hostname : " + hostname);

    Enumeration e = NetworkInterface.getNetworkInterfaces();
    while (e.hasMoreElements()) {
      NetworkInterface n = (NetworkInterface) e.nextElement();
      Enumeration ee = n.getInetAddresses();
      while (ee.hasMoreElements()) {
        InetAddress i = (InetAddress) ee.nextElement();
        System.out.println(i.getHostAddress());
      }
    }

    return checkOk;
  }

  public static void testNull() {
    long total = 0;
    for (int i = 0; i < 100; i++) {
      // 24000
//      Data d = new Data("name_" + i, "sex_" + i);

      Data d = new Data("name_" + i, null);

      // 17600
//      Data d = new Data("name_" + i);
      long size = RamUsageEstimator.sizeOf(d);
      total += size;
    }
    System.out.println(total);
  }

  static class Data {

    Map<String, String> mapV = new HashMap<>();
    Map<String, Data> result = new HashMap<>();
    String name;
    String sex;

    public Data() {

    }

    public Data(String name) {
      this.name = name;
    }

    public Data(String name, String sex) {
      this.name = name;
      this.sex = sex;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

//    public String getSex() {
//      return sex;
//    }
//
//    public void setSex(String sex) {
//      this.sex = sex;
//    }

    public Map<String, Data> getResult() {
      return result;
    }

    public void setResult(Map<String, Data> result) {
      this.result = result;
    }

    public Map<String, String> getMapV() {
      return mapV;
    }

    public void setMapV(Map<String, String> mapV) {
      this.mapV = mapV;
    }

    @Override
    public String toString() {
      return "Data{"
          + " result=" + result
          + ", name='" + name + '\''
//          + ", sex='" + sex + '\''
          + "}";
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Data data = (Data) o;

      return name != null ? name.equals(data.name) : data.name == null;
    }

    @Override
    public int hashCode() {
      return name != null ? name.hashCode() : 0;
    }
  }
}
