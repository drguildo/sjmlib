package io.sjm.sjmlib;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Arrays {
  public static int[] unique(int[] a) {
    Set<Integer> tmp = new LinkedHashSet<Integer>();
    for (Integer n : a) {
      tmp.add(n);
    }

    int[] output = new int[tmp.size()];
    int i = 0;
    for (Integer n : tmp) {
      output[i++] = n;
    }

    return output;
  }

  public static <T> String toString(T[][] a) {
    StringBuilder sb = new StringBuilder();

    sb.append("[");
    for (int i = 0; i < a.length; i++) {
      sb.append("[");
      for (int j = 0; j < a[i].length; j++) {
        sb.append(a[i][j]);
        if (j != a[i].length - 1)
          sb.append(", ");
      }
      sb.append("]");
      if (i != a.length - 1)
        sb.append(", ");
    }
    sb.append("]");

    return sb.toString();
  }

  public static String toString(int[][] a) {
    StringBuilder sb = new StringBuilder();

    sb.append("[");
    for (int i = 0; i < a.length; i++) {
      sb.append("[");
      for (int j = 0; j < a[i].length; j++) {
        sb.append(a[i][j]);
        if (j != a[i].length - 1)
          sb.append(", ");
      }
      sb.append("]");
      if (i != a.length - 1)
        sb.append(", ");
    }
    sb.append("]");

    return sb.toString();
  }

  public static String toString(double[][] a) {
    StringBuilder sb = new StringBuilder();

    sb.append("[");
    for (int i = 0; i < a.length; i++) {
      sb.append("[");
      for (int j = 0; j < a[i].length; j++) {
        sb.append(a[i][j]);
        if (j != a[i].length - 1)
          sb.append(", ");
      }
      sb.append("]");
      if (i != a.length - 1)
        sb.append(", ");
    }
    sb.append("]");

    return sb.toString();
  }

  public static String toString(boolean[][] a) {
    StringBuilder sb = new StringBuilder();

    sb.append("[");
    for (int i = 0; i < a.length; i++) {
      sb.append("[");
      for (int j = 0; j < a[i].length; j++) {
        sb.append(a[i][j]);
        if (j != a[i].length - 1)
          sb.append(", ");
      }
      sb.append("]");
      if (i != a.length - 1)
        sb.append(", ");
    }
    sb.append("]");

    return sb.toString();
  }

  /**
   * Finds the largest value in an array of doubles.
   *
   * @param a the array of doubles
   * @return the largest value in the array
   */
  public static double max(double[] a) {
    if (a.length == 0)
      throw new IllegalArgumentException("array is empty");

    double m = a[0];

    if (a.length > 1)
      for (int i = 1; i < a.length; i++)
        if (a[i] > m)
          m = a[i];

    return m;
  }

  /**
   * Finds the largest value in an array of integers.
   *
   * @param a the array of integers
   * @return the largest value in the array
   */
  public static int max(int[] a) {
    if (a.length == 0)
      throw new IllegalArgumentException("array is empty");

    int m = a[0];

    if (a.length > 1)
      for (int i = 1; i < a.length; i++)
        if (a[i] > m)
          m = a[i];

    return m;
  }

  /**
   * A convenience method for converting an array of bytes to an ArrayList of byte objects.
   *
   * @param elems a primitive array of bytes
   * @return an ArrayList of Byte objects
   */
  public static ArrayList<Byte> toArrayList(byte[] elems) {
    ArrayList<Byte> list = new ArrayList<>();
    for (byte e : elems)
      list.add(e);
    return list;
  }

  /**
   * A convenience method for converting an array of primitive shorts to an ArrayList of Short
   * objects.
   *
   * @param elems a primitive array of shorts
   * @return an ArrayList of Short objects
   */
  public static ArrayList<Short> toArrayList(short[] elems) {
    ArrayList<Short> list = new ArrayList<>();
    for (short e : elems)
      list.add(e);
    return list;
  }

  /**
   * A convenience method for converting an array of primitive integers to an ArrayList of Integer
   * objects.
   *
   * @param elems a primitive array of integers
   * @return an ArrayList of Integer objects
   */
  public static ArrayList<Integer> toArrayList(int[] elems) {
    ArrayList<Integer> list = new ArrayList<>();
    for (int e : elems)
      list.add(e);
    return list;
  }

  /**
   * A convenience method for converting an array of primitive longs to an ArrayList of Long
   * objects.
   *
   * @param elems a primitive array of longs
   * @return an ArrayList of Long objects
   */
  public static ArrayList<Long> toArrayList(long[] elems) {
    ArrayList<Long> list = new ArrayList<>();
    for (long e : elems)
      list.add(e);
    return list;
  }

  /**
   * A convenience method for converting an array of primitive floats to an ArrayList of Float
   * objects.
   *
   * @param elems a primitive array of floats
   * @return an ArrayList of Float objects
   */
  public static ArrayList<Float> toArrayList(float[] elems) {
    ArrayList<Float> list = new ArrayList<>();
    for (float e : elems)
      list.add(e);
    return list;
  }

  /**
   * A convenience method for converting an array of primitive doubles to an ArrayList of Double
   * objects.
   *
   * @param elems a primitive array of doubles
   * @return an ArrayList of Double objects
   */
  public static ArrayList<Double> toArrayList(double[] elems) {
    ArrayList<Double> list = new ArrayList<>();
    for (double e : elems)
      list.add(e);
    return list;
  }

  /**
   * A convenience method for converting an array of primitive booleans to an ArrayList of Boolean
   * objects.
   *
   * @param elems a primitive array of booleans
   * @return an ArrayList of Boolean objects
   */
  public static ArrayList<Boolean> toArrayList(boolean[] elems) {
    ArrayList<Boolean> list = new ArrayList<>();
    for (boolean e : elems)
      list.add(e);
    return list;
  }

  /**
   * A convenience method for converting an array of primitive chars to an ArrayList of Character
   * objects.
   *
   * @param elems a primitive array of chars
   * @return an ArrayList of Character objects
   */
  public static ArrayList<Character> toArrayList(char[] elems) {
    ArrayList<Character> list = new ArrayList<>();
    for (char e : elems)
      list.add(e);
    return list;
  }

  public static void main(String[] args) {
    System.out.println(toString(new String[][] {{""}}));
  }
}
