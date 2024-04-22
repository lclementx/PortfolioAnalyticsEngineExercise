package org.asset;

public class Valuation {

    private Asset asset;

    private Price price;

    public Valuation(Asset asset, Price price) {
        this.asset = asset;
        this.price = price;
    }

    public Asset getAsset() {
        return asset;
    }

    public Price getPrice() {
        return price;
    }

    public double getValue() {
        return this.asset.getQuantity() * this.price.getPrice();
    }
}
