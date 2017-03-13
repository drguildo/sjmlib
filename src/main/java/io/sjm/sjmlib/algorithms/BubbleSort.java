/*
 * Copyright (c) 2016, Simon Morgan
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package io.sjm.sjmlib.algorithms;

import io.sjm.sjmlib.Rand;

import java.util.Arrays;
import java.util.List;

public class BubbleSort {
  public static <T extends Comparable<T>> List<T> sort(List<T> elems) {
    final int last = elems.size() - 1;
    for (int i = 0; i < last; i++)
      for (int j = 0; j < last; j++)
        if (elems.get(j).compareTo(elems.get(j + 1)) > 0) {
          T tmp = elems.get(j + 1);
          elems.set(j + 1, elems.get(j));
          elems.set(j, tmp);
        }
    return elems;
  }

  public static void main(String[] args) {
    Rand r = new Rand();
    List<Integer> integers = Arrays.asList(4, 3, 2, 1, 5, 7, -2, -29, -6, 0);
    System.out.println(integers);
    sort(integers);
    System.out.println(integers);
  }
}
