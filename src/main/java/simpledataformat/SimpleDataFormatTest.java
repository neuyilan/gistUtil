package simpledataformat;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 本类是用来测试SimpleDateFormat的坑的。
 * 
 * @author houliangqi
 */
public class SimpleDataFormatTest {

    public static void main(String[] args) {
        timeTest();
    }

    public static void timeTest() {
        String str1 = "2020-05-23T15:14:02.890+08:00";
        String str2 = "2020-05-23T15:14:02.89+08:00";

        String wrongStr1 = simpleDataFormatTimestamp(str1);
        String wrongStr2 = simpleDataFormatTimestamp(str2);
        System.out.println("wrongStr1.equals(wrongStr2)=" + wrongStr1.equals(wrongStr2) + ", wrongStr1=" + wrongStr1
            + ", wrongStr2=" + wrongStr2);

        String ss1 = jodaFormatTimestamp(str1);
        String ss2 = jodaFormatTimestamp(str2);
        System.out.println("ss1.equals(ss1)=" + ss1.equals(ss2) + ", ss1=" + ss1 + ", ss2=" + ss2);

    }

    public static String simpleDataFormatTimestamp(String stime) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+08:00'");
        try {
            Date date = simpleDateFormat.parse(stime);
            return String.valueOf(date.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String jodaFormatTimestamp(String stime) {
        final DateTimeFormatter fmt1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'+08:00'");
        try {
            DateTime dt = fmt1.parseDateTime(stime);
            return String.valueOf(dt.toDate().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}