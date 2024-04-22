package org.asset.option;

import org.asset.Asset;
import org.asset.SecurityType;
import org.asset.Security;

public class Option extends Security {

    private OptionProperties optionProperties;

    public Option(String ticker, OptionProperties optionProperties) {
        super(ticker, SecurityType.OPTION);
        this.optionProperties = optionProperties;
    }

    public OptionProperties getOptionProperties() {
        return optionProperties;
    }

    public void setOptionProperties(OptionProperties optionProperties) {
        this.optionProperties = optionProperties;
    }

}
