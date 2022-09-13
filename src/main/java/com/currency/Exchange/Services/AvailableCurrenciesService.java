package com.currency.Exchange.Services;

import com.currency.Exchange.Utils.InMemoryDBUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class AvailableCurrenciesService {

    //SQL script that creates table available_currencies
    private static final String CREATE_AVAILABLE_CURRENCIES_SQL = """
            create table if not exists available_currencies (\r
              id integer auto_increment not null primary key,\r
              currency varchar(7) not null\r
              );""";

    //SQL script that inserts new currency to table available_currencies
    private static final String INSERT_AVAILABLE_CURRENCIES_SQL =
            "INSERT INTO available_currencies (currency) VALUES (?);";

    //SQL script that deletes all data from table available_currencies
    private static final String CLEAR_TABLE_SQL =
            "delete from available_currencies;";

    //SQL script that reads all data from table available_currencies
    private static final String READ_AVAILABLE_CURRENCIES_SQL = "select currency from available_currencies;";

    private static final Logger logger = LoggerFactory.getLogger(AvailableCurrenciesService.class);

    //creating table available_currencies
    public static void createTable() {
        try (Connection connection = InMemoryDBUtils.getConnection();
             Statement statement = connection.createStatement();) {

            statement.execute(CREATE_AVAILABLE_CURRENCIES_SQL);
            logger.info("Available currencies table was successfully created");
        } catch (SQLException e) {
            InMemoryDBUtils.printSQLException(e);
        }
    }

    //inserting new currency to table available_currencies from String
    public static void put(String currency) throws SQLException {
        try (Connection connection = InMemoryDBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AVAILABLE_CURRENCIES_SQL)) {
            preparedStatement.setString(1, currency);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            InMemoryDBUtils.printSQLException(e);
        }
    }

    //inserting new currency to table available_currencies from JSONArray
    public static void put(JSONArray currencies) throws SQLException {
        for (var currency : currencies) {
            put(currency.toString());
        }
    }

    //deleting all data from table available_currencies
    public static void clear() {
        try (Connection connection = InMemoryDBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_TABLE_SQL)) {

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            InMemoryDBUtils.printSQLException(e);
        }
    }

    //reading all data from table available_currencies as JSONArray
    public static JSONArray getJSONArray() {
        JSONArray array = new JSONArray();
        try (Connection connection = InMemoryDBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_AVAILABLE_CURRENCIES_SQL);) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                array.put(rs.getString("currency"));
            }
        } catch (SQLException e) {
            InMemoryDBUtils.printSQLException(e);
        }
        return array;
    }

}
