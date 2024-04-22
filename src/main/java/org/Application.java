package org;

import org.asset.Asset;
import org.asset.Security;
import org.asset.stock.Stock;
import org.portfolio.Portfolio;
import org.service.MockMarketDataProvider;
import org.service.ValuationService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) {
        //Get the list of assets from a DB
        List<Asset> assets = Arrays.asList(
                new Stock(new Security("GOOGL","GOOGL"),100),
                new Stock(new Security("AAPL","AAPL"),200),
                new Stock(new Security("NVDA","NVDA"),320)
        );

        Portfolio portfolio = new Portfolio(assets);
        MockMarketDataProvider mockMarketDataProvider = new MockMarketDataProvider(
                assets.stream().map(Asset::getSecurity).collect(Collectors.toList()));
        ValuationService valuationService = new ValuationService(portfolio,mockMarketDataProvider);

    }
}
