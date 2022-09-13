# CurrencyExchange
Short description
-
REST-application on Java for providing up-to-date currency exchange rates for input currencies.

Used frameworks and libraries:
- Spring Boot (web, test);
- Json
- Junit
- Httpclient
- H2 database

Brief description of the classes
-
The root package of the `com.currency.Exchange` project contains the following classes: 
- `ExchangeApplication` - class, which contains the entry point to the application;
- `RunAfterStartup` - class, that creates tables in In-Memory database when application starts;

The package `Controllers` contains the following class:
- `CurrencyRequestController` - a controller that receives HTTP requests, processes them and returns a response;

The package `Config` contains the following class:
- `ScheduleConfig` - configurator for delayed functions;

The package `Services` contains the following classes:
- `AvailableCurrenciesService` - service for interacting with the table of the current list of available currencies in In-Memory database;
- `RequestsHistoryService` - service for interacting with the table of requests and responses history in In-Memory database;

The package `Utils` contains the following classes:
- `AvailableCurrencyUpdateUtils` - utility that updates available currencies list every 24h;
- `HttpRequestUtils` - utility with sending HTTP requests functions;
- `InMemoryDBUtils` - utility with connecting to In-Memory database and showing info about SQLException functions;

Description of the external programming interface
-
### Get exchange rates for currencies 

To get exchange rates for currencies, send a GET request to `/latest`:

The params of the request can contain:
- `sell=[Currency]` - Currency that you want to sell (default - USD);
- `buy=[Currency1,Currency2,Currency3...]` - Currencies that you want to buy (default - all);
 
The response returns an json object containing the exchange rates for selected currencies.

In cases where the `sell` or `buy` fields has an invalid format, the code `400 Bad Request` is returned to the client.

### Get available currency codes

To get exchange rates for currencies, send a GET or POST request to `/available`:
The response returns an json object containing the available currency codes.

### Get exchange rates history by criteria parameters

To get exchange rates for currencies, send a GET request to `/history`:

The params of the request can contain:
- `date=[YYYY-MM-DD]` - Date for which you want to get the exchange rate (Mandatory, can't be null);
- `sell=[Currency]` - Currency that you want to sell (default - USD);
- `buy=[Currency1,Currency2,Currency3...]` - Currencies that you want to buy (default - all avaible currencies);
- `service=[ServiceName]` - 3d party service from where you want to get info (you can choose between `exchangeratesapi` (https://exchangeratesapi.io/) or `freecurrencyapi` (https://freecurrencyapi.net/)) (default - `freecurrencyapi`);
 
The response returns an json object containing the exchange rates for selected currencies, 3d party service and date.

In cases where the any of the fields has an invalid format, or field `date` is missing, the code `400 Bad Request` is returned to the client.

Deploying on Docker commands
-
- `docker build -t currency_exchange_docker:latest .`
- `docker run -p 8080:8080 currency_exchange_docker`
