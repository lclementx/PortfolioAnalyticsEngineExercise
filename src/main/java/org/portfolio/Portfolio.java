package org.portfolio;

import org.asset.Asset;
import java.util.List;

public class Portfolio {

    private List<Asset> assetList;
    public Portfolio(List<Asset> assetList) {
        this.assetList = assetList;
    }

    public List<Asset> getAssetList() {
        return assetList;
    }
}
