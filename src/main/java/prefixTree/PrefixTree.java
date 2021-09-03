package prefixTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import utils.RamUsageEstimator;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2021-04-12 15:17
 */
public class PrefixTree {

  private int sgNum = 100;
  private int sgNameMinLength = 50;
  private int sgNameMaxLength = 100;
  private String ROOT = "root.";

  private int deviceNum = 10_000_00;
  private Trie trieTree = new Trie();
  private Random random = new Random();
  private List<String> sgNameList = new ArrayList<>();

  private List<String> allDevicesList = new ArrayList<>();

  public static void main(String[] args) {
    PrefixTree prefixTree = new PrefixTree();
    prefixTree.constructTrieTree();
    System.out
        .println("sg number = " + prefixTree.sgNameList.size() + ",the trie tree memory usage="
            + RamUsageEstimator.humanSizeOf(prefixTree));

    prefixTree.constructDevice();
    prefixTree.test();
  }


  private void constructTrieTree() {
    String template = constructTemplate();
    for (int i = 0; i < sgNum; i++) {
      String tmpSgName = ROOT + createRandomStr(template);
      trieTree.insert(tmpSgName);
      sgNameList.add(tmpSgName);
    }
  }


  private void constructDevice() {
    int deviceNumForEachSg = deviceNum / sgNameList.size();
    for (int i = 0; i < sgNameList.size(); i++) {
      String sgName = sgNameList.get(i);
      for (int j = 0; j < deviceNumForEachSg; j++) {
        String tmpDevice = sgName + ".device_" + j;
        String noExistTmpDevice = sgName + "_no" + ".device_" + j;
        allDevicesList.add(noExistTmpDevice);
        allDevicesList.add(tmpDevice);
      }
    }
  }

  private void test() {
    long totalCostTime = 0;
    for (int i = 0; i < allDevicesList.size(); i++) {
      long start = System.nanoTime();
      boolean result = trieTree.search2(allDevicesList.get(i));
      totalCostTime += (System.nanoTime() - start);
    }

    System.out
        .println(
            "total device num = " + allDevicesList.size() + ", total time cost= " + totalCostTime
                + "ns, avg=" + totalCostTime / allDevicesList.size() + "ns");
  }


  public String constructTemplate() {
    String template = "";
    for (char c = ' '; c < '~'; c++) {
      template += c;
    }
    System.out.println("the storage group string template=" + template);
    return template;
  }

  public String createRandomStr(String template) {
    StringBuilder sb = new StringBuilder();
    int deviceLength = (int) (Math.random() * (sgNameMaxLength - sgNameMinLength) + sgNameMinLength
        + 1);
    sb.append("\"");
    int templateLength = template.length();
    for (int i = 0; i < deviceLength; i++) {
      int number = random.nextInt(templateLength);
      char tmpChar = template.charAt(number);
      if (tmpChar == '"') {
        sb.append('\\');
      }
      sb.append(tmpChar);
    }
    sb.append("\"");
    return sb.toString();
  }
}

class TrieNode {

  //记录孩子节点
  TrieNode[] child;
  //记录当前节点是不是一个单词的结束字母
  boolean is_end;

  public TrieNode() {
    //从空格开始，到ascii结束，共计96个字符
    child = new TrieNode[96];
    is_end = false;
  }
}

class Trie {

  private final char first = ' ';
  // 记录前缀树的根
  TrieNode root;

  /**
   * Initialize your data structure here.
   */
  public Trie() {
    root = new TrieNode();
  }

  /**
   * Inserts a word into the trie.
   */
  public void insert(String word) {
    TrieNode ptr = root;//从根出发
    for (int i = 0; i < word.length(); i++) {
      char c = word.charAt(i);//对于每个单词
      if (ptr.child[c - first] == null) {//如果c - first为空，说明还没有存入
        ptr.child[c - first] = new TrieNode();//存入节点
      }
      ptr = ptr.child[c - first];//指向当前节点
    }
    ptr.is_end = true;//最后的节点为单词的最后一个单词，is_end设置为true
  }

  /**
   * Returns if the word is in the trie.
   */
  public boolean search(String word) {
    TrieNode ptr = root;//从根出发
    for (int i = 0; i < word.length(); i++) {
      char c = word.charAt(i);//对于每个字母
      if (ptr.child[c - first] == null) {//如果不存在于前缀树中，返回false
        return false;
      }
      ptr = ptr.child[c - first];
    }
    return ptr.is_end;//如果所有字符都在前缀树中，那么判断最后一个字母结束标志是否为true，
    // 为true，返回true，说明单词找到，否则，false，没找到
  }

  /**
   * Returns if there is any word in the trie that starts with the given prefix.
   */
  public boolean startsWith(String prefix) {
    TrieNode ptr = root;//从根出发
    for (int i = 0; i < prefix.length(); i++) {
      char c = prefix.charAt(i);//对于每个字母
      if (ptr.child[c - first] == null) {//如果不存在于前缀树中，返回false
        return false;
      }
      ptr = ptr.child[c - first];
    }
    return true;
  }

  public boolean search2(String word) {
    TrieNode ptr = root;//从根出发
    char pre = ' ';
    char current = ' ';
    int quotationNum = 0;
    for (int i = 0; i < word.length(); i++) {
      char c = word.charAt(i);//对于每个字母
      current = c;
      if (pre != '\\' && current == '"') {
        quotationNum++;
      }
      pre = c;
      if (ptr.child[c - first] == null) {//如果不存在于前缀树中，返回false
        if (c == '.' && quotationNum % 2 == 0) {
          return true;
        }
        return false;
      }
      ptr = ptr.child[c - first];
    }
    return ptr.is_end;//如果所有字符都在前缀树中，那么判断最后一个字母结束标志是否为true，
    // 为true，返回true，说明单词找到，否则，false，没找到
  }
}

