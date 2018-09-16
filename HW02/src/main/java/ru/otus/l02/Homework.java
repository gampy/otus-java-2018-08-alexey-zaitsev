package ru.otus.l02;

import com.google.gson.*;

/**
 * VM options
 * -XX:-UseTLAB
 */


public class Homework {

    static Runtime runtime = Runtime.getRuntime();

    private static long getFreeMem() throws InterruptedException {
        runtime.gc();
        Thread.sleep(10);
        return runtime.freeMemory();
    }

    public static void main(String[] args) throws InterruptedException {

        Object[] array = {
                "",
                new String(""),
                new String(new char[0]),
                new Object(),
                new String(new byte[0]),
                new Gson(),
        };

        long freeBefore = 0L;
        for (int i = 0; i < array.length; i++) {
            System.out.print("Class: " + array[i].getClass().getName() + " size, bytes: ");
            if (i == 0) { runtime.gc(); Thread.sleep(10); freeBefore = getFreeMem(); }
            array[i] = null;
            System.out.println((getFreeMem()-freeBefore));
            freeBefore = getFreeMem();
        }



    }

}
