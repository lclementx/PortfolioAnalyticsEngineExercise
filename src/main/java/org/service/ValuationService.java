package org.service;

import com.google.common.math.Stats;
import org.asset.*;
import org.asset.option.Option;
import org.portfolio.Portfolio;
import org.util.OptionPriceCalculator;
import org.util.PriceUtils;
import org.util.PrintUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ValuationService implements ISubscriber<List<Price>>{

    private final Portfolio portfolio;
    private double riskFreeRate = 0.2;
    private Map<Security,Valuation> valuation;

    private Map<Security,List<Price>> priceHistory;


    private int updateCount = 0;

    public ValuationService(Portfolio portfolio, IPublisher<List<Price>> priceSource) {
        this.portfolio = portfolio;
        this.valuation = new HashMap<>();
        this.priceHistory = new HashMap<>();
        priceSource.subscribe(Collections.singletonList(this));
    }

    public void setRiskFreeRate(double riskFreeRate) {
        this.riskFreeRate = riskFreeRate;
    }

    private void valuate(List<Price> prices) {
        Map<Security,Price> priceMap = prices.stream().collect(Collectors.toMap(Price::getSecurity,Function.identity()));
        Map<Security, Valuation> valueMap = new HashMap<>();
        for(Asset asset: portfolio.getAssetList()) {
            double assetPrice = Double.NEGATIVE_INFINITY;
            Security assetSecurity = asset.getSecurity();
            AssetType assetType = asset.getAssetType();
            switch(assetType) {
                case STOCK:
                    assetPrice = getStockPrice(assetSecurity, priceMap);
                    break;
                case OPTION:
                    assetPrice = getOptionPrice(asset, priceMap);
            }
            valueMap.put(assetSecurity, new Valuation(asset,new Price(assetSecurity,assetPrice)));
        }
        this.valuation = valueMap;
    }

    @Override
    public void update(Event<List<Price>> event) {
        updateCount += 1;
        List<Price> priceUpdates = event.getEvent();
        updatePriceHistory(priceUpdates);
        PrintUtils.printPriceUpdates(priceUpdates,updateCount);
        valuate(event.getEvent());
        PrintUtils.printValuation(this.portfolio,this.valuation);
    }

    private void updatePriceHistory(List<Price> prices) {
        for(Price price: prices) {
            List<Price> secPriceHistory = priceHistory.getOrDefault(price.getSecurity(), new ArrayList<>());
            secPriceHistory.add(price);
            priceHistory.put(price.getSecurity(),secPriceHistory);
        }
    }

    private double getStockPrice(Security assetSecurity, Map<Security,Price> priceMap) {
        double previousPrice = 0.0;
        if(valuation.containsKey(assetSecurity)) {
            previousPrice = valuation.get(assetSecurity).getPrice().getPrice();
        }
        Optional<Price> securityPriceObject = Optional.ofNullable(priceMap.get(assetSecurity));
        if (securityPriceObject.isPresent()) {
            previousPrice = securityPriceObject.get().getPrice();
        }
        return previousPrice;
    }

    private double getOptionPrice(Asset asset, Map<Security,Price> priceMap) {
        double volatility;
        Option option = (Option) asset;
        Security underlier = option.getOptionProperties().getUnderlier();
        List<Price> secPriceHistory = priceHistory.get(underlier);
        if(secPriceHistory.size() < 3) {
            volatility = 0.0;
        } else {
            System.out.println("Price History Size: " + secPriceHistory.size());
            List<Double> priceBySecondBuckets = PriceUtils.getPriceByTime(secPriceHistory);
            List<Double> returnsBySecondBuckets = PriceUtils.getPricePercentageDiff(priceBySecondBuckets);
            Stats priceStats = Stats.of(returnsBySecondBuckets);
            volatility = Math.sqrt(priceStats.sampleVariance() * 21_772_800.0);
            System.out.printf("Volatility : %f \n", volatility);
        }
        Price underlierPrice = priceMap.getOrDefault(underlier,secPriceHistory.get(secPriceHistory.size() - 1));
        LocalDateTime priceTime = underlierPrice.getTime();
        return OptionPriceCalculator.getPrice((Option) asset,underlierPrice.getPrice(), volatility, priceTime, riskFreeRate);
    }

}
