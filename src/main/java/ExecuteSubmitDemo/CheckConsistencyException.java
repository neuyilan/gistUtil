
package ExecuteSubmitDemo;

/**
 * @author houliangqi
 * @since 2020/6/16 10:22 上午
 */
public class CheckConsistencyException extends Exception {

    public CheckConsistencyException(String errMag) {
        super(String.format("check consistency failed, error message=%s ", errMag));
    }
}
