package com.currency.Exchange.Controllers;

import com.currency.Exchange.Services.AvailableCurrenciesService;
import com.currency.Exchange.Utils.HttpRequestUtils;
import com.currency.Exchange.Services.RequestsHistoryService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.sql.SQLException;

@RestController
public class CurrencyRequestController {

    //Freecurrency API key
    public static final String FREE_CURRENCY_API_KEY = "h83woDtJhKNhab3UTVOgrovb8PhJKSseHMMyCrar";

    //Exchangerates API key
    public static final String EXCHANGE_RATES_API_KEY = "n2pbKbPml6w7OdLyllzIROHOMp2hc8IJ";

    private final Logger logger = LoggerFactory.getLogger(CurrencyRequestController.class);

    @Autowired
    private RequestsHistoryService requestsHistoryService;

    //Get exchange rates for currencies by parameters: sell/buy currencies
    @RequestMapping(value = "/latest", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<String> getExchangeRates(HttpServletRequest request,
                                                   @PathParam("sell") String sell,
                                                   @PathParam("buy") String buy) {
        String response;

        try {
            if (sell == null) {
                sell = "USD";
            }
            String url = "https://api.currencyapi.com/v3/latest?apikey=" +
                    FREE_CURRENCY_API_KEY + "&base_currency=" + sell;
            if (buy != null) {
                url = url.concat("&currencies=" + buy);
            }

            response = HttpRequestUtils.sendHTTPRequest(url);
            response = new JSONObject(response).toString();

            requestsHistoryService
                    .put(request.getRequestURL().toString() + "?" + request.getQueryString(), response);

            logger.info("The system has processed a request for a exchange rates, all data saved to db");

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (SQLException e) {
            logger.error("Adding data to db ended with an error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //get available currency codes
    @RequestMapping(value = "/available", produces="application/json")
    public ResponseEntity<String> getAvailableCurrencies(HttpServletRequest request) {

        try {
            String result = new JSONObject().put("Available currencies", AvailableCurrenciesService.getJSONArray()).toString();

            requestsHistoryService.put(request.getRequestURL().toString(), result);

            logger
                    .info("The system has processed a request for a list of all available currencies, all data saved to db");

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (SQLException e) {
            logger.error("Adding data to db ended with an error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    //get exchange rates history by parameters: sell/buy currencies, 3d party system identifier, date
    @RequestMapping(value = "/history", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<String> getHistoryRate(HttpServletRequest request,
                                                 @PathParam("sell") String sell,
                                                 @PathParam("buy") String buy,
                                                 @PathParam("service") String service,
                                                 @PathParam("date") String date) {
        String response;
        try {
            if (sell == null) {
                sell = "USD";
            }

            String url = "";

            if (service == null) {
                service = "freecurrencyapi";
            }
            switch (service) {
                case "freecurrencyapi":
                    url = "https://api.currencyapi.com/v3/historical?apikey=" + FREE_CURRENCY_API_KEY
                            + "&date=" + date
                            + "&base_currency=" + sell;

                    if (buy != null) {
                        url = url.concat("&currencies=" + buy);
                    }
                    response = HttpRequestUtils.sendHTTPRequest(url);
                    break;
                case "exchangeratesapi":

                    url = "https://api.apilayer.com/exchangerates_data/" + date + "?base=" + sell;
                    if (buy != null) {
                        url = url.concat("&symbols=" + buy);
                    }
                    response = HttpRequestUtils.sendHTTPRequest(url, EXCHANGE_RATES_API_KEY);
                    break;
                default:
                    logger.error("System received request with wrong service parameter");
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            response = new JSONObject(response).toString();

            requestsHistoryService
                    .put(request.getRequestURL().toString() + "?" + request.getQueryString(), response);

            logger.info("The system has processed a request for a historical rates, all data saved to db");

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (SQLException e) {
            logger.error("Adding data to db ended with an error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
