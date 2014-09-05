package com.drguildo.stdlib.benchmarks;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.drguildo.stdlib.datastructures.Trie;

public class TrieBenchmark {
  private static final String DICT_PATH = "resources/2of4brif.txt";

  public static void main(String[] args) throws IOException {
    ArrayList<String> a = new ArrayList<String>();
    Trie t = new Trie();

    List<String> lines = Files.readAllLines(Paths.get(DICT_PATH),
        Charset.forName("UTF-8"));

    System.out.println("dictionary size: " + lines.size());

    long startTime = System.currentTimeMillis();
    for (String line : lines) {
      a.add(line);
    }
    long endTime = System.currentTimeMillis();
    System.out.println("ArrayList add: " + (endTime - startTime) + "ms");

    startTime = System.currentTimeMillis();
    for (String line : lines) {
      t.add(line);
    }
    endTime = System.currentTimeMillis();
    System.out.println("Trie add: " + (endTime - startTime) + "ms");

    startTime = System.currentTimeMillis();
    for (String line : lines) {
      a.contains(line);
    }
    endTime = System.currentTimeMillis();
    System.out.println("ArrayList lookup: " + (endTime - startTime) + "ms");

    startTime = System.currentTimeMillis();
    for (String line : lines) {
      t.contains(line);
    }
    endTime = System.currentTimeMillis();
    System.out.println("Trie lookup: " + (endTime - startTime) + "ms");
  }
}
