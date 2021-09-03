package remoteFileLoad;

/**
 * This program will demonstrate the file transfer from remote to local $ CLASSPATH=.:../build javac
 * ScpFrom.java $ CLASSPATH=.:../build java ScpFrom user@remotehost:file1 file2 You will be asked
 * passwd. If everything works fine, a file 'file1' on 'remotehost' will copied to local 'file1'.
 */

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RemoteFileLoad2 {

  public static void main(String[] args) {
    doTask();
    System.out.println("hahahahhha");
  }

  public static void doTask() {
    FileOutputStream fos = null;
    try {

      String user = "root";
      String host = "172.16.48.4";
      String rfile = "/data8/qhl/data-migration/res.out";
      String lfile = "qhl.out";
      String password = "2021fight";

      String prefix = null;
      if (new File(lfile).isDirectory()) {
        prefix = lfile + File.separator;
      }

      JSch jsch = new JSch();
      Session session = jsch.getSession(user, host, 22);

//      // username and password will be given via UserInfo interface.
//      UserInfo ui = new MyUserInfo();
//      session.setUserInfo(ui);
      session.setPassword(password);
      java.util.Properties config = new java.util.Properties();
      config.put("StrictHostKeyChecking", "no");
      session.setConfig(config);
      session.connect();

      // exec 'scp -f rfile' remotely
      rfile = rfile.replace("'", "'\"'\"'");
      rfile = "'" + rfile + "'";
//      String command = "scp -f " + rfile;
      String command = "scp -f " + rfile;
      Channel channel = session.openChannel("exec");
      ((ChannelExec) channel).setCommand(command);

      // get I/O streams for remote scp
      OutputStream out = channel.getOutputStream();
      InputStream in = channel.getInputStream();

      channel.connect();

      System.out.println(System.currentTimeMillis());
      byte[] buf = new byte[1024];

      // send '\0'
      buf[0] = 0;
      out.write(buf, 0, 1);
      out.flush();

      while (true) {
        int c = checkAck(in);
        System.out.println("qhl=" + c);
        if (c != 'C') {
          break;
        }

        // read '0644 '
        in.read(buf, 0, 5);

        long filesize = 0L;
        while (true) {
          if (in.read(buf, 0, 1) < 0) {
            // error
            break;
          }
          if (buf[0] == ' ') {
            break;
          }
          filesize = filesize * 10L + (long) (buf[0] - '0');
        }

        String file = null;
        for (int i = 0; ; i++) {
          in.read(buf, i, 1);
          if (buf[i] == (byte) 0x0a) {
            file = new String(buf, 0, i);
            break;
          }
        }

        System.out.println("filesize=" + filesize + ", file=" + file);

        // send '\0'
        buf[0] = 0;
        out.write(buf, 0, 1);
        out.flush();

        // read a content of lfile
        fos = new FileOutputStream(prefix == null ? lfile : prefix + file);
        int foo;
        while (true) {
          if (buf.length < filesize) {
            foo = buf.length;
          } else {
            foo = (int) filesize;
          }
//          System.out.println("filesize=" + filesize + ", file=" + file + ",foo=" + foo);
          foo = in.read(buf, 0, foo);
          if (foo < 0) {
            // error
            break;
          }
//          System.out.println("once write=" + foo);
          fos.write(buf, 0, foo);
          filesize -= foo;
          if (filesize == 0L) {
            break;
          }
        }
        fos.close();
        fos = null;

//        if (checkAck(in) != 0) {
//          System.exit(0);
//        }

        // send '\0'
        buf[0] = 0;
        out.write(buf, 0, 1);
        out.flush();
      }

      session.disconnect();

    } catch (Exception e) {
      System.out.println(e);
      try {
        if (fos != null) {
          fos.close();
        }
      } catch (Exception ee) {
      }
    }

    System.out.println(System.currentTimeMillis());
  }

  static int checkAck(InputStream in) throws IOException {
    int b = in.read();
    // b may be 0 for success,
    //          1 for error,
    //          2 for fatal error,
    //          -1
    if (b == 0) {
      return b;
    }
    if (b == -1) {
      return b;
    }

    if (b == 1 || b == 2) {
      StringBuffer sb = new StringBuffer();
      int c;
      do {
        c = in.read();
        sb.append((char) c);
      }
      while (c != '\n');
      if (b == 1) { // error
        System.out.print(sb.toString());
      }
      if (b == 2) { // fatal error
        System.out.print(sb.toString());
      }
    }
    return b;
  }
}