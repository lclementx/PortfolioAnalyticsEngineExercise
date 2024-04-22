package org.service;

public interface ISubscriber<T> {

    void update(Event<T> event);
}
