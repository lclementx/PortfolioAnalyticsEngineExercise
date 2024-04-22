package org;

import org.asset.Asset;
import org.asset.Security;
import org.asset.SecurityType;
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
                new Asset(new Stock("GOOGL")),
                new Asset(new Stock("NVDA"))
        );

        Portfolio portfolio = new Portfolio(assets);
        MockMarketDataProvider mockMarketDataProvider = new MockMarketDataProvider(
                assets.stream().map(Asset::getSecurity).collect(Collectors.toList()));
        ValuationService valuationService = new ValuationService(portfolio,mockMarketDataProvider);

    }
}
