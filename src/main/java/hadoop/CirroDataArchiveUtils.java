/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package hadoop;

import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.tools.HadoopArchives;
import org.apache.hadoop.util.ToolRunner;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2023-03-24 11:10
 */
public class CirroDataArchiveUtils {

  public static int doArchiveTask(Configuration conf, String[] args, String user) throws Exception {
    UserGroupInformation userGroupInformation = UserGroupInformation.createRemoteUser(user);
    int res = userGroupInformation.doAs(new PrivilegedExceptionAction<Integer>() {
      public Integer run() throws Exception {
        HadoopArchives archives = new HadoopArchives(conf);
        return ToolRunner.run(archives, args);
      }
    });
    return res;
  }

  /**
   * do archive task with the kerberos
   */
  public static int doArchiveTaskWithKerberos(Configuration conf, String[] args,
      String userPrincipal, String keytabFile) throws Exception {
    UserGroupInformation userGroupInformation = UserGroupInformation.loginUserFromKeytabAndReturnUGI(
        userPrincipal, keytabFile);
    int res = userGroupInformation.doAs(new PrivilegedExceptionAction<Integer>() {
      public Integer run() throws Exception {
        HadoopArchives archives = new HadoopArchives(conf);
        return ToolRunner.run(archives, args);
      }
    });
    return res;
  }


  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    // Set FileSystem URI
    String hdfsuri = "hdfs://172.16.48.4:9000/";
    conf.set("fs.defaultFS", hdfsuri);
    // Because of Maven
    conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
    List<String> argsList = new ArrayList<>();
    String str = "-archiveName,ARCHIVE_440303299597434880.har,"
        + "-p,"
        + "/cirro_cluster_test0307/cirro_cluster_test0307.data/REG_PARTITION_1026/TEST_DOUBLE_SMALL_1027/UNICOM_201206_QUANJIAO_1059,PARTITION_1060,"
        + "/cirro_cluster_test0307/cirro_cluster_test0307.data/REG_PARTITION_1026/TEST_DOUBLE_SMALL_1027/UNICOM_201206_QUANJIAO_1059/ARCHIVE/ARCHIVE_8";
    argsList.addAll(Arrays.asList(str.split(",")));
    String[] aargs = argsList.toArray(new String[argsList.size()]);
    String user = "iotdb";

    int res = CirroDataArchiveUtils.doArchiveTask(conf, aargs, user);
    System.out.println("the result=" + res);


  }
}
