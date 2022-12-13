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

package macaddress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComputerInfoUtils {

  private static final Logger logger = LoggerFactory
      .getLogger(ComputerInfoUtils.class);
  private static String macAddressStr = null;
  private static String computerName = System.getenv().get("COMPUTERNAME");

  private static final String[] windowsCommand = {"ipconfig", "/all"};
  private static final String[] ifconfigLinuxCommand = {"/sbin/ifconfig", "-a"};
  private static final String[] ipLinuxCommand = {"/sbin/ip", "address"};
  private static final Pattern macPattern =
      Pattern.compile(".*((:?[0-9a-f]{2}[-:]){5}[0-9a-f]{2}).*", Pattern.CASE_INSENSITIVE);

  /**
   * 获取多个网卡地址
   *
   * @return
   * @throws IOException
   */
  private static final List<String> getMacAddressListUsingIfConfig() throws IOException {
    ArrayList<String> macAddressList = new ArrayList<String>();
    final String os = System.getProperty("os.name");
    String command[];
    boolean isLinux = false;
    if (os.startsWith("Windows")) {
      command = windowsCommand;
    } else if (os.startsWith("Linux")) {
      command = ipLinuxCommand;
      isLinux = true;
    } else {
      throw new IOException("Unknow operating system:" + os);
    }

    macAddressList = getMacAddressListUsingIpAddr(command);
    if (macAddressList.isEmpty() && isLinux) {
      logger.warn(
          "can not get mac address using command={}, try using={}", command, ifconfigLinuxCommand);
      command = ifconfigLinuxCommand;
      macAddressList = getMacAddressListUsingIfConfig(command);
    }
    if (macAddressList.isEmpty()) {
      logger.error("get mac address failed");
    }
    return macAddressList;
  }

  /**
   * 获取多个网卡地址
   *
   * @return
   * @throws IOException
   */
  private static final ArrayList<String> getMacAddressListUsingIfConfig(String[] command)
      throws IOException {
    final ArrayList<String> macAddressList = new ArrayList<String>();
    // 执行命令
    final Process process = Runtime.getRuntime().exec(command);

    BufferedReader bufReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    for (String line = null; (line = bufReader.readLine()) != null; ) {
      Matcher matcher = macPattern.matcher(line);
      if (matcher.matches()) {
        macAddressList.add(matcher.group(1));
        // macAddressList.add(matcher.group(1).replaceAll("[-:]",
        // ""));//去掉MAC中的“-”
      }
    }

    process.destroy();
    bufReader.close();
    return macAddressList;
  }

  private static final ArrayList<String> getMacAddressListUsingIpAddr(String[] command)
      throws IOException {
    final ArrayList<String> macAddressList = new ArrayList<String>();
    // 执行命令
    final Process process = Runtime.getRuntime().exec(command);

    BufferedReader bufReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    for (String line = null; (line = bufReader.readLine()) != null; ) {
      Matcher matcher = macPattern.matcher(line);
      if (matcher.matches()) {
        for (int i = 0; i < matcher.groupCount(); i++) {
          System.out.println("index= " + i + "," + matcher.group(i));
        }
        macAddressList.add(matcher.group(1));
        // macAddressList.add(matcher.group(1).replaceAll("[-:]",
        // ""));//去掉MAC中的“-”
      }
    }

    process.destroy();
    bufReader.close();
    return macAddressList;
  }

  /**
   * 获取一个网卡地址（多个网卡时从中获取一个）
   *
   * @return
   */
  public static String getMacAddress() {
    if (macAddressStr == null || macAddressStr.equals("")) {
      StringBuffer sb = new StringBuffer(); // 存放多个网卡地址用，目前只取一个非0000000000E0隧道的值
      try {
        List<String> macList = getMacAddressListUsingIfConfig();
        for (Iterator<String> iter = macList.iterator(); iter.hasNext(); ) {
          String amac = iter.next();
          if (!amac.equals("00-00-00-00-00-E0")) {
            sb.append(amac);
            sb.append(",");
          }
        }
      } catch (IOException e) {
        logger.error("get mac address failed", e);
      }
      macAddressStr = sb.toString();
    }
    return macAddressStr.toUpperCase();
  }

  /**
   * 获取电脑名
   *
   * @return
   */
  public static String getComputerName() {
    if (computerName == null || computerName.equals("")) {
      computerName = System.getenv().get("COMPUTERNAME");
    }
    return computerName;
  }

  /**
   * 获取客户端IP地址
   *
   * @return
   */
  public static String getIpAddrAndName() throws IOException {
    return InetAddress.getLocalHost().toString();
  }

  /**
   * 获取客户端IP地址
   *
   * @return
   */
  public static String getIpAddr() throws IOException {
    return InetAddress.getLocalHost().getHostAddress().toString();
  }

  /**
   * 获取电脑唯一标识
   *
   * @return
   */
  public static String getComputerID() {
    String id = getMacAddress();
    if (id == null || id.equals("")) {
      try {
        id = getIpAddrAndName();
      } catch (IOException e) {
        logger.error("get computer ID failed", e);
      }
    }
    return computerName;
  }

  /**
   * 限制创建实例
   */
  private ComputerInfoUtils() {
  }

  public static void main(String[] args) throws IOException {
//    String line = "b0:26:28:c5:9c:de";
//    String line = "link/ether b0:26:28:c5:9c:de brd ff:ff:ff:ff:ff:ff";
    String line = "ether b0:26:28:e9:73:d0  txqueuelen 1000  (Ethernet)";
//    String line = "b0:26:28:c5:9c:de";
//    Pattern pattern = Pattern
//        .compile("([0-9A-Fa-f]{2}[\\.:-]){5}([0-9A-Fa-f]{2})");
//    Matcher matcher = pattern.matcher(line);
//    Pattern p = Pattern.compile("^([a-fA-F0-9][:-]){5}[a-fA-F0-9][:-]$", Pattern.CASE_INSENSITIVE);
//    Pattern p = Pattern
//        .compile(".*^((:?[0-9a-f]{2}[-:]){5}[0-9a-f]{2}).*$.*", Pattern.CASE_INSENSITIVE);

    String arr[] = line.split(" ");
    Pattern p =
        Pattern.compile(".*((:?[0-9a-f]{2}[-:]){5}[0-9a-f]{2}).*", Pattern.CASE_INSENSITIVE);

    for (String str : arr) {
      Matcher matcher = p.matcher(str);
      System.out.println("1111=" + matcher.find());
      if (matcher.matches()) {
        for (int i = 0; i < matcher.groupCount(); i++) {
          System.out.println("index= " + i + "," + matcher.group(i));
        }
      }
    }

//    Matcher matcher = p.matcher(line);
//    System.out.println("1111=" + matcher.find());
//    if (matcher.matches()) {
//      for (int i = 0; i < matcher.groupCount(); i++) {
//        System.out.println("index= " + i + "," + matcher.group(i));
//      }
//    }
//    System.out.println(getMacAddress());

    List<String> list = Arrays
        .asList("aa", "bb", "bb", "cc", "bbf", "FF:FF:FF:FF:FF:FF", "FF:FF:FF:FF:FF:FF",
            "00:00:00:00:00:00");
    List newList = list.stream().distinct().collect(Collectors.toList());
    System.out.println(newList);
  }
}
