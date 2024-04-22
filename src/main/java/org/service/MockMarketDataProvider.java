package org.service;

import org.asset.Asset;
import org.asset.Price;
import org.asset.Security;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class MockMarketDataProvider implements IPublisher<List<Price>> {

    public static final String MU = "mu";
    public static final String SIGMA = "sigma";

    private Map<Security, Map<String,Double>> assetMap;
    private Map<Security,Price> priceMap;
    private final Random random = new Random();

    private List<ISubscriber<List<Price>>> subscribers;

    private LocalTime lastExecutionTime;

    public MockMarketDataProvider(List<Security> assetList) {
        this.assetMap = new HashMap<>();
        for(Security security: assetList) {
            Map<String,Double> stockProps = new HashMap<>();
            stockProps.put(MU,random.nextGaussian());
            stockProps.put(SIGMA,random.nextGaussian());
            assetMap.put(security,stockProps);
        }
        this.priceMap = new HashMap<>();
        this.subscribers = new ArrayList<>();
        this.lastExecutionTime = LocalTime.now();
    }

    public void generatePrice() {
        if(priceMap.isEmpty()) {
            for(Map.Entry<Security,Map<String,Double>> securityProps : assetMap.entrySet()) {
                Security security = securityProps.getKey();
                Price price = new Price(security, random.nextInt(1) + 130);
                priceMap.put(security,price);
            }
            notifySubscribers(new Event<>(new ArrayList<>(priceMap.values())));
        } else {
            List<Price> priceUpdates = new ArrayList<>();
            LocalTime executionTime = LocalTime.now();
            long timeDelta = lastExecutionTime.until(executionTime,ChronoUnit.SECONDS);
            for (Map.Entry<Security, Map<String, Double>> securityProps : assetMap.entrySet()) {
                if (random.nextBoolean()) {
                    Security security = securityProps.getKey();
                    Map<String,Double> securityPropsValue = securityProps.getValue();
                    double newPriceVal = discreteTimeGeometricBrownianMotion(
                            securityPropsValue.get(MU),
                            securityPropsValue.get(SIGMA),
                            timeDelta,
                            priceMap.get(security).getPrice()
                    );
                    Price newPrice = new Price(security,newPriceVal);
                    priceUpdates.add(newPrice);
                    priceMap.put(security,newPrice);
                }
            }
            notifySubscribers(new Event<>(priceUpdates));
        }
    }

    private double discreteTimeGeometricBrownianMotion(Double mu, Double sigma, long timeChange, Double price) {
        double deltaTime = timeChange / 7257600.0;
        double priceChange = Double.NEGATIVE_INFINITY;
        //Guarantees price to be positive
        while(priceChange + price < 0) {
            double epsilon = random.nextGaussian();
            priceChange = price * ( ( mu * deltaTime ) + (sigma * epsilon * Math.sqrt(deltaTime)) );
        }
        return price + priceChange;
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
