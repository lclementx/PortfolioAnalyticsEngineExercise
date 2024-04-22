package org.service;

public class Event<T> {

    private final T eventData;

    public Event(T eventData) {
        this.eventData = eventData;
    }

    public T getEvent() {
        return eventData;
    }
}
