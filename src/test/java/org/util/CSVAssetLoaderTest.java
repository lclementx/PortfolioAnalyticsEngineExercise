package org.util;

import org.asset.Asset;
import org.asset.Security;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CSVAssetLoaderTest {

    @Test
    void loadCSV() throws SQLException {
        String path = "/Users/clemmie/Documents/JAVA/PortfolioAnalyticsEngineExercise/sample.csv";
        String dbConn = "jdbc:sqlite:/Users/clemmie/Documents/JAVA/PortfolioAnalyticsEngineExercise/db/asset.db";
        Map<String,Double> securityQtyMap = CSVAssetLoader.getAssetQuantities(path);
        DBQuery dbQuery = new DBQuery(dbConn);
        List<Security> securityList = dbQuery.getSecurities(new ArrayList<>(securityQtyMap.keySet()));
        System.out.println(securityList);
    }

}