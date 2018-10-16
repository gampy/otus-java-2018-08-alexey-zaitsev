package ru.otus.l05;

import java.util.ArrayList;
import java.util.List;


public class SomeApp implements SomeAppMBean{

    private static volatile int size = 0;
    List<Obj> list;
    List<List<Obj>> lists = new ArrayList<>(size);

    void run() throws InterruptedException {

       GCLogger gclogger = new GCLogger();

       for (int i = 0; i < size; i++) {
           list = new ArrayList<>(size);
           for (int j = 0; j < size; j++) {
               list.add(new Obj());
               gclogger.collectMetrics();
               Thread.sleep(300);
           }
           lists.add(list);
           list = null;
       }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    static class Obj {
        private final double[] array = new double [256 * 256];
       // private final double[] array = new double [1024 * 1024];

        public double[] getArray() { return array; }
    }

}


