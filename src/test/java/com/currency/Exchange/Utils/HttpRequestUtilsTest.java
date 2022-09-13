package com.currency.Exchange.Utils;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

//Unit testing
class HttpRequestUtilsTest {

    private final Logger logger = LoggerFactory.getLogger(HttpRequestUtilsTest.class);

    @Test
    void sendHTTPRequest() throws Exception {
        String url = "https://httpbin.org/get";
        String response = HttpRequestUtils.sendHTTPRequest(url);
        logger.debug(response);
        response = new JSONObject(response).getString("url");
        assertEquals(url, response);

    }

    @Test
    void testSendHTTPRequest() throws Exception {
        String url = "https://httpbin.org/get";
        String apikey = java.util.UUID.randomUUID().toString();
        String response = HttpRequestUtils.sendHTTPRequest(url, apikey);
        logger.debug(response);
        JSONObject responseObject = new JSONObject(response);

        response = responseObject.getString("url");
        assertEquals(url, response);

        response = responseObject.getJSONObject("headers").getString("Apikey");
        assertEquals(apikey, response);
    }
}