package com.currency.Exchange.Utils;

import com.currency.Exchange.Controllers.CurrencyRequestController;
import com.currency.Exchange.Services.AvailableCurrenciesService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AvailableCurrencyUpdateUtils {

    private final Logger logger = LoggerFactory.getLogger(AvailableCurrencyUpdateUtils.class);

    //Updates available currencies list every 24h
    @Scheduled(fixedDelay = 86400000)
    public void getAvailableCurrencies() throws Exception {

        String url = "https://api.currencyapi.com/v3/currencies?apikey="
                + CurrencyRequestController.FREE_CURRENCY_API_KEY;

        String response = HttpRequestUtils.sendHTTPRequest(url);

        AvailableCurrenciesService.clear();
        AvailableCurrenciesService.put(new JSONObject(response).getJSONObject("data").names());

        logger.info("The system received a list of available currencies and saved it to the database");
    }

}
