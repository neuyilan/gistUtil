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

package FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import org.apache.iotdb.tsfile.fileSystem.FSFactoryProducer;
import org.apache.iotdb.tsfile.utils.BytesUtils;
import org.apache.iotdb.tsfile.utils.ReadWriteIOUtils;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2022-12-13 14:42
 */
public class FileInputStreamSkip {

  public static void main(String[] args) {
    String tsFilePath = "/Users/houliangqi/Desktop/1670342029354-1-1-1.tsfile.resource";
    try (InputStream inputStream =
        FSFactoryProducer.getFSFactory()
            .getBufferedInputStream(tsFilePath)) {
      // The first byte is VERSION_NUMBER, second byte is timeIndexType.
      ReadWriteIOUtils.readBytes(inputStream, 2);
      // will cause error
      getDevices(inputStream);

      // ok
//      deserialize(inputStream);
    } catch (Exception e) {
      throw new RuntimeException(
          "Failed to get devices from tsfile: " + tsFilePath);
    }
  }

  public static void deserialize(InputStream inputStream) throws IOException {
    int deviceNum = readInt(inputStream);
    for (int i = 0; i < deviceNum; i++) {
      long startTime = readLong(inputStream);
      long endTime = readLong(inputStream);
      System.out.println("startTime=" + startTime + ",endTime=" + endTime);
    }
    for (int i = 0; i < deviceNum; i++) {
      String path = readString(inputStream);
      int index = readInt(inputStream);
      System.out.println("path=" + path + ",index=" + index);
    }
  }

  public static Set<String> getDevices(InputStream inputStream) throws IOException {
    int deviceNum = ReadWriteIOUtils.readInt(inputStream);
    long excepted = 2L * deviceNum * ReadWriteIOUtils.LONG_LEN;
    // 如果用inputStream.skip就会出错。
//    long real = inputStream.skip(excepted);
    long real = skip(inputStream, excepted);
    System.out.println("excepted=" + excepted + ", real=" + real);
    Set<String> devices = new HashSet<>();
    for (int i = 0; i < deviceNum; i++) {
      String path = readString(inputStream);
      System.out.println(path);
      inputStream.skip(ReadWriteIOUtils.INT_LEN);
      devices.add(path);
    }
    return devices;
  }

  public static String readString(InputStream inputStream) throws IOException {
    int strLength = readInt(inputStream);
    if (strLength <= 0) {
      return null;
    }
    byte[] bytes = new byte[strLength];
    int readLen = inputStream.read(bytes, 0, strLength);
    if (readLen != strLength) {
      throw new IOException("String.format(RETURN_ERROR, strLength, readLen)");
    }
    return new String(bytes, 0, strLength);
  }

  public static int readInt(InputStream inputStream) throws IOException {
    byte[] bytes = new byte[4];
    int readLen = inputStream.read(bytes);
    if (readLen != 4) {
      throw new IOException("String.format(RETURN_ERROR, INT_LEN, readLen)");
    }
    return BytesUtils.bytesToInt(bytes);
  }

  public static long readLong(InputStream inputStream) throws IOException {
    byte[] bytes = new byte[8];
    int readLen = inputStream.read(bytes);
    if (readLen != 8) {
      throw new IOException("String.format(RETURN_ERROR, LONG_LEN, readLen)");
    }
    return BytesUtils.bytesToLong(bytes);
  }

  public static long skip(InputStream inputStream, long n) throws IOException {
    while (n > 0) {
      long skipN = inputStream.skip(n);
      if (skipN > 0) {
        n -= skipN;
      } else {
        // read one byte to decide should we retry
        if (inputStream.read() == -1) {
          // EOF
          break;
        } else {
          n--;
        }
      }
    }
    return n;
  }
}
