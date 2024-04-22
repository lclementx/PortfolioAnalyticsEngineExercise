package org.asset.option;

import org.asset.Asset;
import org.asset.AssetType;
import org.asset.Security;

public class Option extends Asset {

    private OptionProperties optionProperties;

    public Option(Security security, OptionProperties optionProperties) {
        super(security, AssetType.OPTION);
        this.optionProperties = optionProperties;
    }

    public Option(Security security, OptionProperties optionProperties, double qty) {
        this(security,optionProperties);
        this.setQuantity(qty);
    }

    public OptionProperties getOptionProperties() {
        return optionProperties;
    }

    public void setOptionProperties(OptionProperties optionProperties) {
        this.optionProperties = optionProperties;
    }

}
