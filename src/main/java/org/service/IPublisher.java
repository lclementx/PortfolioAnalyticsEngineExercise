package org.service;

import java.util.List;

public interface IPublisher<T> {

    void subscribe(List<ISubscriber<T>> subscribers);

    void subscribe(ISubscriber<T> subscriber);

    void notifySubscribers(Event<T> event);

}
