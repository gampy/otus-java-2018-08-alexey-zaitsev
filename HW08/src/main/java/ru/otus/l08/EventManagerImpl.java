package ru.otus.l08;

import java.util.*;

public class EventManagerImpl implements EventManager{
    Map<Object, List<EventListener>> listeners = new HashMap<>();

    public EventManagerImpl(Object ... objects) {
        for (Object obj : objects) {
            this.listeners.put(obj, new ArrayList<>());
        }
    }

    @Override
    public void subscribe(Object obj, EventListener listener) {
        listeners.get(obj).add(listener);
    }

    @Override
    public void unsubscribe(Object obj, EventListener listener) {
        listeners.remove(obj);
    }

    @Override
    public void notify(Object obj, int id) {
        for (EventListener listener : listeners.get(obj)) {
            listener.update(obj, id);
        }
    }

}
