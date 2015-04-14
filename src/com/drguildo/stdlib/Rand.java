package com.drguildo.stdlib;

import java.util.List;

/**
 * @author Simon Morgan <sjm@eml.cc>
 */
public class Rand {
  private final java.util.Random rand = new java.util.Random();

  /**
   * Generates a random readable ASCII string of a specified length.
   *
   * @param len the length of the string
   * @return the random string
   */
  public String randString(int len) {
    String s = "";

    for (int i = 0; i < len; i++) {
      s += (char) (33 + rand.nextInt(93));
    }

    return s;
  }

  /**
   * Generates a list of random boolean values.
   *
   * @param len the number of values to generate
   * @return a list of random booleans
   */
  public boolean[] randArrayBool(int len) {
    boolean[] a = new boolean[len];

    for (int i = 0; i < a.length; i++) {
      a[i] = rand.nextBoolean();
    }

    return a;
  }

  /**
   * Generates a list of random integers.
   *
   * @param len the number of values to generate
   * @return a list of random integers
   */
  public int[] randArrayInt(int len) {
    int[] a = new int[len];

    for (int i = 0; i < a.length; i++) {
      a[i] = rand.nextInt();
    }

    return a;
  }

  /**
   * Generates a list of random integers, each one being between 0 (inclusive) and max (exclusive).
   * See {@link Rand#randArrayInt(int)}.
   *
   * @param len the number of values to generate
   * @param max the maximum value (exclusive) to generate
   * @return a list of random integers
   */
  public int[] randArrayInt(int len, int max) {
    int[] a = new int[len];

    for (int i = 0; i < a.length; i++) {
      a[i] = rand.nextInt(max);
    }

    return a;
  }

  /**
   * Generates a list of random doubles, each one being between 0.0 and 1.0.
   *
   * @param len the number of values to generate
   * @return a list of random doubles
   */
  public double[] randArrayDouble(int len) {
    double[] a = new double[len];

    for (int i = 0; i < a.length; i++) {
      a[i] = rand.nextDouble();
    }

    return a;
  }

  public boolean nextBoolean(double p) {
    return (rand.nextInt(100) * 0.01) < p;
  }

  public String chooseString(List<String> l) {
    if (l.isEmpty())
      throw new IllegalArgumentException("List cannot be empty");
    else
      return l.get(rand.nextInt(l.size()));
  }
}
