package org.asset;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Price {

    private Security security;
    private double price;

    private LocalDateTime time;

    public Price(Security security, double price) {
        this.security = security;
        this.price = price;
        this.time = LocalDateTime.now(); //You can set this based on price source as well.
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Price{" +
                "security=" + security.getTicker() +
                ", price=" + price +
                ", time=" + time +
                '}';
    }
}
