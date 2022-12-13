package murmurHash64;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2021-12-16 14:57
 */
public class MurmurHash64 {

  /**
   * Generates 64 bit hash from byte array of the given length and seed.
   *
   * @param data   byte array to hash
   * @param length length of the array to hash
   * @param seed   initial seed value
   * @return 64 bit hash of the given array
   */
  static final int murmur_seed_l0 = 100663319;

//  public static UnsignedLong hash64(final byte[] data, int length, int seed) {
////    System.out.println("length=" + length);
////    final long m = 0xc6a4a7935bd1e995L;
//    UnsignedLong m = UnsignedLong.valueOf(0xc6a4a7935bd1e995L);
//    final int r = 47;
//
//    UnsignedLong h = UnsignedLong.valueOf((seed & 0xffffffffL) ^ (length * m));
////    long h = (seed ) ^ (length * m);
//
//    int length8 = length / 8;
//
//    for (int i = 0; i < length8; i++) {
//      final int i8 = i * 8;
//
//      UnsignedLong k = UnsignedLong.valueOf(data[i8 + 0] & 0xff).
//          plus(UnsignedLong.valueOf((data[i8 + 1] & 0xff) << 8)).
//          plus(UnsignedLong.valueOf((data[i8 + 2] & 0xff) << 16)).
//          plus(UnsignedLong.valueOf((data[i8 + 3] & 0xff) << 24)).
//          plus(UnsignedLong.valueOf((data[i8 + 4] & 0xff) << 32)).
//          plus(UnsignedLong.valueOf((data[i8 + 5] & 0xff) << 40)).
//          plus(UnsignedLong.valueOf((data[i8 + 6] & 0xff) << 48)).
//          plus(UnsignedLong.valueOf((data[i8 + 7] & 0xff) << 56));
//
////      long k = Long.parseUnsignedLong(String.valueOf(data[i8 + 0] & 0xff)) +
////          Long.parseUnsignedLong(String.valueOf((data[i8 + 1] & 0xff) << 8)) +
////          Long.parseUnsignedLong(String.valueOf((data[i8 + 2] & 0xff) << 16)) +
////          Long.parseUnsignedLong(String.valueOf((data[i8 + 3] & 0xff) << 24)) +
////          Long.parseUnsignedLong(String.valueOf((long) (data[i8 + 4] & 0xff) << 32)) +
////          Long.parseUnsignedLong(String.valueOf((long) (data[i8 + 5] & 0xff) << 40)) +
////          Long.parseUnsignedLong(String.valueOf((long) (data[i8 + 6] & 0xff) << 48)) +
////          Long.parseUnsignedLong(String.valueOf((long) (data[i8 + 7] & 0xff) << 56));
//
//      k = k.minus(m);
////      k *= m;
////      k ^= k >>> r;
//
//      UnsignedLong tmpK = UnsignedLong.valueOf(k.longValue() >>> r);
//      UnsignedLong tmpK2 = UnsignedLong.valueOf(k.longValue() ^ (tmpK.longValue()));
//      k = tmpK2;
//
//      k *= m;
//
//      h ^= k;
//      h *= m;
//    }
//
//    switch (length % 8) {
//      case 7:
//        h ^= Long.parseUnsignedLong(String.valueOf((long) (data[(length & ~7) + 6] & 0xff) << 48));
//      case 6:
//        h ^= Long.parseUnsignedLong(String.valueOf((long) (data[(length & ~7) + 5] & 0xff) << 40));
//      case 5:
//        h ^= Long.parseUnsignedLong(String.valueOf((long) (data[(length & ~7) + 4] & 0xff) << 32));
//      case 4:
//        h ^= Long.parseUnsignedLong(String.valueOf((long) (data[(length & ~7) + 3] & 0xff) << 24));
//      case 3:
//        h ^= Long.parseUnsignedLong(String.valueOf((long) (data[(length & ~7) + 2] & 0xff) << 16));
//      case 2:
//        h ^= Long.parseUnsignedLong(String.valueOf((long) (data[(length & ~7) + 1] & 0xff) << 8));
//      case 1:
//        h ^= Long.parseUnsignedLong(String.valueOf((long) (data[length & ~7] & 0xff)));
//        h *= m;
//    }
//
//    h ^= h >>> r;
//    h *= m;
//    h ^= h >>> r;
//
//    return Long.parseUnsignedLong(String.valueOf(h));
//  }

  /**
   * Generates 64 bit hash from byte array of the given length and seed.
   *
   * @param data   byte array to hash
   * @param length length of the array to hash
   * @param seed   initial seed value
   * @return 64 bit hash of the given array
   */
  public static BigDecimal hash64(final byte[] data, int length, int seed) {
    final long m = 0xc6a4a7935bd1e995L;
    final int r = 47;

    long h = (seed & 0xffffffffL) ^ (length * m);

    int length8 = length / 8;

    for (int i = 0; i < length8; i++) {
      final int i8 = i * 8;
      long k = ((long) data[i8 + 0] & 0xff) + (((long) data[i8 + 1] & 0xff) << 8)
          + (((long) data[i8 + 2] & 0xff) << 16) + (((long) data[i8 + 3] & 0xff) << 24)
          + (((long) data[i8 + 4] & 0xff) << 32) + (((long) data[i8 + 5] & 0xff) << 40)
          + (((long) data[i8 + 6] & 0xff) << 48) + (((long) data[i8 + 7] & 0xff) << 56);

      k *= m;
      k ^= k >>> r;
      k *= m;

      h ^= k;
      h *= m;
    }

    switch (length % 8) {
      case 7:
        h ^= (long) (data[(length & ~7) + 6] & 0xff) << 48;
      case 6:
        h ^= (long) (data[(length & ~7) + 5] & 0xff) << 40;
      case 5:
        h ^= (long) (data[(length & ~7) + 4] & 0xff) << 32;
      case 4:
        h ^= (long) (data[(length & ~7) + 3] & 0xff) << 24;
      case 3:
        h ^= (long) (data[(length & ~7) + 2] & 0xff) << 16;
      case 2:
        h ^= (long) (data[(length & ~7) + 1] & 0xff) << 8;
      case 1:
        h ^= (long) (data[length & ~7] & 0xff);
        h *= m;
    }
    ;

    h ^= h >>> r;
    h *= m;
    h ^= h >>> r;

//    UnsignedLong unsignedLong = UnsignedLong.valueOf(h);
//    System.out.println("aaaaaa=" + getUnsigned(h));
    return getUnsigned(h);
  }

  static BigDecimal getUnsigned(long signed) {
//    System.out.println(Long.MAX_VALUE);
//    return signed >= 0 ? signed
//        : BigDecimal.valueOf(2 * Long.MAX_VALUE + 2 + signed).longValue();

    if (signed >= 0) {
      return BigDecimal.valueOf(signed);
    }
    BigDecimal value = BigDecimal.valueOf(Long.MAX_VALUE).add(BigDecimal.valueOf(Long.MAX_VALUE))
        .add(BigDecimal.valueOf(2))
        .add(BigDecimal.valueOf(signed));
//    System.out.println(value);
//    System.out.println(Long.parseUnsignedLong(value.toString()));

    return value;
  }

  public static List<String> getRandomStrings() {
    List<String> result = new ArrayList<>();
    Random random = new Random();
    for (int i = 0; i < 10; i++) {
      int length = random.nextInt(300);
      result.add(RandomStringUtils.randomAlphabetic(length));
    }
    return result;
  }

  public static void main(String[] args) {
//    List<String> result = getRandomStrings();
//    for (String str : result) {
//      System.out.println(str);
//    }

    List<String> result = new ArrayList<>();
    result.add(
        "BBkTyzaaURGiLOGORNgRJHOrSvpdVOmSNMzguwdcqrXrqFRgErjxpmbuRLfNXjFPWhAIThljoNmMlSLPTUtWYvAZGcAEdmMCZXInxMAcmmsAeeUKZbZbnZWfvkWxOFiKYPUiZHgaEQViniDiXLBYJkBhcWDVddGRMwjuSsytKMBHccgVwVbUkKyZURSwD");
    result.add("yvu");
    result.add("LZkAJmeEMJnklCYzRpRWehvogghyKXgZMzWxCItIYYFuhzFQmhmishNmXwozUXYLf");
    result.add(
        "DkOpqIkyRqMptuRBvADSPWKvrBeoVBqBPJLHcFmRfnGziZfqYgJAjRXSZxfnGNRtGGLOxLzHHVzYOArKPxPoHHsADlmfkfaXBfkyeFNw");
    result.add(
        "yXuvYDHEwMcyWmvKxQbrYMiFgcGzMVwlWZuhPDVGDUIXpwKRuzpQyaVSdnJKIydUgqaPPQpNqVluQatrCigBUzLBhgnhjjCzAJZdCEjYAbGzLsqfqcqvEQoXFXcgkrQRFmVqzBanuSquhZYcSmhBNZxOFzMXjtYjnSeRBGsWzwZpZUQYUlCRiiaRAMSutaYDdWCAogmUnTjH");
    result.add(
        "TwvfJgJnOZFJtdEcBJXbAefPGZZIZLfRObQkEvLDMFQRXvrwYojXQjrFzFoyQRwCkMvQwEvMlktREhjtZvtVxKKwBaVcmAjzHjQCZQanjWzbZRvAAYyJpbvSfPerWeODGqmajriiLMjiPuQQMvjmqTDoWLVCvGeXLrZzMjaoYFCDdMwbWAiE");
    result.add(
        "osJRPnELOvqfppFCYKFjynAEcoOjoQtQwDKOQRtdLrGgkEderfjbdTXyBlAagJNSEbzUDwSaWyaIMEbSOMFNSHvszbNEUbaumrFKdVkxrXiQnBPCVUnyFdZnpZFsrihjhxCSsgBwAvllWEwGWGNjDIsGBaMuZRkKgjHrqTJKEzFGzgboxLNbavDmbVVzGmSAMtq");
    result.add(
        "IohQBmASTbnmqmnkLAnIoRJiPerhCiovaLhHAqQIZGUgrBWDPjeXqUQNDTZfdrTlrsRtdwupiuHAPXZwblfoQqpjhTCGSDIOllcuqgMWMSxKcgzuNCnOCnHsrBaOuYeeFynppazluKilIgxZFnxovbpYLpKAFMNhYHhMwFGHIaCjieriAkiCUdJnAhbEWjuHZKiWgvfQRyssBCsrZyZtIHWcfZdeExExTUmOythbQEeJWIoFkIYQAcHZskQYqZeXLfxmKDpr");
    result.add(
        "jfDuTRLVcNYhuHnmjePXLYpmuIPoWxCYJAkPEwsupcVSipIbEhPqSMCgHsBHodzWrSOIYmSlbxlGzKcYHiRl");
    result.add(
        "yOVuJmfXPCQUFrHFFhXqKhvcSrGCfXgmcCVHKvQNzvBVHrfcbECfCLThtVMknpLlQktrJuqBzuRQOlcOIlecYOkveYNhzoBGeHnll");

    System.out.println("*******************");
    for (String str : result) {
//    String str = "DkOpqIkyRqMptuRBvADSPWKvrBeoVBqBPJLHcFmRfnGziZfqYgJAjRXSZxfnGNRtGGLOxLzHHVzYOArKPxPoHHsADlmfkfaXBfkyeFNw";
      BigDecimal res = hash64(str.getBytes(), str.getBytes().length, murmur_seed_l0);
      System.out.println(res.divideAndRemainder(BigDecimal.valueOf(2))[1]);
      System.out.println(str + "," + res);
    }
  }
}
