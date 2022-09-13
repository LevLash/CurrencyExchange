package com.currency.Exchange.Services;

import com.currency.Exchange.Utils.InMemoryDBUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class RequestsHistoryService {

    //SQL script that creates table requests_history
    private static final String CREATE_REQUESTS_HISTORY_SQL = """
            create table if not exists requests_history (\r
              id integer auto_increment not null primary key,\r
              request varchar not null,\r
              answer varchar not null\r
              );""";

    //SQL script that inserts new request and answer to table requests_history
    private static final String INSERT_REQUESTS_HISTORY_SQL =
            "INSERT INTO requests_history (request, answer) VALUES (?, ?);";

    //SQL script that reads all data from table requests_history
    private static final String READ_REQUESTS_HISTORY_SQL = "select * from requests_history";

    private static final Logger logger = LoggerFactory.getLogger(RequestsHistoryService.class);

    //creating table requests_history
    public static void createTable() {
        try (Connection connection = InMemoryDBUtils.getConnection();
             Statement statement = connection.createStatement();) {

            statement.execute(CREATE_REQUESTS_HISTORY_SQL);
            logger.info("Requests history table was successfully created");
        } catch (SQLException e) {
            InMemoryDBUtils.printSQLException(e);
        }
    }

    //inserting new request and answer to table requests_history
    public void put(String request, String answer) throws SQLException {
        try (Connection connection = InMemoryDBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REQUESTS_HISTORY_SQL)) {

            preparedStatement.setString(1, request);
            preparedStatement.setString(2, answer);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            InMemoryDBUtils.printSQLException(e);
        }
    }

    //reading all data from table requests_history as JSONObject
    public JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try (Connection connection = InMemoryDBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_REQUESTS_HISTORY_SQL);) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                jsonObject.put("" + rs.getInt("id"),
                        new JSONObject().put("request", rs.getString("request"))
                                .put("answer", rs.getString("answer")));
            }
        } catch (SQLException e) {
            InMemoryDBUtils.printSQLException(e);
        }
        return new JSONObject().put("data", jsonObject);
    }

}
