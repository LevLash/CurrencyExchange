package com.currency.Exchange.Services;

import com.currency.Exchange.Utils.InMemoryDBUtils;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//Unit testing
class AvailableCurrenciesServiceTest {

    @BeforeAll
    static void setUp(){
        AvailableCurrenciesService.createTable();
    }

    @Test
    void createTableTest() throws SQLException {
        boolean tableExists = false;
        Connection conn = InMemoryDBUtils.getConnection();
        ResultSet rset = conn.getMetaData()
                .getTables(null, null, "AVAILABLE_CURRENCIES", null);
        if (rset.next())
        {
            tableExists = true;
        }
        assertTrue(tableExists);
    }

    @Test
    void putTest() throws SQLException {
        AvailableCurrenciesService.put("ABC");
        JSONArray array = AvailableCurrenciesService.getJSONArray();
        assertEquals(array.getString(array.length() - 1), "ABC");
    }

    @Test
    void putJSONArrayTest() throws SQLException {
        JSONArray previousArray = AvailableCurrenciesService.getJSONArray();
        AvailableCurrenciesService.put(new JSONArray().put("ABCD").put("FFF"));
        JSONArray newArray = AvailableCurrenciesService.getJSONArray();
        previousArray.put("ABCD").put("FFF");
        assertTrue(previousArray.similar(newArray));
    }

    @Test
    void clearTest() {
        AvailableCurrenciesService.clear();
        assertTrue(AvailableCurrenciesService.getJSONArray().isEmpty());
    }

    @Test
    void getJSONArrayTest() throws SQLException {
        AvailableCurrenciesService.clear();
        JSONArray newArray = new JSONArray().put("ABCD").put("FFF");
        AvailableCurrenciesService.put(newArray);

        assertTrue(AvailableCurrenciesService.getJSONArray().similar(newArray));
    }
}