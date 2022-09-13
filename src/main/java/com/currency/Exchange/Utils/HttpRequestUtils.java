package com.currency.Exchange.Utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

@Component
public class HttpRequestUtils {

    //Sends HTTP request with url
    public static String sendHTTPRequest(String url) throws Exception {

        HttpUriRequest request = new HttpGet(url);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        if ( httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return EntityUtils.toString(httpResponse.getEntity());
        } else {
            throw new Exception("Error in API Call");
        }
    }

    //Sends HTTP request with url and API key in header
    public static String sendHTTPRequest(String url, String apiKey) throws Exception {

        HttpUriRequest request = new HttpGet(url);
        request.addHeader("apikey", apiKey);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return EntityUtils.toString(httpResponse.getEntity());
        } else {
            throw new Exception("Error in API Call");
        }
    }
}

