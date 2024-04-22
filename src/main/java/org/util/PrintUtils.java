package org.util;

import org.asset.Asset;
import org.asset.Price;
import org.asset.Security;
import org.asset.Valuation;
import org.portfolio.Portfolio;

import java.util.List;
import java.util.Map;

public class PrintUtils {

    private PrintUtils(){}

    public static void printPriceUpdates(List<Price> prices, int updateCount) {
        System.out.println("## " + updateCount + " Market Update");
        for(Price price: prices) {
            System.out.printf("%s changed to %f \n", price.getSecurity().getTicker(), price.getPrice());
        }
        System.out.println();
    }

    public static void printValuation(Portfolio portfolio, Map<Security, Valuation> valuationMap) {
        double totalPortfolioValue = 0;
        String divider = new String(new char[93]).replace('\0', '-');
        System.out.println("## Portfolio");
        System.out.printf(divider + "\n");
        System.out.printf("| %-32s | %8s | %8s | %32s |%n", "Symbol", "Price", "Qty", "Value");
        System.out.printf(divider + "\n");
        List<Asset> assets = portfolio.getAssetList();
        for(Asset asset: assets) {
            Security security = asset.getSecurity();
            Valuation value = valuationMap.get(security);
            totalPortfolioValue += value.getValue();
            System.out.printf("| %-32s | %8s | %8s | %32s |%n",
                    security.getTicker(),
                    String.format("%.2f",value.getPrice().getPrice()),
                    value.getAsset().getQuantity(),
                    String.format("%.2f",value.getValue()));

        }
        System.out.printf(divider + "\n");
        System.out.printf("Total Portfolio Value: %f \n" , totalPortfolioValue);
        System.out.println();
    }
}
