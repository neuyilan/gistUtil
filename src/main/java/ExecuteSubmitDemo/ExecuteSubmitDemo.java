package ExecuteSubmitDemo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

/**
 * @author houliangqi
 * @since 2020/6/16 9:52 上午
 */
public class ExecuteSubmitDemo {
    public void method() throws Exception {
        System.out.println("creating service");
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(2,
            new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());

        List<Future> futureList = new ArrayList<>();
        int threadNum = 5;
        for (int i = 0; i < threadNum; i++) {
            futureList.add(service.submit(() -> {
                try {
                    throwException();
                } catch (CheckConsistencyException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }));
        }

        for (Future future : futureList) {
            try {
                future.get();
            } catch (RuntimeException | InterruptedException | ExecutionException e) {
                throw new Exception(e.getMessage());
            }

        }
        service.shutdown();
    }

    public void throwException() throws CheckConsistencyException {
        throw new CheckConsistencyException("oh fuck!");
    }

    public static void main(String args[]) {
        ExecuteSubmitDemo demo = new ExecuteSubmitDemo();
        try {
            demo.method();
        } catch (Exception e) {
            System.out.println("the exception= " + e.getMessage());
        }

    }
}
