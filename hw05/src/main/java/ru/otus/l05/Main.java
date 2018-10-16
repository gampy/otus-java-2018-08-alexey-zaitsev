package ru.otus.l05;

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class Main {

    public static void main(String[] args) throws InterruptedException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, MalformedObjectNameException, IOException {

        // VM:
        // -Xms512m -Xmx512m -XX:+UseSerialGC
        // -Xms512m -Xmx512m -XX:+UseParallelOldGC
        // -Xms512m -Xmx512m -XX:+UseConcMarkSweepGC
        // -Xms512m -Xmx512m -XX:+UseG1GC


        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=SomeApp");
        SomeApp app = new SomeApp();

        mbs.registerMBean(app, name);

        app.setSize(1_024);
        app.run();
    }

}
