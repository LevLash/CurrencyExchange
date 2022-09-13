package com.currency.Exchange.Utils;

import com.currency.Exchange.Controllers.CurrencyRequestController;
import com.currency.Exchange.Services.AvailableCurrenciesService;
import com.currency.Exchange.Utils.AvailableCurrencyUpdateUtils;
import com.currency.Exchange.Utils.HttpRequestUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

//Unit testing
@RunWith(SpringRunner.class)
class AvailableCurrencyUpdateUtilsTest {

    @Test
    void getAvailableCurrenciesTest() throws Exception {
        AvailableCurrencyUpdateUtils updateService = new AvailableCurrencyUpdateUtils();
        AvailableCurrenciesService.createTable();
        updateService.getAvailableCurrencies();

        String url = "https://api.currencyapi.com/v3/currencies?apikey="
                + CurrencyRequestController.FREE_CURRENCY_API_KEY;

        String apiResponse = HttpRequestUtils.sendHTTPRequest(url);
        String apiResponseArray = new JSONObject(apiResponse).getJSONObject("data").names().toString();
        String updatedCurrencies = AvailableCurrenciesService.getJSONArray().toString();

        assertEquals(apiResponseArray, updatedCurrencies);
    }
}