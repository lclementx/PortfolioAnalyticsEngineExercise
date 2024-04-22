package org.util;


import org.asset.option.Option;
import org.asset.option.OptionProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class OptionPriceCalculator {

    private OptionPriceCalculator(){}

    public static double getPrice(Option option, double underlierPrice, double volatility, LocalDateTime currentTime, double riskFreeRate) {
        OptionProperties props = option.getOptionProperties();
        LocalDate maturityDate = props.getMaturityDate();

        //assume 16:00 is the end of the trading day.
        long timeToMaturitySeconds = currentTime.until(maturityDate.atTime(16,0), ChronoUnit.SECONDS);
        double timeToMaturity = timeToMaturitySeconds / (60.0 * 60.0 * 24.0 * 365.0);

        double d1 = calculateD1(underlierPrice, props.getStrikePrice(), volatility, riskFreeRate, timeToMaturity);
        double d2 = calculateD2(d1, volatility, timeToMaturity);
        double optionPrice = 0.0;
        switch(props.getOptionTypeEnum()) {
            case CALL:
                optionPrice = callPrice(d1,d2,underlierPrice,props.getStrikePrice(),riskFreeRate,timeToMaturity); break;
            case PUT:
                optionPrice = putPrice(d1,d2,underlierPrice,props.getStrikePrice(),riskFreeRate,timeToMaturity); break;
        }
        return optionPrice;
    }

    private static double standardNormalCDF(double x) {
        if(x >= 8.0) return 1.0;
        if(x <= -8.0) return 0.0;
        //https://www.hrpub.org/download/20181230/MS1-13412146.pdf - simple approximation for Standard Normal CDF
        double a = 0.647 - (0.021 * x);
        return 0.5 * (1 + Math.sqrt(1 - Math.exp(-a * Math.pow(x,2.0))));
    }

    private static double calculateD1(double underlierPrice, double strikePrice, double volatility, double riskFreeRate, double timeToMaturity) {
        if(volatility == 0.0) return Double.POSITIVE_INFINITY;
        double numerator = Math.log(underlierPrice/strikePrice) +
                ((riskFreeRate + Math.pow(volatility,2.0)/2) * timeToMaturity);
        double denominator = volatility * Math.sqrt(timeToMaturity);
        return numerator / denominator;
    }

    private static double calculateD2(double d1, double volatility, double timeToMaturity) {
        return d1 - (volatility * Math.sqrt(timeToMaturity));
    }

    private static double callPrice(double d1, double d2, double underlierPrice, double strikePrice, double riskFreeRate, double timeToMaturity) {
        return (underlierPrice * standardNormalCDF(d1)) -
                (strikePrice * (Math.exp(-riskFreeRate * timeToMaturity)) * standardNormalCDF(d2));
    }

    private static double putPrice(double d1, double d2, double underlierPrice, double strikePrice, double riskFreeRate, double timeToMaturity) {
        return strikePrice * (Math.exp(-riskFreeRate * timeToMaturity)) * standardNormalCDF(-d2) -
                underlierPrice * standardNormalCDF(-d1);

    }



}
