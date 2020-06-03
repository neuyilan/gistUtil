本仓库主要存放一些小的工具和实验，以备后续查看
# autoreload
场景：对于一些配置希望热加载，即不希望重启服务就能够热加载配置文件。
解决方案：使用apache.commons.configuration的工具类来实现的。
# concurrenthashmap
场景：用来测试两个线程同时对同一个map里面的key进行访问，一个线程往这个map里面put东西；另外一个线程从这个map里面取数据。
模仿了golang的chan模型
# hostnametoIP
把hostname 转化为ip的一个工具
# simpledateformat
详细描述了SimpleDateFormat中，对于毫秒位置不能够正确识别的问题。
# synchronizedtest
本类主要演示了synchronized中notify的使用