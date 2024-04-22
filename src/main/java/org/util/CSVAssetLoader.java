package org.util;

import org.asset.Asset;
import org.asset.Security;
import org.asset.option.Option;
import org.asset.option.OptionProperties;
import org.asset.option.OptionTypeEnum;
import org.asset.stock.Stock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CSVAssetLoader {

    public static Map<String,Double> getAssetQuantities(String csvPath) {
        Map<String,Double> tickerQtyMap = new HashMap<>();
        try {
            List<String> file = Files.readAllLines(Paths.get(csvPath));
            //dismiss the first line cause its the header
            file.remove(0);
            for(String line : file) {
                String[] entry = line.split(",");
                String ticker = entry[0];
                double qty = Double.parseDouble(entry[1]);
                tickerQtyMap.put(ticker,qty);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tickerQtyMap;
    }


}
