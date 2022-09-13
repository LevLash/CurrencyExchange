package com.currency.Exchange;


import com.currency.Exchange.Controllers.CurrencyRequestController;
import com.currency.Exchange.Services.AvailableCurrenciesService;
import com.currency.Exchange.Services.RequestsHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RunAfterStartup {

    private final Logger logger = LoggerFactory.getLogger(CurrencyRequestController.class);

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        try {
            createTables();

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public void createTables() {
        AvailableCurrenciesService.createTable();
        RequestsHistoryService.createTable();
    }

}
