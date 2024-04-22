package org.asset.stock;

import org.asset.Asset;
import org.asset.AssetType;
import org.asset.Security;

public class Stock extends Asset {

    public Stock(Security security) {
        super(security, AssetType.STOCK);
    }

    public Stock(Security security, double qty) {
        super(security, AssetType.STOCK);
        this.setQuantity(qty);
    }

}
