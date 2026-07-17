# Insurance Quoting Technical Context

```yaml
ModernizationRequirements:
  application_name: insurance-quote
  source_technology: SPRING_WEB
  source_server: EXTERNAL_JAKARTA_SERVLET_6_1_CONTAINER
  target_platform: "Spring Boot 4 executable JAR on Cloud Foundry"
  java:
    source_version: 17
    target_version: 21
  service_endpoints:
    - path: /api/quote
      method: GET
      response_type: JSON
      response_body: Quote
      authentication: ANONYMOUS
      operation: calculateQuote
  service_operations:
    - name: calculateQuote
      type: QUERY
      transactional: false
      output: Quote
  data_structures:
    - name: Quote
      persistence_type: DTO
    - name: RiskLevel
      persistence_type: NONE
    - name: ErrorResponse
      persistence_type: DTO
  persistence: NONE
  integrations: []
  migration_considerations:
    - "Replace external DispatcherServlet bootstrap with @SpringBootApplication"
    - "Retain premium calculation and validation behavior"
    - "Replace WAR packaging with executable JAR packaging"
```

## Legacy bootstrap

- `src/main/webapp/WEB-INF/web.xml` registers the `DispatcherServlet`.
- `src/main/java/com/acme/quote/web/WebConfig.java` enables Spring MVC and component scanning.
- `pom.xml` packages `target/insurance-quote.war`.
