package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2020-08-06 10:55
 */
public class TimeUtils {

    public static String getNowTime() {
        // 设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ");
        // new Date()为获取当前系统时间
        return df.format(new Date());
    }

    public static void main(String args[]) {
        String nowTime = TimeUtils.getNowTime();
        System.out.println(nowTime + "yes!");
    }

}
