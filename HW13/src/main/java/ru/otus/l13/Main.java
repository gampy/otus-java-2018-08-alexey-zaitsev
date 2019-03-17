package ru.otus.l13;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Switcher switcher = new Switcher();

        new Thread(() -> {
            Thread.currentThread().setName("[1]> ");
            switcher.run(Thread.currentThread(), false);
        }).start();

        new Thread(() -> {
            Thread.currentThread().setName("[2]> ");
            switcher.run(Thread.currentThread(), true);
        }).start();

    }



}
