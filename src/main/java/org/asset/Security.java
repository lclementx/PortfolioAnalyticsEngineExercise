package org.asset;

import java.util.Objects;

public abstract class Security {
    private String ticker;

    private SecurityType securityType;

    public Security(String ticker, SecurityType securityType) {
        this.ticker = ticker;
        this.securityType = securityType;
    }
    public String getTicker() {
        return ticker;
    }

    public SecurityType getSecurityType() {
        return securityType;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Security)) return false;
        Security security = (Security) o;
        return Objects.equals(ticker, security.ticker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticker);
    }
}
