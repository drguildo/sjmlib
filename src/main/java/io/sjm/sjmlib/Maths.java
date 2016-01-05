package io.sjm.sjmlib;

public class Maths {
  public static int gcd(int x, int y) {
    return y == 0 ? x : gcd(y, x % y);
  }

  public static float div(int a, int b) {
    return (float) a / (float) b;
  }
}
