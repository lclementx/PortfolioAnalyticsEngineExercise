package org.asset.option;

import org.asset.Asset;
import org.asset.Security;

import java.time.LocalDate;
import java.util.Date;

public class OptionProperties {

    private Security underlier;
    OptionTypeEnum optionTypeEnum;
    private double strikePrice;
    private LocalDate maturityDate;

    public OptionProperties(Security underlier, OptionTypeEnum optionTypeEnum, double strikePrice, LocalDate maturityDate) {
        this.underlier = underlier;
        this.optionTypeEnum = optionTypeEnum;
        this.strikePrice = strikePrice;
        this.maturityDate = maturityDate;
    }

    public Security getUnderlier() {
        return underlier;
    }

    public OptionTypeEnum getOptionTypeEnum() {
        return optionTypeEnum;
    }

    public double getStrikePrice() {
        return strikePrice;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }
}
