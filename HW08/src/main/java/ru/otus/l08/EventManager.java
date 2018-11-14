package ru.otus.l08;

public interface EventManager {

    public void subscribe(Object obj, EventListener listener);

    public void unsubscribe(Object obj, EventListener listener);

    public void notify(Object obj, int id);

}
