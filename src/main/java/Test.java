/**
 * @Description
 * @Author qihouliang@bonc.com.cn
 * @Date 2020-06-04 3:59 下午
 */

/**
 * @author houliangqi
 * @since 2020/6/4 3:59 下午
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(0 % 1024);
        System.out.println(1 % 1024);
        System.out.println(2 % 1024);
        System.out.println(1024 / Math.log(1024));
    }

    public static void enumSize() {
        System.out.println(EnumTest.CHUNK_GROUP_FOOTER.name().length());
    }

    enum EnumTest {
        CHUNK_GROUP_FOOTER, CHUNK_HEADER, SEPARATOR, VERSION
    }

}
