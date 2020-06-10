/**
 * @Description
 * @Author qihouliang@bonc.com.cn
 * @Date 2020-06-09 5:48 下午
 */

package scheduleexecutor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

/**
 * @author houliangqi
 * @since 2020/6/9 5:48 下午
 */
public class ScheduleExecutorTest {
    private static ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
        new BasicThreadFactory.Builder().namingPattern("raft-log-delete-%d").daemon(true).build());

    private static void scheduleMethod() {
        System.out.println("oh, just print hello world");
    }

    public static void main(String[] args) {
        executorService.scheduleAtFixedRate(ScheduleExecutorTest::scheduleMethod, 0, 1, TimeUnit.SECONDS);
        while (true) {
            // do nothing, just not allow the process exit
        }
    }
}
