package io.sjm.stdlib.datastructures;

import java.util.HashSet;
import java.util.Set;

public class Sets {
  @SuppressWarnings("unchecked")
  public static <T> Set<T> buildSet(T... args) {
    HashSet<T> s = new HashSet<>();
    for (T elem : args)
      s.add(elem);
    return s;
  }

  /**
   * A non-mutable version of the set intersection operation.
   * 
   * The method Set.retainAll performs the same function but mutates the first set.
   * 
   * @see Set#retainAll(java.util.Collection)
   * @param a
   * @param b
   * @return A new set representing the intersection of a and b.
   */
  public static <T> Set<T> intersection(Set<T> a, Set<T> b) {
    HashSet<T> s = new HashSet<>(a);
    s.retainAll(b);
    return s;
  }
}
