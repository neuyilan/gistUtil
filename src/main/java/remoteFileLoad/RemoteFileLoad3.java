package remoteFileLoad;

import com.jcraft.jsch.JSchException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import remoteFileLoad.ssh.Jscp;
import remoteFileLoad.ssh.SecureContext;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2021-03-24 14:46
 */
public class RemoteFileLoad3 {


  public static void main(String[] args) throws IOException, JSchException {
// create secure context
    SecureContext context = new SecureContext("root", "172.16.48.4");

// set optional security configurations.
    context.setTrustAllHosts(true);
    context.setPrivateKeyFile(new File("private/key"));

// Console requires JDK 1.7
// System.out.println("enter password:");
// context.setPassword(System.console().readPassword());

    String src = "/data8/qhl/data-migration/1616553751003-2-0.tsfile";
    String dest = "qhl.aaa";
    Jscp.exec(context, src, dest,
        // regex ignore list
        Arrays.asList("logs/log[0-9]*.txt",
            "backups"));
  }
}
