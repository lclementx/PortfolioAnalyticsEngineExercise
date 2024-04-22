package org.service;

import org.asset.Asset;
import org.asset.Security;
import org.asset.option.Option;
import org.asset.option.OptionProperties;
import org.asset.option.OptionTypeEnum;
import org.asset.stock.Stock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.portfolio.Portfolio;
import org.util.PrintSubscriber;
import org.util.TestPriceProvider;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ValuationServiceTest {

    @BeforeAll
    static void setup() {

    }

    @Test
    void testValuationService() throws InterruptedException {
        Security security = new Security("AAPL","AAPL");
        Security optionSecurity = new Security("AAPL-DEC-2024-123-C","AAPL-DEC-2024-123-C");
        OptionProperties optionProperties = new OptionProperties(security, OptionTypeEnum.CALL, 123, LocalDate.of(2024,12,31));
        List<Asset> assets = Arrays.asList(
                new Option(optionSecurity,optionProperties,1000),
                new Stock(security, 50)
        );
        Portfolio portfolio = new Portfolio(assets);
        MockMarketDataProvider testPriceProvider = new MockMarketDataProvider(Collections.singletonList(security));
        ValuationService vs = new ValuationService(portfolio, testPriceProvider);
        ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
        es.scheduleAtFixedRate(() -> {
            try {
                testPriceProvider.generatePrice();
            } catch(Exception e) {
                e.printStackTrace();
            }}
                ,0,1, TimeUnit.SECONDS);
        Thread.sleep(20000);
    }

    @Test
    void sandbox() {
        Random random = new Random();
        for(int i = 0; i < 100; i++) {
            System.out.println(random.nextGaussian());
        }
    }
}