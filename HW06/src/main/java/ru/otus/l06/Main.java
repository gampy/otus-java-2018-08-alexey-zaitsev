package ru.otus.l06;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<Obj> references;

    void fill(List<Obj> list, int size) {
        for (int i = 0; i < size; i++) {
            list.add(new Obj(i));
        }
    }

    static void print(List<Obj> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("Value for " + i + ": " + ((list.get(i) != null)? list.get(i).get0(): "null"));
        }
    }

    // VM:
    // -Xms512m -Xmx512m
    public static void main(String[] args) throws InterruptedException {
        references = new ArrayList<>();

        Main m = new Main();
        m.fill(references, 20);
        System.out.println("List of 20 Strong-referenced elements: ");
        print(references);

        MyAppCache mac = new MyAppCache(6, 300, 0);
        mac.put(references);
        System.out.println("\nLimited SoftReference Cache (6 elements): ");
        mac.print();
        System.out.println("Cache cumulative lifetime < " + 300 +"ms: ");
        Thread.sleep(200);
        mac.print();
        System.out.println("Cache cumulative lifetime >= " + 300 +"ms: ");
        Thread.sleep(100);
        mac.print();
        mac.getCache().dispose();

        m.fill(references, 50);
        System.out.println("\nAdding 30 extra Strong-referenced elements to the List:");
        print(references);

        MyAppCache mac2 = new MyAppCache(70, 300, 0);
        mac2.put(references);
        System.out.println("\nCache2 before Garbage Collection of Soft References:: ");
        mac2.print();
        System.gc();
        Thread.sleep(180);
        System.out.println("\nCache2 after Garbage Collection of Soft References:");
        mac2.print();

        mac2.getCache().dispose();
    }

     class Obj {
        private Integer[] array = (Integer[]) Array.newInstance(Integer.class, 1024 * 1024);

        Obj(Integer i) {
            array[0] = i;
        }

        public Integer get0() {
            return array[0];
        }

        @Override
        public String toString() {
            return array[0].toString();
        }
    }
}