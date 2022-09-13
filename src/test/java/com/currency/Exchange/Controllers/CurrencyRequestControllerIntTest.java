package com.currency.Exchange.Controllers;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.hamcrest.MatcherAssert;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

//API testing
class CurrencyRequestControllerIntTest {

    @Test
    void getExchangeRatesTest() throws IOException {

        // Testing the status code 400 (BAD_REQUEST)
        String sell = java.util.UUID.randomUUID().toString();
        String buy = java.util.UUID.randomUUID().toString();
        HttpUriRequest request = new HttpGet( "http://localhost:8080/latest?sell=" + sell + "&buy=" + buy);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // 400 (BAD_REQUEST) if given currencies does not exists
        MatcherAssert.assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_BAD_REQUEST));

        // Testing the status code 200 (OK)
        request = new HttpGet( "http://localhost:8080/latest?sell=USD&buy=EUR");
        httpResponse = HttpClientBuilder.create().build().execute(request);

        // 200 (OK) if /latest controller is working and the fields were filled correctly
        MatcherAssert.assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));

        // Testing the Media Type
        // Should return data in "application/json" type
        String mimeType = ContentType.parse(httpResponse.getEntity().getContentType().getValue()).getMimeType();
        assertEquals( "application/json", mimeType );

        // Testing the JSON Payload
        // Should return JSON object with key - "data"
        String json = EntityUtils.toString(httpResponse.getEntity());
        JSONObject jsonObject = new JSONObject(json);
        MatcherAssert.assertThat(jsonObject.has("data"),equalTo(true));

    }

    @Test
    void getAvailableCurrenciesTest() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/available");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Testing the status code
        // 200 (OK) if /available controller is working
        MatcherAssert.assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));

        // Testing the Media Type
        // Should return data in "application/json" type
        String mimeType = ContentType.parse(httpResponse.getEntity().getContentType().getValue()).getMimeType();
        assertEquals("application/json", mimeType);

        // Testing the JSON Payload
        // Should return JSON object with key - "Available currencies"
        String json = EntityUtils.toString(httpResponse.getEntity());
        JSONObject jsonObject = new JSONObject(json);
        MatcherAssert.assertThat(jsonObject.has("Available currencies"),equalTo(true));
    }

    @Test
    void getHistoryRateTest() throws IOException {

        // Testing the status code 400 (BAD_REQUEST)
        String sell = java.util.UUID.randomUUID().toString();
        String buy = java.util.UUID.randomUUID().toString();
        String service = java.util.UUID.randomUUID().toString();
        String date = java.util.UUID.randomUUID().toString();
        HttpUriRequest request = new HttpGet(
                "http://localhost:8080/history?sell=" + sell + "&buy=" + buy + "&service=" + service + "&date=" + date);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // 400 (BAD_REQUEST) if given currencies does not exists
        MatcherAssert.assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_BAD_REQUEST));

        // Testing the status code 200 (OK)
        request = new HttpGet(
                "http://localhost:8080/history?sell=USD&buy=EUR&service=freecurrencyapi&date=2021-12-21");
        httpResponse = HttpClientBuilder.create().build().execute(request);

        // 200 (OK) if /history controller is working and the fields were filled correctly
        MatcherAssert.assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));

        // Testing the Media Type
        // Should return data in "application/json" type
        String mimeType = ContentType.parse(httpResponse.getEntity().getContentType().getValue()).getMimeType();
        assertEquals( "application/json", mimeType );

        // Testing the JSON Payload
        // Should return JSON object with key - "data" if service is "freecurrencyapi"
        String json = EntityUtils.toString(httpResponse.getEntity());
        JSONObject jsonObject = new JSONObject(json);
        MatcherAssert.assertThat(jsonObject.has("data"),equalTo(true));

        // Should return JSON object with key - "rates" if service is "exchangeratesapi"
        request = new HttpGet(
                "http://localhost:8080/history?sell=USD&buy=EUR&service=exchangeratesapi&date=2021-12-21");
        httpResponse = HttpClientBuilder.create().build().execute( request );

        json = EntityUtils.toString(httpResponse.getEntity());
        jsonObject = new JSONObject(json);
        MatcherAssert.assertThat(jsonObject.has("rates"),equalTo(true));


    }
}