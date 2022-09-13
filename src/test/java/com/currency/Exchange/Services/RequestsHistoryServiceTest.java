package com.currency.Exchange.Services;

import com.currency.Exchange.Utils.InMemoryDBUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//Unit testing
class RequestsHistoryServiceTest {

    private RequestsHistoryService requestsHistoryService;

    private static final String CLEAR_TABLE_SQL =
            "drop table requests_history;";

    @BeforeEach
    void setUp(){
        requestsHistoryService = new RequestsHistoryService();
        try (Connection connection = InMemoryDBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_TABLE_SQL)) {

            Connection conn = InMemoryDBUtils.getConnection();
            ResultSet rset = conn.getMetaData()
                    .getTables(null, null, "REQUESTS_HISTORY", null);
            if (rset.next())
            {
                preparedStatement.executeUpdate();
            }

            RequestsHistoryService.createTable();

        } catch (SQLException e) {
            InMemoryDBUtils.printSQLException(e);
        }
    }

    @Test
    void createTableTest() throws SQLException {
        boolean tableExists = false;
        Connection conn = InMemoryDBUtils.getConnection();
        ResultSet rset = conn.getMetaData()
                .getTables(null, null, "REQUESTS_HISTORY", null);
        if (rset.next())
        {
            tableExists = true;
        }

        assertTrue(tableExists);
    }

    @Test
    void putAndGetJSONObjectTest() throws SQLException {
        String request = java.util.UUID.randomUUID().toString();
        String answer = java.util.UUID.randomUUID().toString();
        JSONObject oldJsonObject = requestsHistoryService.getJSONObject().getJSONObject("data");
        oldJsonObject.put("1", new JSONObject().put("request", request).put("answer", answer));
        requestsHistoryService.put(request, answer);
        JSONObject newJsonObject = requestsHistoryService.getJSONObject().getJSONObject("data");
        assertTrue(newJsonObject.similar(oldJsonObject));
    }
}