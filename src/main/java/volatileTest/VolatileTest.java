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

package volatileTest;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2022-06-28 16:32
 */
public class VolatileTest {

  private volatile static int num = 0;

  public static void numPlus() {
    num++;
  }

  public static void main(String[] args) {
    VolatileTest volatileTest = new VolatileTest();
    for (int i = 0; i < 10; i++) {
      new Thread(() -> {
        for (int j = 0; j < 2000; j++) {
          volatileTest.numPlus();
        }
      }).start();
    }
    while (Thread.activeCount() > 2) {
      System.out.println(Thread.activeCount() + ", " + num);
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.println(volatileTest.num);
  }
}
