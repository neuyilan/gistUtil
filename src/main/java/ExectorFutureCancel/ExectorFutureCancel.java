package ExectorFutureCancel;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2020-11-30 11:02
 */
public class ExectorFutureCancel {
    private ExecutorService service;
    private Future<?> future;
    private Random random = new Random();

    public ExectorFutureCancel() {
        this.service = Executors.newFixedThreadPool(1);
    }

    public void noReturnValueWithTimeOut() {
        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public void noReturnValue() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public void doWork() {
        int tmp = random.nextInt(2);
        System.out.println(tmp);
        if (tmp % 2 == 0) {
            future = service.submit(this::noReturnValue);
        } else {
            future = service.submit(this::noReturnValueWithTimeOut);
        }
        future = service.submit(() -> {
            System.out.println("1111");
        });
        try {
            Void value = (Void)future.get(5 * 1000, TimeUnit.MILLISECONDS);

            if (value == null) {
                System.out.println("the result is null");
            } else {
                System.out.println("the result = " + value.toString());
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            boolean success = future.cancel(true);
            System.out.println("cancel task = " + success);
            System.out.println(future + "," + e);
        }
    }

    public static void main(String[] args) {
        ExectorFutureCancel exectorFutureCancel = new ExectorFutureCancel();
        exectorFutureCancel.doWork();
    }
}
