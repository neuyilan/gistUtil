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

package treemap;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2023-02-01 12:02
 */
public class TreeMapTest {

  private static TreeMap<Long, TmpClass> sequenceFiles = new TreeMap<>();

  private static final ReadWriteLock resourceListLock = new ReentrantReadWriteLock();
  private static Long key = 1000L;


  public void create() {
    Thread t1 = new Thread(() -> {
      resourceListLock.readLock().lock();
      sequenceFiles.computeIfAbsent(key, l -> new TmpClass());
//      System.out.println("create");
      resourceListLock.readLock().unlock();
    });
    while (true) {
      t1.run();
    }
  }

  public void read2() {
    Thread t1 = new Thread(() -> {
      resourceListLock.readLock().lock();
      Map<Long, TmpClass> chosenMap =  sequenceFiles ;
      List<TmpClass> allResources = new ArrayList<>();
      for (Map.Entry<Long, TmpClass> entry : chosenMap.entrySet()) {
        allResources.add(entry.getValue());
      }
      resourceListLock.readLock().unlock();
    });
    while (true) {
      t1.run();
    }
  }


  public void clear() {
    Thread t2 = new Thread(() -> {
      resourceListLock.writeLock().lock();
      sequenceFiles.clear();
//      System.out.println("clear");
      resourceListLock.writeLock().unlock();
    });
    while (true) {
      t2.run();
    }
  }

  public static void main(String[] args) {
    TreeMapTest treeMapTest = new TreeMapTest();
    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();

    /**
     * Common Thread Pool
     */
    ExecutorService excutor = new ThreadPoolExecutor(100, 200, 0L, TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<Runnable>(102400), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

//    excutor.submit(()->treeMapTest.create());
////    excutor.submit(()->treeMapTest.clear());
//    excutor.submit(()->treeMapTest.read2());


    System.out.println(sequenceFiles);
    sequenceFiles.computeIfAbsent(key, l -> new TmpClass());
    System.out.println(sequenceFiles);


  }


  static class TmpClass {

  }

}
