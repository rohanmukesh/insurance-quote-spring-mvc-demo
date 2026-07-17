# Endpoint Catalog

## GET `/api/quote`

- Controller: `com.acme.quote.web.QuoteController#quote`
- Response: JSON `Quote`
- Inputs:
  - `coverage` (`BigDecimal`, required)
  - `risk` (`RiskLevel`, required; `LOW`, `MEDIUM`, or `HIGH`)
- Operation: `QuoteService#calculate`
- Errors: `IllegalArgumentException` is returned as HTTP 400 with `{"error": "<message>"}`
- Authentication: none detected
- Source files:
  - `src/main/java/com/acme/quote/web/QuoteController.java`
  - `src/main/java/com/acme/quote/service/QuoteService.java`
  - `src/main/java/com/acme/quote/domain/Quote.java`
  - `src/main/java/com/acme/quote/domain/RiskLevel.java`

## GET `/health`

- Controller: `com.acme.quote.web.QuoteController#health`
- Response: JSON map containing `{"status": "UP"}`
- Inputs: none
- Authentication: none detected
- Source files:
  - `src/main/java/com/acme/quote/web/QuoteController.java`

## Bootstrap

- Technology: non-Boot Spring Web MVC
- Servlet: Spring `DispatcherServlet`, mapped to `/`
- Configuration: annotation-based `WebConfig`
- Deployment: Jakarta Servlet 6.1 WAR
- Source files:
  - `src/main/webapp/WEB-INF/web.xml`
  - `src/main/java/com/acme/quote/web/WebConfig.java`
  - `pom.xml`
