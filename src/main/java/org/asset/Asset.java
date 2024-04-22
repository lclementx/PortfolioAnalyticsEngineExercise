package org.asset;

import java.util.Objects;

public class Asset{

    private Security security;

    private double quantity;

    public Asset(Security security) {
        this.security = security;
    }

    public Asset(Security security, double qty) {
        this.security = security;
        this.quantity = qty;
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
