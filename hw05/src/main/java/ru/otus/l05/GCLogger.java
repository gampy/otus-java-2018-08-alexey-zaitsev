package ru.otus.l05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.*;


public class GCLogger {
    private Logger logger = LoggerFactory.getLogger(GCLogger.class);

    Thread.UncaughtExceptionHandler handler = (thread, e) -> {
        String err = "\"" + e.getMessage() + "\" exception is thrown in \"" + thread.getName() + "\" thread. Application is terminated.";
        logger.error(err);
        System.exit(-1);()
    };

    private void stat(GC gc) {
        StringBuilder sb = new StringBuilder();
        sb.append("GS type: ").append(gc.getType())
            .append(",\tGS name: ").append(gc.getName())
            .append(",\tCount: ").append(gc.getCount())
            .append(",\tTime (ms): ").append(gc.getTime());
        logger.info(sb.toString());
    }


    public void collectMetrics() {

        Thread.currentThread().setUncaughtExceptionHandler(handler);

        List<GarbageCollectorMXBean> mxBeans = ManagementFactory.getGarbageCollectorMXBeans();

        for (GarbageCollectorMXBean mxBean : mxBeans) {

            GC gc = GC.get(mxBean);
            gc.setCount(mxBean.getCollectionCount());
            gc.setTime(mxBean.getCollectionTime());
            gc.startTimer(new TimerTask() {
                @Override
                public void run() {
                    stat(gc);
                }
            }, 60_000, 60_000);

        }
    }
}
