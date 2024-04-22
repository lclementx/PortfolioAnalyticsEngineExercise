package org.asset.stock;

import org.asset.Security;
import org.asset.SecurityType;

public class Stock extends Security {

    public Stock(String ticker) {
        super(ticker, SecurityType.STOCK);
    }

}
