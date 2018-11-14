package ru.otus.l08;

import java.util.*;

public class Department {
    static final private List<ATMimpl> ATMpanel = new ArrayList<>();
    static private EventManager mngr = new EventManagerImpl(Department.class);

    private Department() {}

    public static void addATMs(ATMimpl ... ATMs) {
        //ATMpanel.addAll(Arrays.asList(ATMs));
        for (ATMimpl instance : ATMs) {
            ATMpanel.add(instance);
            mngr.subscribe(Department.class, instance);
        }
    }

    public static List<ATMimpl> getATMs() {
        return ATMpanel;
    }

    public static ATMimpl getATM(int i) {
        return ATMpanel.get(i);
    }

    public static void reset() {
        mngr.notify(Department.class,200);
    }

}
