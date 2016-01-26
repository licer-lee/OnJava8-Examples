// tasks/MapComparisons.java
// (c)2016 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://mindviewinc.com/Books/OnJava/ for more book information.
// {Args: 1 10 10} (Fast verification check during build)
// Rough comparison of thread-safe Map performance
import java.util.concurrent.*;
import java.util.*;
import onjava.*;

abstract class MapTest
extends Tester<Map<Integer,Integer>> {
  MapTest(String testId, int nReaders, int nWriters) {
    super(testId, nReaders, nWriters);
  }
  class Reader extends TestTask {
    long result = 0;
    void test() {
      for(long i = 0; i < testCycles; i++)
        for(int n = 0; n < collectionSize; n++)
          result += testCollection.get(n);
    }
    void putResults() {
      readResult += result;
      readTime += duration;
    }
  }
  class Writer extends TestTask {
    void test() {
      for(long i = 0; i < testCycles; i++)
        for(int n = 0; n < collectionSize; n++)
          testCollection.put(n, writeData[n]);
    }
    void putResults() {
      writeTime += duration;
    }
  }
  void startReadersAndWriters() {
    for(int i = 0; i < nReaders; i++)
      exec.execute(new Reader());
    for(int i = 0; i < nWriters; i++)
      exec.execute(new Writer());
  }
}

class SynchronizedHashMapTest extends MapTest {
  Map<Integer,Integer> collectionInitializer() {
    return Collections.synchronizedMap(
      new HashMap<>(
        FilledMap.map(
          new onjava.Count.Integer(),
          new onjava.Count.Integer(),
          collectionSize)));
  }
  SynchronizedHashMapTest(int nReaders, int nWriters) {
    super("Synched HashMap", nReaders, nWriters);
  }
}

class ConcurrentHashMapTest extends MapTest {
  Map<Integer,Integer> collectionInitializer() {
    return new ConcurrentHashMap<>(
      FilledMap.map(
        new onjava.Count.Integer(),
        new onjava.Count.Integer(),
        collectionSize));
  }
  ConcurrentHashMapTest(int nReaders, int nWriters) {
    super("ConcurrentHashMap", nReaders, nWriters);
  }
}

public class MapComparisons {
  public static void main(String[] args) {
    Tester.initMain(args);
    new SynchronizedHashMapTest(10, 0);
    new SynchronizedHashMapTest(9, 1);
    new SynchronizedHashMapTest(5, 5);
    new ConcurrentHashMapTest(10, 0);
    new ConcurrentHashMapTest(9, 1);
    new ConcurrentHashMapTest(5, 5);
    Tester.exec.shutdown();
  }
}
/* Output:
Type                             Read time     Write time
Synched HashMap 10r 0w             2825885              0
Synched HashMap 9r 1w              3463976          74283
readTime + writeTime =             3538259
Synched HashMap 5r 5w               744537         633254
readTime + writeTime =             1377791
ConcurrentHashMap 10r 0w            437445              0
ConcurrentHashMap 9r 1w             545597          78268
readTime + writeTime =              623865
ConcurrentHashMap 5r 5w              58343         232524
readTime + writeTime =              290867
*/