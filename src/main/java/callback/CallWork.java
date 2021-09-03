package callback;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2021-08-03 09:43
 */
public class CallWork {

  public void doWork(CalledListener listener) {
    listener.onCalled();
  }

  interface CalledListener {

    void onCalled();
  }
}



