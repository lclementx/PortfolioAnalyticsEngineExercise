package org;

import org.asset.Asset;
import org.asset.Security;
import org.asset.SecurityType;
import org.asset.stock.Stock;
import org.portfolio.Portfolio;
import org.service.MockMarketDataProvider;
import org.service.ValuationService;
import org.util.CSVAssetLoader;
import org.util.DBQuery;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.Collectors;

public class Application {

    private static String defaultCSVPath = "/Users/clemmie/Documents/JAVA/PortfolioAnalyticsEngineExercise/sample.csv";
    private static String dbUrl = "jdbc:sqlite:/Users/clemmie/Documents/JAVA/PortfolioAnalyticsEngineExercise/db/asset.db";
    public static void main(String[] args) {
        Map<String,Double> securityQtyMap = CSVAssetLoader.getAssetQuantities(defaultCSVPath);
        DBQuery dbQuery = new DBQuery(dbUrl);
        List<Security> securityList = dbQuery.getSecurities(new ArrayList<>(securityQtyMap.keySet()));
        List<Asset> assets = new ArrayList<>();
        for(Security security : securityList) {
            assets.add(new Asset(security,securityQtyMap.get(security.getTicker())));
        }
        Portfolio portfolio = new Portfolio(assets);
        MockMarketDataProvider mockMarketDataProvider = new MockMarketDataProvider(securityList);
        new ValuationService(portfolio,mockMarketDataProvider);
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(() -> {
            Random random = new Random();
            while(true){
                mockMarketDataProvider.generatePrice();
                try {
                    Thread.sleep((random.nextInt(15) + 5) * 100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });



    }
}
