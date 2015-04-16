/*
 * Copyright (c) 2014, Simon Morgan <sjm@sjm.io>
 * 
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without
 * fee is hereby granted, provided that the above copyright notice and this permission notice appear
 * in all copies.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS
 * SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE
 * AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
 * NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE
 * OF THIS SOFTWARE.
 */

package io.sjm.stdlib.datastructures;

public class BinaryTree<T extends Comparable> {
  private class Node {
    Node left = null;
    Node right = null;
    T data;

    public Node(T data) {
      this.data = data;
    }

    @Override
    public String toString() {
      return String.format("Node(%s, %s, %s)", left, right, data);
    }
  }

  private Node root;

  public BinaryTree() {
    root = null;
  }

  public void insert(T data) {
    if (root == null) {
      root = new Node(data);
    } else {
      insert(root, data);
    }
  }

  private void insert(Node cur, T data) {
    // cur is the Node to begin searching at
    // data is the data you wish to insert into the tree
    int n = data.compareTo(cur.data);

    if (n < 0) {
      if (cur.left == null)
        cur.left = new Node(data);
      else
        insert(cur.left, data);
    } else if (n > 0) {
      if (cur.right == null)
        cur.right = new Node(data);
      else
        insert(cur.right, data);
    }
  }

  public long count() {
    return count(root);
  }

  private long count(Node node) {
    if (node != null) {
      return 1 + count(node.left) + count(node.right);
    } else {
      return 0;
    }
  }

  public boolean lookup(T data) {
    return lookup(root, data);
  }

  private boolean lookup(Node cur, T data) {
    if (cur == null)
      return false;
    else if (data.compareTo(cur.data) < 0)
      return lookup(cur.left, data);
    else if (data.compareTo(cur.data) > 0)
      return lookup(cur.right, data);
    else
      return true;
  }

  @Override
  public String toString() {
    return root.toString();
  }

  public static void main(String[] args) {
    BinaryTree<Integer> bt = new BinaryTree<Integer>();
    for (int i = 0; i < 100; i++) {
      bt.insert(i);
    }

    System.out.println(bt.lookup(-1));
  }
}
