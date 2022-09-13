package com.currency.Exchange.Utils;


import com.currency.Exchange.Services.RequestsHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Component
public class InMemoryDBUtils {

    private static final Logger logger = LoggerFactory.getLogger(RequestsHistoryService.class);

    //In-Memory db url
    private static final String jdbcURL = "jdbc:h2:mem:exchangeDB;DB_CLOSE_DELAY=-1";

    //In-Memory db login and password
    private static final String jdbcUsername = "admin";
    private static final String jdbcPassword = "admin";

    //Connection to In-Memory db
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //Prints In-Memory db error info
    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                logger.error("SQLState: " + ((SQLException) e).getSQLState());
                logger.error("Error Code: " + ((SQLException) e).getErrorCode());
                logger.error("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    logger.error("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
