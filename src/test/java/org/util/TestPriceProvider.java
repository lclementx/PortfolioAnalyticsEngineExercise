package org.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.asset.Price;
import org.asset.Security;
import org.service.Event;
import org.service.IPublisher;
import org.service.ISubscriber;

public class TestPriceProvider implements IPublisher<List<Price>> {

    private List<Security> securities;
    private List<ISubscriber<List<Price>>> subscribers;

    private int updateCount = 0;
    public TestPriceProvider(List<Security> securities) {
        this.securities = securities;
        this.subscribers = new ArrayList<>();
    }

    public void generatePrice() {
        updateCount += 1;
        List<Price> prices =  securities.stream().map(x ->new Price(x, 100 + updateCount))
                .collect(Collectors.toList());
        notifySubscribers(new Event<>(prices));
    }
    @Override
    public void subscribe(List<ISubscriber<List<Price>>> subscribers) {
        this.subscribers.addAll(subscribers);
    }

    @Override
    public void subscribe(ISubscriber<List<Price>> subscriber) {
        this.subscribers.add(subscriber);
    }

    @Override
    public void notifySubscribers(Event<List<Price>> event) {
        for(ISubscriber<List<Price>> sub : subscribers) {
            sub.update(event);
        }
    }
}
