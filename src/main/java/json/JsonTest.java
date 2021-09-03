package json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2021-09-01 18:02
 */
public class JsonTest {

  public static void main(String[] args) {
    Map<String, LeaderInfo> map = new HashMap<>();
    map.put("aaa", new LeaderInfo("127.0.0.1", 111));
    String json = JSON.toJSONString(map);
    Map<String, LeaderInfo> newmap = JSON
        .parseObject(json, new TypeReference<Map<String, LeaderInfo>>() {
        });
    System.out.println(json);
    System.out.println(newmap);

    List<LeaderInfo> list = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      list.add(new LeaderInfo("aaa_" + i, i));
    }

    String json2 = JSON.toJSONString(list);
    System.out.println(json2);
    List<LeaderInfo> newList = JSON
        .parseObject(json2, new TypeReference<List<LeaderInfo>>() {
        });

    System.out.println(newList);

  }
}
