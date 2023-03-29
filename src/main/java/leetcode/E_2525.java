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

package leetcode;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2023-02-19 12:44
 */
public class E_2525 {
  public String categorizeBox(int length, int width, int height, int mass) {
    boolean isBukly = isBukly(length, width, height);
    boolean isHeavy = isHeavy(mass);
    Math.abs(2);
    if (isHeavy && isBukly) {
      return "Both";
    }

    if (!isHeavy && !isBukly) {
      return "Neither";
    }

    if (isHeavy) {
      return "Heavy";
    }

    return "Bulky";
  }

  private boolean isBukly(int length, int width, int height) {
    if (length >= 10000 || width >= 10000 || height >= 10000) {
      return true;
    }

    long size = (long) length * width * height;
    System.out.println(size);
    return size >= 1000000000l;
  }

  private boolean isHeavy(int mass) {
    return mass >= 100;
  }

  public static void main(String[] args) {
    E_2525 e_2525 = new E_2525();
    String res = e_2525.categorizeBox(2909, 3968, 3272, 727);
    System.out.println(res);
  }
}
