package org.util;

import org.asset.Asset;
import org.asset.Security;
import org.asset.option.Option;
import org.asset.option.OptionProperties;
import org.asset.option.OptionTypeEnum;
import org.asset.stock.Stock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DBQuery {

    private static final String tickerQueryTemplate =
            "select ticker, name from Security, SecurityType \n" +
                    "where \n" +
                    "ticker in (%s) and \n" +
                    "securityType = value";

    private static final String optionPropertiesTemplate =
            "select strikePrice, maturityDate, type, underlier \n" +
                    "where \n" +
                    "ticker = %s";

    private Connection dbconn;

    public DBQuery(String dbConnection) {
        try {
            dbconn = DriverManager.getConnection(dbConnection);
        } catch(Exception e) {
            System.out.println("Failed to establish connection with database");
            e.printStackTrace();
        }
    }

    public List<Security> getSecurities(List<String> tickers) {
        List<Security> securitiesList = new ArrayList<>();
        String tickerQuery = String.format(tickerQueryTemplate,getStringList(tickers));
        try (Statement stmt = dbconn.createStatement()) {
            ResultSet rs = stmt.executeQuery(tickerQuery);
            while (rs.next()) {
                String tickerName = rs.getString("ticker");
                String securityType = rs.getString("name");
                if(securityType.equals("OPTION")){
                    securitiesList.add(getOption(tickerName));
                } else {
                    securitiesList.add(new Stock(tickerName));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return securitiesList;
    };

    private String getStringList(List<String> inputs) {
        List<String> quotedInputs = inputs.stream().map(x -> "'" + x + "'").collect(Collectors.toList());
        return String.join(",", quotedInputs);
    }

    private Option getOption(String ticker) {
        OptionProperties optionProperties = null;
        try (Statement stmt = dbconn.createStatement()) {
            String optionQuery = String.format(optionPropertiesTemplate,ticker);
            ResultSet rs = stmt.executeQuery(optionQuery);
            while (rs.first()) {
                double strikePrice = rs.getDouble("strikePrice");
                Date maturityDate = rs.getDate("maturityDate");
                String optionType = rs.getString("type");
                String underlier = rs.getString("underlier");
                OptionTypeEnum optionTypeEnum = optionType.equals("CALL") ? OptionTypeEnum.CALL : OptionTypeEnum.PUT;
                Stock stock = new Stock(underlier); //This can be adpated to query the db as well
                optionProperties = new OptionProperties(stock,optionTypeEnum,strikePrice,maturityDate.toLocalDate());
            }
            return new Option(ticker,optionProperties);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
