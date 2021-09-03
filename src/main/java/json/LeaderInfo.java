package json;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2021-09-01 19:25
 */
public class LeaderInfo {

  private String ip;
  private int port;


  public LeaderInfo(String ip, int port) {
    this.ip = ip;
    this.port = port;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  @Override
  public String toString() {
    return "LeaderInfo{"
        + " ip='" + ip + '\''
        + ", port=" + port
        + "}";
  }
}
