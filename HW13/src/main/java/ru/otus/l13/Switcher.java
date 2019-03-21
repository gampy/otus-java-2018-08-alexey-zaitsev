package ru.otus.l13;

import java.util.function.Supplier;

public class Switcher {
    private static final int MIN = 1;
    private static final int MAX = 10;

    private static volatile int counter;
    private static volatile boolean ascending = true;

    private volatile Thread lastThread;


    Switcher() {
        this.counter = MIN;
    }

    public synchronized void run(Thread thread, boolean doInc) {
        while (true) {
            if (thread.equals(lastThread)) {
                wait(this);
            } else {
                out();
                if (doInc) next();
                lastThread = thread;
                notify();
                sleep(300);
            }
        }
    }

    private synchronized void next() {
        if (ascending) swap(() -> ++counter >= MAX);
        else swap(() -> --counter <= MIN);

    }

    private static void wait(Switcher object) {
        try {
            object.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void swap (Supplier<Boolean> limit) {
        if (limit.get()) ascending =! ascending;
    }

    private void out() {
        System.out.println(Thread.currentThread().getName() + counter);
    }
}
