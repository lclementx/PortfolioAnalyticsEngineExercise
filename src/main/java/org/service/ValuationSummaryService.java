package org.service;

import org.asset.Price;

import java.util.List;

public class ValuationSummaryService {


    class PriceSubscriber implements ISubscriber<List<Price>> {
        @Override
        public void update(Event<List<Price>> event) {

        }
    }

    class ValuationSubscriber implements ISubscriber<List<Price>> {

        @Override
        public void update(Event<List<Price>> event) {

        }
    }
}
