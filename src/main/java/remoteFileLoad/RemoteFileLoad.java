package remoteFileLoad;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.xfer.FileSystemFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.UserAuthenticator;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.auth.StaticUserAuthenticator;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2021-03-08 16:52
 */
public class RemoteFileLoad {

  public void readRemoteFile() throws IOException {
    File f = new File("abc.txt"); //Takes the default path, else, you can specify the required path
    if (f.exists()) {
      f.delete();
    }
    f.createNewFile();
    FileObject destn = VFS.getManager().resolveFile(f.getAbsolutePath());
    UserAuthenticator auth = new StaticUserAuthenticator("172.16.48.4", "root", "2021fight");
    FileSystemOptions opts = new FileSystemOptions();

    StandardFileSystemManager fsManager = (StandardFileSystemManager)
        VFS.getManager(); // YUCK! smb
    fsManager.setBaseFile(new File("smb://172.16.48.4")); // Another YUCK.
    FileObject fo = fsManager
        .resolveFile("/data8/qhl/cluster/kill_iotdb.sh", opts);
//    DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts, auth);
//    FileObject fo = VFS.getManager()
//        .resolveFile("ftp://172.16.48.4/data8/qhl/cluster/kill_iotdb.sh", opts);
//    FileObject fo = VFS.getManager()
//        .resolveFile("\\\\172.16.48.4\\data8\\qhl\\cluster\\kill_iotdb.sh", opts);
    destn.copyFrom(fo, Selectors.SELECT_SELF);
    destn.close();
  }


  public void read2() throws IOException {
    SmbFile smbFile = new SmbFile(
        "smb://root:2021fight@172.16.48.4/data8/qhl/cluster/kill_iotdb.sh");
    int length = smbFile.getContentLength();//得到文件的大小
    byte buffer[] = new byte[length];
    SmbFileInputStream in = new SmbFileInputStream(smbFile); //建立smb文件输入流
    while ((in.read(buffer)) != -1) {
      System.out.write(buffer);
      System.out.println(buffer.length);
    }
    in.close();
  }

  public void read3() throws IOException {
    SSHClient ssh = new SSHClient();
    // ssh.useCompression(); // Can lead to significant speedup (needs JZlib in classpath)
    ssh.addHostKeyVerifier(new PromiscuousVerifier());
    ssh.connect("172.16.48.4");
    ssh.authPassword("root", "2021fight");

//    Session session = ssh.startSession();
//    Command cmd = session.exec("cat /data8/qhl/cluster/kill_iotdb.sh");
//    String ret = IOUtils.readFully(cmd.getInputStream()).toString();
//    System.out.println(ret);
//    session.close();
//    ssh.close();

    System.out.println("11111111");
    try {
//      ssh.newSCPFileTransfer()
//          .download("/data8/qhl/cluster/kill_iotdb.sh", new FileSystemFile("/tmp/a.txt"));

      File newFile = new File("/tmp/qhl/111/222/333/a.txt");
      System.out.println(newFile.getAbsolutePath());
      if (newFile.exists()) {
        System.out.println("exist");
        return;
      }
      if (!newFile.exists()) {
        if (!newFile.getParentFile().exists()) {
          FileUtils.forceMkdir(newFile.getParentFile());
          System.out.println("111111");
        }
        newFile.createNewFile();
      }
      String rfile = "/data8/qhl/data-migration/1616553751003-2-0.tsfile";
      ssh.newSCPFileTransfer()
          .download(
              rfile,
              new FileSystemFile(newFile.getPath()));
    } finally {
      ssh.disconnect();
    }

  }


  public void readScp() throws JSchException {
    String user = "root";
    String host = "172.16.48.4";
    String password = "2021fight";
    String rfile = "/data8/qhl/data-migration/1616553751003-1-0.tsfile";
    JSch jsch = new JSch();
    Session session = jsch.getSession(user, host, 22);
    System.out.println("44444");
    // username and password will be given via UserInfo interface.
    System.out.println("333333");
    session.setPassword(password);
    System.out.println("111111");
    session.connect();
    System.out.println("111111");

    String outFile = "qhl.out";

    ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");

    try (InputStream stream = sftp.get(rfile);
        OutputStream outputStream = new FileOutputStream(new File(outFile))) {

      while (stream.available() != 0) {
        outputStream.write(stream.read());
      }
      // read from br
    } catch (SftpException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void moveHardLink() throws IOException {
    String fileName1 = "/tmp/1/1.txt";
    String fileName2 = "/tmp/2/2.txt";
    File f1 = new File(fileName1);
    File f2 = new File(fileName2);

    if (!f1.exists()) {
      f1.getParentFile().mkdirs();
      f1.createNewFile();
    }

    FileOutputStream fos = new FileOutputStream(f1);
    for (int i = 0; i < 100; i++) {
      String str = "yes!" + i;
      fos.write(str.getBytes());
    }

    Random random = new Random();
    String hardlinkSuffix = System.currentTimeMillis() + "_" + random.nextLong();
    File hardlink = new File(fileName1 + hardlinkSuffix);
    Files.createLink(Paths.get(hardlink.getAbsolutePath()), Paths.get(f1.getAbsolutePath()));

    FileUtils.moveFile(f1, f2);
  }

  public static void main(String[] args) throws IOException, JSchException {
    File newFile = new File("/1/1/1/1/1/1.aa");
    System.out.println(newFile.getAbsolutePath());

//    RemoteFileLoad remoteFileLoad = new RemoteFileLoad();
//    System.out.println(System.currentTimeMillis());
//    remoteFileLoad.read3();
//    System.out.println(System.currentTimeMillis());
////    remoteFileLoad.moveHardLink();
  }
}
