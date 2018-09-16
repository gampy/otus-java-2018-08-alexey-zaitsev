package ru.otus.l02;

import com.google.gson.*;

/**
 * VM options
 * -XX:-UseTLAB
 */


public class Homework {

    static Runtime runtime = Runtime.getRuntime();

    private static void gc() throws InterruptedException {
        runtime.gc();
        Thread.sleep(10);
    }

    private static long getFreeMem() throws InterruptedException  {
        gc();
        return runtime.freeMemory();
    }

    public static void main(String[] args) throws InterruptedException {

        Object[] array = {
                "",
                new String(""),
                new String(new char[0]),
                new String(new char[1]),
                new String(new char[2]),
                new String(new char[10]),
                new Object(),
                new String(new byte[0]),
                new Gson(),
        };

        long freeBefore;
        for (int i = 0; i < array.length; i++) {
            System.out.print("Class: " + array[i].getClass().getName() + " размер, байт: ");
            int len;
            try {
                len = ((String) array[i]).length();
            } catch (ClassCastException e) {
                len = -1;
            }
            gc();
            freeBefore = getFreeMem();
            array[i] = null;
            System.out.print((getFreeMem()-freeBefore));
            if (len > -1) System.out.println(" для размера контейнера " + len);
            else System.out.println();
        }



    }

}
