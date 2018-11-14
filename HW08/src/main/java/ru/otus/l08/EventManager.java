package ru.otus.l08;

public interface EventManager {

    void subscribe(Object obj, EventListener listener);

    void unsubscribe(Object obj, EventListener listener);

    void notify(Object obj, int id);

}
