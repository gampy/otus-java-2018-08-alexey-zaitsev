package ru.otus.l05;

import java.lang.management.GarbageCollectorMXBean;
import java.util.Timer;
import java.util.TimerTask;

public enum GC {
    One("Copy", "Young"),
    Two("PS Scavenge", "Young"),
    Three("ParNew", "Young"),
    Four("G1 Young Generation", "Young"),
    Five("MarkSweepCompact", "Old"),
    Six("PS MarkSweep", "Old"),
    Seven("ConcurrentMarkSweep", "Old"),
    Eight("G1 Old Generation", "Old"),
    ;

    private String name;
    private String type;
    private long count;
    private long time;
    private boolean isTimer = false;

    GC(String name, String type) {
        this.name = name;
        this.type = type;
    }

    static GC get(GarbageCollectorMXBean gc) {
        for (GC c : GC.values() ) {
            if (c.name.equals(gc.getName())) return c;
        }
        return null;
    }

    String getName() {
        return this.name;
    }

    String getType() {
        return this.type;
    }

    void setCount (long count) {
        this.count = count;
    }

    long getCount() {
        return this.count;
    }

    void setTime (long time) {
        this.time = time;
    }

    long getTime() {
        return this.time;
    }


    void startTimer(TimerTask task, long delay, long period) {
        if (!isTimer) {
            System.out.println("\"" + this.name + "\" collector's timer has started. Statistics data is collected each " + period/1000 + " secs.");
            new Timer(this.name).scheduleAtFixedRate(task, delay, period);
            isTimer = true;
        }
    }

}
