package ExectorFutureCancel;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2020-11-30 11:49
 */
public class TimeoutUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeoutUtil.class);

    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static <T> T process(Callable<T> task, long timeout) {
        if (task == null) {
            return null;
        }
        Future<T> futureRet = executor.submit(task);
        try {
            T ret = futureRet.get(timeout, TimeUnit.SECONDS);
            return ret;
        } catch (InterruptedException e) {
            LOGGER.error("Interrupt Exception", e);
        } catch (ExecutionException e) {
            LOGGER.error("Task execute exception", e);
        } catch (TimeoutException e) {
            LOGGER.warn("Process Timeout");
            if (futureRet != null && futureRet.isCancelled()) {
                futureRet.cancel(true);
            }
        }
        return null;
    }
}