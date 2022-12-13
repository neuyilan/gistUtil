import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.hadoop.fs.StorageType;

/**
 * @author HouliangQi(neuyilan @ 163.com)
 * @description
 * @since 2020-07-21 7:46 下午
 */
public class MainTest {

  public MainTest(String s1, String s2) {

  }

  public MainTest() {
    this("1", "2");
  }

  public static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
  public static ExecutorService pool = Executors.newFixedThreadPool(1);

  private static String[] str;
  private static StorageType[] students;

  private ConcurrentHashMap<String, String> value = new ConcurrentHashMap<>();

  public static void main(String[] args)
      throws IOException, ExecutionException, InterruptedException, ParseException {
    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    ScheduledExecutorService scheduledExecutorService2 = Executors.newSingleThreadScheduledExecutor();
    MainTest mainTest = new MainTest();
    scheduledExecutorService.scheduleAtFixedRate(() -> {
      System.out.println(mainTest.getValue());
    }, 0, 1, TimeUnit.SECONDS);

    scheduledExecutorService2.scheduleAtFixedRate(() -> {
      Random random = new Random();
      ConcurrentHashMap<String, String> vv = new ConcurrentHashMap<>();

      vv.put("key" + random.nextInt(), "value" + random.nextInt());
      mainTest.getValue().clear();
      mainTest.setValue(vv);
    }, 0, 1, TimeUnit.SECONDS);
  }

  public ConcurrentHashMap<String, String> getValue() {
    return value;
  }

  public void setValue(ConcurrentHashMap<String, String> value) {
    this.value = value;
  }

  public static void put(double v) {
    double[] values = new double[10];
    values[0] = v;
    for (int i = 0; i < values.length; i++) {
      System.out.println(values[i]);
    }
  }

  public static Map<String, String[]> getRightDataType(String fileName) {
    Map<String, String[]> rightDataTypeTimeSeries = new HashMap<>();
    String line = "";
    String cvsSplitBy = ",";
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      while ((line = br.readLine()) != null) {
        System.out.println(line);
        String[] arr = line.split(cvsSplitBy);
        String timeSeries = "root." + arr[3] + "." + arr[4] + "." + arr[5];
        rightDataTypeTimeSeries.put(timeSeries, arr);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return rightDataTypeTimeSeries;
  }

  public static void tt(String[] s, StorageType[] students) {
    MainTest.str = s;
    MainTest.students = students;
  }

  public static void doWork() throws ParseException {

  }

  public static void doWorkException() {
    String a = null;
    if (a.equals("aa")) {
      System.out.println("hahaha");
    }
  }

  static class Student {

    private Map<String, String> map;

    public Student(Map<String, String> map) {
      this.map = map;
    }

    public Map<String, String> getMap() {
      return map;
    }

    public void setMap(Map<String, String> map) {
      this.map = map;
    }

    @Override
    public String toString() {
      return "Student{"
          + " map=" + map
          + "}";
    }

  }


}


