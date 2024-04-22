package org.asset;

import java.util.Objects;

public abstract class Asset{

    private Security security;

    private double quantity;

    private final AssetType assetType;

    public Asset(Security security, AssetType assetType) {
        this.security = security;
        this.assetType = assetType;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Security getSecurity() {
        return security;
    }

    public void addQuantity(double delta) {
        this.quantity += delta;
    }


    public AssetType getAssetType() {
        return assetType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Asset)) return false;
        Asset asset = (Asset) o;
        return Objects.equals(security, asset.security);
    }

    @Override
    public int hashCode() {
        return Objects.hash(security);
    }
}
