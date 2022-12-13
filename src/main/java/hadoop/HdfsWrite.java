package hadoop;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.HarFileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.fs.StorageType;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DirectoryListing;
import org.apache.hadoop.hdfs.protocol.HdfsFileStatus;
import org.apache.hadoop.hdfs.protocol.HdfsLocatedFileStatus;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.tools.HadoopArchives;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2021-10-19 10:45
 */
public class HdfsWrite {

  private static final Logger logger = LoggerFactory.getLogger(HdfsWrite.class);

  public static void main(String[] args) throws Exception {
    writeArchiveFile4();
//    readArchiveFile();
//    readFile();
//    testGetBlockInfo();
//    writeFile();
  }

  public static void writeArchiveFile2() throws Exception {
    Configuration conf = new Configuration();
//    conf.addResource(new Path(
//        "/Users/houliangqi/codespace/github.com/neuyilan/gistUtil/src/main/resources/hdfs-site.xml"));
    System.out.println("dfs.nameservices=" + conf.get("dfs.nameservices"));
//    String hdfsuri = "hdfs://172.16.48.4:9000/";
//    // Set FileSystem URI
//    conf.set("fs.defaultFS", hdfsuri);
////    // Because of Maven
//    conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
//    conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
//    // 设置hadoop应用程序执行用户
//    System.setProperty("HADOOP_USER_NAME", "iotdb");
//    System.setProperty("hadoop.home.dir", "/");
//
//    UserGroupInformation userGroupInformation = UserGroupInformation.createRemoteUser("iotdb");
//    UserGroupInformation.setLoginUser(userGroupInformation);
//    JobConf jobConf = new JobConf(conf);
//    jobConf.setUser("iotdb");
//    jobConf.setJobName("ARCHIVE_121.har");
    HadoopArchives hadoopArchives = new HadoopArchives(conf);
    List<String> argsList = new ArrayList<>();
//  String str = "-archiveName,ARCHIVE_131.har,-p,/xcloud/qhl_cluster.data/QHL_DB_3/TEST_DOUBLE_SMALL_13/UNICOM_201206_D_NUMBER_53,PARTITION_102,/xcloud/qhl_cluster.data/QHL_DB_3/TEST_DOUBLE_SMALL_13/UNICOM_201206_D_NUMBER_53/ARCHIVE/ARCHIVE_1";
    String str = "-archiveName,ARCHIVE_150.har,-p,/xcloud/qhl_cluster.data/QHL_DB_3/TEST_DOUBLE_SMALL_13/UNICOM_201206_QUANJIAO_85,PARTITION_87,/xcloud/qhl_cluster.data/QHL_DB_3/TEST_DOUBLE_SMALL_13/UNICOM_201206_QUANJIAO_85/ARCHIVE/ARCHIVE_121";
    argsList.addAll(Arrays.asList(str.split(",")));
    System.out.println(argsList);
//    int res = ToolRunner.run(hadoopArchives, argsList.toArray(new String[0]));
    int res = hadoopArchives.run(argsList.toArray(new String[0]));
    System.out.println("res=" + res);
//    hadoopArchives.run(argsList.toArray(new String[argsList.size()]));

  }


  public static void writeArchiveFile4() throws Exception {
    Configuration conf = new Configuration();
    conf.addResource(new Path(
        "/Users/houliangqi/codespace/github.com/neuyilan/gistUtil/src/main/resources/hdfs-site.xml"));
    conf.addResource(new Path(
        "/Users/houliangqi/codespace/github.com/neuyilan/gistUtil/src/main/resources/core-site.xml"));
    System.out.println("dfs.nameservices=" + conf.get("dfs.nameservices"));
    System.out.println("fs.defaultFS=" + conf.get("fs.defaultFS"));
//    // 设置hadoop应用程序执行用户
    System.setProperty("HADOOP_USER_NAME", "iotdb");
    System.setProperty("hadoop.home.dir", "/");

    JobConf jobConf = new JobConf(conf);
    jobConf.setUser("iotdb");
    jobConf.setJobName("ARCHIVE_121.har");

    HadoopArchives hadoopArchives = new HadoopArchives(jobConf);
    List<String> argsList = new ArrayList<>();
//    String str = "-archiveName,ARCHIVE_195.har,-p,/xcloud/qhl_cluster.data/QHL_DB_3/TEST_DOUBLE_SMALL_13/UNICOM_201206_QUANJIAO_85,PARTITION_95,/xcloud/qhl_cluster.data/QHL_DB_3/TEST_DOUBLE_SMALL_13/UNICOM_201206_QUANJIAO_85/ARCHIVE/ARCHIVE_121";
    String str = "-archiveName,ARCHIVE_new_4.har,-p,/xcloud/qhl_cluster.data/QHL_DB_3/TEST_DOUBLE_SMALL_13/UNICOM_201206_D_NUMBER_53,PARTITION_101,/xcloud/qhl_cluster.data/QHL_DB_3/TEST_DOUBLE_SMALL_13/UNICOM_201206_QUANJIAO_85/ARCHIVE/ARCHIVE_121";
    argsList.addAll(Arrays.asList(str.split(",")));
    System.out.println(argsList);
    int res = ToolRunner.run(hadoopArchives, argsList.toArray(new String[argsList.size()]));
    System.out.println(res);
  }


  public static void writeArchiveFile3() throws Exception {
    Configuration conf = new Configuration();
    // Set FileSystem URI
    String hdfsuri = "hdfs://172.16.48.4:9000/";
    conf.set("fs.defaultFS", hdfsuri);
    // Because of Maven
    conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());

//    UserGroupInformation userGroupInformation = UserGroupInformation.createRemoteUser("iotdb");
//    UserGroupInformation.setLoginUser(userGroupInformation);
//    JobConf jobConf = new JobConf(conf);
//    jobConf.setUser("iotdb");
//    jobConf.setJobName("ARCHIVE_121.har");
    HadoopArchives hadoopArchives = new HadoopArchives(conf);
    List<String> argsList = new ArrayList<>();
    String str = "-archiveName,ARCHIVE_143.har,-p,/xcloud/qhl_cluster.data/QHL_DB_3/TEST_DOUBLE_SMALL_13/UNICOM_201206_QUANJIAO_85,PARTITION_87,/xcloud/qhl_cluster.data/QHL_DB_3/TEST_DOUBLE_SMALL_13/UNICOM_201206_QUANJIAO_85/ARCHIVE/ARCHIVE_121";
    argsList.addAll(Arrays.asList(str.split(",")));
    System.out.println(argsList);
    hadoopArchives.run(argsList.toArray(new String[argsList.size()]));
  }

  public static void writeArchiveFile() throws Exception {
    Configuration conf = new Configuration();
    // Set FileSystem URI
    String hdfsuri = "hdfs://172.16.48.4:9000/";
    conf.set("fs.defaultFS", hdfsuri);
    // Because of Maven
    conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());

    UserGroupInformation userGroupInformation = UserGroupInformation.createRemoteUser("iotdb");
    UserGroupInformation.setLoginUser(userGroupInformation);
    JobConf jobConf = new JobConf(conf);
    jobConf.setUser("iotdb");
    jobConf.setJobName("ARCHIVE_121.har");
    HadoopArchives hadoopArchives = new HadoopArchives(jobConf);
    List<String> argsList = new ArrayList<>();
    String str = "-archiveName,ARCHIVE_126.har,-p,/xcloud/qhl_cluster.data/QHL_DB_3/TEST_DOUBLE_SMALL_13/UNICOM_201206_QUANJIAO_85,PARTITION_87,/xcloud/qhl_cluster.data/QHL_DB_3/TEST_DOUBLE_SMALL_13/UNICOM_201206_QUANJIAO_85/ARCHIVE/ARCHIVE_121";
    argsList.addAll(Arrays.asList(str.split(",")));
    System.out.println(argsList);
    hadoopArchives.run(argsList.toArray(new String[argsList.size()]));
  }


  public static void writeFile() throws IOException {
    //HDFS URI}
    String hdfsuri = "hdfs://172.16.48.4:9000/";

    String path = "/qhl/archive_max/";
    String fileName = "hello.csv";
    String fileContent = "hello;world";
    int fileCount = 15;

    // ====== Init HDFS File System Object
    Configuration conf = new Configuration();
    // Set FileSystem URI
    conf.set("fs.defaultFS", hdfsuri);
    // Because of Maven
    conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
    conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
    // 设置hadoop应用程序执行用户
    System.setProperty("HADOOP_USER_NAME", "iotdb");
    System.setProperty("hadoop.home.dir", "/");
    //创建 FileSystem - HDFS
    FileSystem fs = FileSystem.get(URI.create(hdfsuri), conf);

    //创建文件目录，如果不存在
    Path workingDir = fs.getWorkingDirectory();
    Path newFolderPath = new Path(path);
    if (!fs.exists(newFolderPath)) {
      // Create new Directory
      fs.mkdirs(newFolderPath);
      logger.info("Path " + path + " created.");
    }

    //==== Write file
    logger.info("Begin Write file into hdfs");

    for (int i = 10; i < fileCount; i++) {
      String newFileName = newFolderPath + "/" + fileName + "_" + (i + 1);
      //Create a path
      Path hdfswritepath = new Path(newFileName);
      //Init output stream
      FSDataOutputStream outputStream = fs.create(hdfswritepath);
      //Cassical output stream usage
      for (int j = 0; j < 10000000; j++) {
        outputStream.writeBytes(fileContent);
      }
      outputStream.close();
      logger.info("End Write file into hdfs");
    }

    fs.close();
  }

  public static void readArchiveFile2() throws IOException, URISyntaxException {
    //HDFS URI
    String hdfsuri = "hdfs://172.16.48.4:9000/";

    String readFile = "/qhl/archive_max/archive/archive_10001.har/hello.csv_4";
    // ====== Init HDFS File System Object
    Configuration conf = new Configuration();
    // Set FileSystem URI
    conf.set("fs.defaultFS", hdfsuri);
    // Because of Maven
    conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
    conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
    // 设置hadoop应用程序执行用户
    System.setProperty("HADOOP_USER_NAME", "iotdb");
    System.setProperty("hadoop.home.dir", "/");
    //创建 FileSystem - HDFS
    DistributedFileSystem underlyingFs = (DistributedFileSystem) DistributedFileSystem.get(conf);

    HarFileSystem harFileSystem = new HarFileSystem(underlyingFs);
    URI uri = new URI("har://" + readFile);
    harFileSystem.initialize(uri, conf);

    //==== Read file
    logger.info("Read file into hdfs");
    //Create a path
    Path hdfsreadpath = new Path(readFile);

    int count = 0;

    /************************重要，这里面说明了不同的文件，跨block的时候，返回的 文件位置 *****/
//    String readFile = "/qhl/archive_max/archive/archive_10001.har/hello.csv_0";   b=0,0,110000000,nn2,dn3,dn2

//    String readFile = "/qhl/archive_max/archive/archive_10001.har/hello.csv_14";  b=0,0,110000000,dn3,nn2,nn1

//        b=0,0,96870912,dn3,dn2,nn2
//        b=1,96870912,13129088,dn3,nn2,nn1
//        String readFile = "/qhl/archive_max/archive/archive_10001.har/hello.csv_4";

    FileStatus[] filesStatus = harFileSystem.listStatus(hdfsreadpath);
    for (int i = 0; i < filesStatus.length; i++) {
      FileStatus fs = filesStatus[i];
      System.out.println("77777777777status=" + fs.toString());

      BlockLocation[] blockLocations = harFileSystem.getFileBlockLocations(fs, 0, fs.getLen());
      System.out.println("8888888888888 b size=" + blockLocations.length);
      for (int b = 0; b < blockLocations.length; b++) {
        System.out.println("666666666666666 b=" + b + "," + blockLocations[b]);
      }
    }

    System.out.println("&&&&&&&&&&&&&&&&&&&&&&");
    harFileSystem.close();
  }


  public static void readArchiveFile() throws IOException, URISyntaxException {
    //HDFS URI
    String hdfsuri = "hdfs://172.16.48.4:9000/";

//    String readFile = "/qhl/real_archive_multi/archive_2.har/archive3/test1/test2/hello.csv_0";
    String readFile = "/xcloud/xcloud.data/REG_PARTITION_150/TEST_CREATE_161/CSV_1000_206555/ARCHIVE/ARCHIVE_1346750/ARCHIVE_1346750.har";
//    String readFile = "/qhl/real_archive_max/archive_max.har";

//    String readFile = "/qhl/archive_max/archive/archive_10001.har";

//    String readFile = "/qhl/real_archive/test_ar.har";
    // ====== Init HDFS File System Object
    Configuration conf = new Configuration();
    // Set FileSystem URI
    conf.set("fs.defaultFS", hdfsuri);
    // Because of Maven
    conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
    conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
    // 设置hadoop应用程序执行用户
    System.setProperty("HADOOP_USER_NAME", "iotdb");
    System.setProperty("hadoop.home.dir", "/");
    //创建 FileSystem - HDFS
    DistributedFileSystem underlyingFs = (DistributedFileSystem) DistributedFileSystem.get(conf);

    HarFileSystem harFileSystem = new HarFileSystem(underlyingFs);
    URI uri = new URI("har://" + readFile);
    harFileSystem.initialize(uri, conf);

    //==== Read file
    logger.info("Read file into hdfs");
    //Create a path
    Path hdfsreadpath = new Path(readFile);

    int count = 0;

    /************************重要，这里面说明了不同的文件，跨block的时候，返回的 文件位置 *****/
//    String readFile = "/qhl/archive_max/archive/archive_10001.har/hello.csv_0";   b=0,0,110000000,nn2,dn3,dn2

//    String readFile = "/qhl/archive_max/archive/archive_10001.har/hello.csv_14";  b=0,0,110000000,dn3,nn2,nn1

//        b=0,0,96870912,dn3,dn2,nn2
//        b=1,96870912,13129088,dn3,nn2,nn1
//        String readFile = "/qhl/archive_max/archive/archive_10001.har/hello.csv_4";

    String partitionFullPath = "/xcloud/xcloud.data/REG_PARTITION_150/TEST_CREATE_161/CSV_1000_206555/ARCHIVE/ARCHIVE_1346750/ARCHIVE_1346750.har/PARTITION_206569/SLICE_0";
    Path partitionPath = new Path(partitionFullPath);
    RemoteIterator<LocatedFileStatus> it = harFileSystem.listFiles(partitionPath, false);
    while (it.hasNext()) {
      LocatedFileStatus locatedFileStatus = it.next();
      System.out.println("00000000000=" + locatedFileStatus.toString());

      System.out.println("00000000000111=" + locatedFileStatus.getPath());

      BlockLocation[] blockLocations = locatedFileStatus.getBlockLocations();
      System.out.println("00000000000222=" + blockLocations.length);
      for (int i = 0; i < blockLocations.length; i++) {
        String[] storageId = blockLocations[i].getStorageIds();
        StorageType[] storageTypes = blockLocations[i].getStorageTypes();
        for (int j = 0; j < storageId.length; j++) {
          System.out.print("11111111=" + storageId[j] + " ");
        }
        System.out.println();

        for (int j = 0; j < storageTypes.length; j++) {
          System.out.print("22222222=" + storageTypes[j] + " ");
        }
        System.out.println();
      }
    }

    FileStatus[] filesStatus = harFileSystem.listStatus(partitionPath);
    for (int i = 0; i < filesStatus.length; i++) {
      FileStatus fs = filesStatus[i];
      System.out.println("77777777777status=" + fs.toString());
      String filename = fs.getPath().getName();
      if (fs.isDirectory()) { // 如果文件是目录
        if (filename.startsWith("SLICE")) { // 并且文件名以 "SLICE" 开头，那么说明这是接下来要使用的存放
          System.out.println("aaaaaaa=" + fs.toString());
          FileStatus[] filesStatus2 = harFileSystem.listStatus(fs.getPath());

          boolean result = harFileSystem.exists(fs.getPath());
          System.out.println("result = " + result);

          for (FileStatus fs2 : filesStatus2) {

            System.out.println("8888888888888status=" + fs2.toString());

            BlockLocation[] blockLocations = harFileSystem.getFileBlockLocations(fs2, 0,
                fs2.getLen());
            System.out.println("8888888888888 b size=" + blockLocations.length);
            for (int b = 0; b < blockLocations.length; b++) {
              System.out.println("666666666666666 b=" + b + "," + blockLocations[b]);
            }
          }
        }
      }
    }

    RemoteIterator<LocatedFileStatus> filesStatusIter = harFileSystem.listFiles(hdfsreadpath,
        false);
    count = 0;
    while (filesStatusIter.hasNext()) {
      LocatedFileStatus status = filesStatusIter.next();
      BlockLocation[] locations = status.getBlockLocations();
      for (int i = 0; i < locations.length; i++) {
        System.out.println("1111111locations=" + i + "," + locations[i]);
      }
      count++;
      System.out.println("111111status=" + status);
    }

    System.out.println("111111count=" + count);

    FileStatus status = harFileSystem.getFileStatus(hdfsreadpath);
    System.out.println("333333status=" + status);

    FSDataInputStream inputStream = harFileSystem.open(hdfsreadpath);

    FileStatus fileStatus = harFileSystem.getFileStatus(hdfsreadpath);
    System.out.println("qqqqqqqqqq=" + fileStatus);
    DistributedFileSystem dfs = (DistributedFileSystem) DistributedFileSystem.get(conf);

    BlockLocation[] blockLocations = harFileSystem.getFileBlockLocations(fileStatus, 0,
        fileStatus.getLen());
    System.out.println("11111111111=" + blockLocations.length);
    for (int i = 0; i < blockLocations.length; i++) {
      System.out.println(blockLocations[i]);
    }
    System.out.println("22222222");

    //Classical input stream usage
    String out = IOUtils.toString(inputStream, "UTF-8");
    logger.info(out);
    inputStream.close();
    harFileSystem.close();
  }


  public static void readFile() throws IOException, URISyntaxException {
    //HDFS URI}
    String hdfsuri = "hdfs://172.16.48.4:9000/";

//    String readFile = "/qhl/real_archive_multi/archive_2.har/archive3/test1/test2/hello.csv_0";
    String readFile = "/qhl/real_archive_multi/archive_2.har";
//    String readFile = "/qhl/real_archive_max/archive_max.har";
//    String readFile = "/qhl/real_archive/test_ar.har";
    // ====== Init HDFS File System Object
    Configuration conf = new Configuration();
    // Set FileSystem URI
    conf.set("fs.defaultFS", hdfsuri);
    // Because of Maven
    conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
    conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
    // 设置hadoop应用程序执行用户
    System.setProperty("HADOOP_USER_NAME", "iotdb");
    System.setProperty("hadoop.home.dir", "/");
    //创建 FileSystem - HDFS
    DistributedFileSystem underlyingFs = (DistributedFileSystem) DistributedFileSystem.get(conf);
//    FileSystem underlyingFs = FileSystem.get(URI.create(hdfsuri), conf);

    HarFileSystem harFileSystem = new HarFileSystem(underlyingFs);
    URI uri = new URI("har://" + readFile);
    harFileSystem.initialize(uri, conf);

    //==== Read file
    logger.info("Read file into hdfs");
    //Create a path
    Path hdfsreadpath = new Path(readFile);

    // 测试用普通的filesystem，能不能list har 文件

    RemoteIterator<LocatedFileStatus> filesStatus1 = underlyingFs.listFiles(hdfsreadpath, true);
    while (filesStatus1.hasNext()) {
      LocatedFileStatus status = filesStatus1.next();
      System.out.println("12345677=" + status.toString());
    }
    // 测试用普通的filesystem，能不能list har 文件

    RemoteIterator<LocatedFileStatus> filesStatus = harFileSystem.listFiles(hdfsreadpath, true);
    int count = 0;
    while (filesStatus.hasNext()) {
      LocatedFileStatus status = filesStatus.next();
//      BlockLocation[] locations = status.getBlockLocations();
//      for (int i = 0; i < locations.length; i++) {
//        System.out.println("1111111locations=" + i + "," + locations[i]);
//      }

      count++;
      System.out.println("111111status=" + status);
    }

    System.out.println("111111count=" + count);

    FileStatus status = harFileSystem.getFileStatus(hdfsreadpath);
    System.out.println("333333status=" + status);

    FSDataInputStream inputStream = harFileSystem.open(hdfsreadpath);

    FileStatus fileStatus = harFileSystem.getFileStatus(hdfsreadpath);
    System.out.println("qqqqqqqqqq=" + fileStatus);
    DistributedFileSystem dfs = (DistributedFileSystem) DistributedFileSystem.get(conf);

    BlockLocation[] blockLocations = harFileSystem.getFileBlockLocations(fileStatus, 0,
        fileStatus.getLen());
    System.out.println("11111111111=" + blockLocations.length);
    for (int i = 0; i < blockLocations.length; i++) {
      System.out.println(blockLocations[i]);
    }
    System.out.println("22222222");

    //Classical input stream usage
    String out = IOUtils.toString(inputStream, "UTF-8");
    logger.info(out);
    inputStream.close();
    harFileSystem.close();
  }


  public static void testGetBlockInfo() throws IOException {
    //HDFS URI}
    String hdfsuri = "hdfs://172.16.48.4:9000/";
    String readFile = "/qhl/real_archive_multi/archive_2.har/archive3/test1/test2/hello.csv_99";
    // ====== Init HDFS File System Object
    Configuration conf = new Configuration();
    // Set FileSystem URI
    conf.set("fs.defaultFS", hdfsuri);
    // Because of Maven
    conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
    conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
    // 设置hadoop应用程序执行用户
    System.setProperty("HADOOP_USER_NAME", "iotdb");
    System.setProperty("hadoop.home.dir", "/");
    //创建 FileSystem - HDFS
    DistributedFileSystem dfs = (DistributedFileSystem) DistributedFileSystem.get(conf);

//    String slicePath = "/xcloud/xcloud.data/REG_PARTITION_150/TEST_CREATE_161/CSV_1002_206759/PARTITION_206761/SLICE_0";
//    String slicePath = "/qhl/real_archive_multi/archive_2.har";
    String slicePath = "/qhl/archive_max/archive/archive_10001.har/";
    byte[] listingPoint = HdfsFileStatus.EMPTY_NAME;
    DirectoryListing listPaths = null;
    listPaths = dfs.getClient().listPaths(slicePath, listingPoint, true);

    System.out.println("11111111111111=" + listPaths.getPartialListing().length);

    for (HdfsFileStatus item : listPaths.getPartialListing()) {  // 遍历取出 slice 下的这部分文件
      HdfsLocatedFileStatus hlfStatus = (HdfsLocatedFileStatus) item;  // HdfsLocatedFileStatus 包含了文件块信息
      System.out.println("1111111111,hlfStatus=" + hlfStatus.getLocalName() + "," + hlfStatus);
      if (hlfStatus.getLocalName().equals("_SUCCESS")) {
        System.out.println("7777777777777777777");
        continue;
      }
      BlockInfo blockInfo = getBlockInfo(hlfStatus);
      System.out.println("9999999999=" + hlfStatus + "," + blockInfo);
    }
  }

  public static BlockInfo getBlockInfo(HdfsLocatedFileStatus hlfStatus) throws IOException {
//    LocatedBlocks locatedBlocks = hlfStatus.getLocatedBlocks();
//    List<LocatedBlock> locatedBlockList = locatedBlocks.getLocatedBlocks();
//    String[] dpLocationVolIds = locatedBlockList.get(0).getStorageIDs();
//    StorageType[] dpLocationDiskTypes = locatedBlockList.get(0).getStorageTypes();
//    BlockLocation[] blockLocations = DFSUtilClient.locatedBlocks2Locations(locatedBlockList);
//    String[] dpLocationHosts = blockLocations[0].getHosts();
//    BlockInfo blockInfo = new BlockInfo();
//    blockInfo.setHosts(dpLocationHosts);
//    blockInfo.setStorageIds(dpLocationVolIds);
//    blockInfo.setStorageTypes(dpLocationDiskTypes);
//    return blockInfo;
    return null;
  }

  static class BlockInfo {

    private String[] hosts; // Datanode hostnames
    private String[] storageIds; // Storage ID of each replica
    private StorageType[] storageTypes; // Storage type of each replica

    public String[] getHosts() {
      return hosts;
    }

    public void setHosts(String[] hosts) {
      this.hosts = hosts;
    }

    public String[] getStorageIds() {
      return storageIds;
    }

    public void setStorageIds(String[] storageIds) {
      this.storageIds = storageIds;
    }

    public StorageType[] getStorageTypes() {
      return storageTypes;
    }

    public void setStorageTypes(StorageType[] storageTypes) {
      this.storageTypes = storageTypes;
    }

    @Override
    public String toString() {
      return "BlockInfo{" + " hosts=" + Arrays.toString(hosts) + ", storageIds=" + Arrays.toString(
          storageIds) + ", storageTypes=" + Arrays.toString(storageTypes) + "}";
    }
  }

}
