package hardLinkTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author HouliangQi(neuyilan @ 163.com)
 * @description
 * @since 2020-07-25 3:57 下午
 */
public class HardLinkTest {
    private String originFileName = "test.file";
    private long maxCount = 1000L;

    private byte[] dataToWrite = new byte[1024 * 1024];

    public void createFile() {
        File file = new File(originFileName);
        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();
                if (!created) {
                    System.out.println("create file failed");
                }
            }
            FileOutputStream fos = new FileOutputStream(file);
            for (int i = 0; i < maxCount; i++) {
                String str = "yes!" + i;
                fos.write(str.getBytes());
                fos.write(dataToWrite);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hardLinkTest() {
        File originFile = new File(originFileName);
        String hardlinkSuffix = "." + System.currentTimeMillis();
        File hardlink = new File(originFile.getAbsolutePath() + hardlinkSuffix);
        try {
            Files.createLink(Paths.get(hardlink.getAbsolutePath()), Paths.get(originFile.getAbsolutePath()));
        } catch (IOException e) {
            System.out.println(String.format("Cannot create hardlink for %s: ", originFile, e));
        }

        // 判断两个文件的内容是否一致
        md5Check(originFile, hardlink);
    }

    public void md5Check(File file1, File file2) {
        String file1MD5 = null;
        String file2MD5 = null;
        FileInputStream fis1 = null;
        FileInputStream fis2 = null;
        try {
            fis1 = new FileInputStream(file1);
            file1MD5 = DigestUtils.md5Hex(fis1);

            fis2 = new FileInputStream(file2);
            file2MD5 = DigestUtils.md5Hex(fis2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("file1MD5:" + file1MD5);
        System.out.println("file2MD5:" + file2MD5);
        System.out.println("file1MD5.equals(file2MD5):" + file1MD5.equals(file2MD5));
    }

    public static void main(String[] args) {
        HardLinkTest test = new HardLinkTest();
        test.createFile();
        test.hardLinkTest();
    }
}
