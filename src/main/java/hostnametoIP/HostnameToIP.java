
package hostnametoIP;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description
 * @Author qihouliang@bonc.com.cn
 * @Date 2020-05-16 11:49 上午
 */
public class HostnameToIP {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("please enter the hostname!");
            return;
        }
        String hostname = args[0];
        String ip = null;
        try {
            ip = hostToIP(hostname);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("the hostname=" + hostname + ", ip=" + ip);
    }

    public static String hostToIP(String varHostname) throws UnknownHostException {
        System.out.println(varHostname);
        InetAddress address = null;

        address = InetAddress.getByName(varHostname);

        return (address.getHostAddress());
    }
}
