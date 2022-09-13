package com.currency.Exchange.Utils;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

//Unit testing
class InMemoryDBUtilsTest {

    @Test
    void getConnection() throws SQLException {
        Connection connection = InMemoryDBUtils.getConnection();
        assertFalse(connection.isClosed());
        connection.close();
    }
}