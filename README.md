# CurrencyExchange
Short description
----------------
REST-application on Java for providing up-to-date currency exchange rates for input currencies.

Used frameworks and libraries:
- Spring Boot (web, test);
- Json
- Junit
- Httpclient
- H2 database

Brief description of the classes
-----------------------
The root package of the ```com.currency.Exchange``` project contains the following classes: 
- ```ExchangeApplication``` - class, which contains the entry point to the application;
- ```RunAfterStartup``` - class, that creates tables in In-Memory database when application starts

The package ```Controllers``` contains the following class:
- ```CurrencyRequestController``` - a controller that receives HTTP requests, processes them and returns a response;

The package ```Config``` contains the following class:
- ```ScheduleConfig``` - configurator for delayed functions

The package ```Services``` contains the following classes:
- ```AvailableCurrenciesService``` - service for interacting with the table of the current list of available currencies in In-Memory database;
- ```RequestsHistoryService``` - service for interacting with the table of requests and responses history in In-Memory database;

The package ```Utils``` contains the following classes:
- ```AvailableCurrencyUpdateUtils``` - service 
- ```HttpRequestUtils``` - service 
- ```InMemoryDBUtils``` - service 

Description of the external programming interface
-----------------------------------------
### Generation of short links
To create a new short link, send a POST request to ```/```:

The request keys must contain the key and value for Basic authorization:

```Key: Authorization, Value: Basic dXNlcm5hbWU6cGFzc3dvcmQxMjM=```

(When using Basic Auth in Postman: ```Username: username, Password: password123```)

The body of the request must contain an object in JSON format with the string parameter ```link```. This setting must meet the following requirements:
- not an empty string;
- maximum allowable line size - 2000 characters;
- the string must correspond to the URL format with the obligatory indication of the protocol.
 
The response returns an object containing the generated short link.

In cases where the ```link``` field has an invalid format, the code ```400 Bad Request``` is returned to the client and the short link will not be created.

### Follow a short link
To follow a short link, you must insert the generated short link into the address bar of any browser
