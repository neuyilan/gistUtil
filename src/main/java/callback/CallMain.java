package callback;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2021-08-03 09:45
 */
public class CallMain {

  public void doSomething() {
    System.out.println("do something in CallMain");
  }

  public void mainWork() {
    CallWork callWork = new CallWork();
    callWork.doWork(this::doSomething);
  }

  public static void main(String[] args) {
    CallMain callMain = new CallMain();
    callMain.mainWork();
  }
}
