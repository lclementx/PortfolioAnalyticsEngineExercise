package org.util;

import org.asset.Price;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class PriceUtils {

    private PriceUtils() {}

    public static List<Double> getPriceByTime(List<Price> prices) {
        List<Double> pricesBySeconds = new ArrayList<>();
        Map<Long,List<Price>> priceByTimeBucket = prices.stream()
                .collect(groupingBy(x -> x.getTime().toEpochSecond(ZoneOffset.UTC)));
        long startTime = prices.get(0).getTime().toEpochSecond(ZoneOffset.UTC);
        long endTime = prices.get(prices.size() - 1).getTime().toEpochSecond(ZoneOffset.UTC);
        double previousTimePrice = prices.get(0).getPrice();
        for(long t = startTime; t <= endTime; t++) {
            List<Price> pricesWithinTime = priceByTimeBucket.getOrDefault(t, Collections.emptyList());
            if(!pricesWithinTime.isEmpty()) {
                double timePrice = pricesWithinTime.stream().mapToDouble(Price::getPrice).sum() / pricesWithinTime.size();
                previousTimePrice = timePrice;
                pricesBySeconds.add(timePrice);
            } else {
                pricesBySeconds.add(previousTimePrice);
            }
        }
        return pricesBySeconds;
    }

    public static List<Double> getPricePercentageDiff(List<Double> values) {
        List<Double> diffs = new ArrayList<>();
        double prevValue = values.get(0);
        for(int i = 1; i < values.size();i++) {
            double currentValue = values.get(i);
            diffs.add((currentValue - prevValue)/prevValue);
            prevValue = currentValue;
        }
        return diffs;
    }
}
