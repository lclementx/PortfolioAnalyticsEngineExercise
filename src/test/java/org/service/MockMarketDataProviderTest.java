package org.service;

import org.asset.Security;
import org.asset.stock.Stock;
import org.junit.jupiter.api.Test;
import org.util.PrintSubscriber;

import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


class MockMarketDataProviderTest {

    @Test
    void testPriceGeneration() throws InterruptedException {
        Stock stockA = new Stock(new Security("GOOGL", "GOOGLE"));
        MockMarketDataProvider mockMarketDataProvider = new MockMarketDataProvider(Collections.singletonList(stockA.getSecurity()));
        PrintSubscriber printSubscriber = new PrintSubscriber();
        mockMarketDataProvider.subscribe(Collections.singletonList(printSubscriber));
        ScheduledExecutorService executors = Executors.newScheduledThreadPool(1);
        executors.scheduleAtFixedRate(mockMarketDataProvider::generatePrice,0,1,TimeUnit.SECONDS);
        Thread.sleep(20000);
    }

}