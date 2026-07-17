# Platform Operations Technical Context

```yaml
ModernizationRequirements:
  application_name: insurance-quote
  source_technology: SPRING_WEB
  target_platform: "Spring Boot 4 on Cloud Foundry"
  service_endpoints:
    - path: /health
      method: GET
      response_type: JSON
      response_body: HealthResponse
      authentication: ANONYMOUS
  data_structures:
    - name: HealthResponse
      persistence_type: DTO
  dependencies: []
  migration_considerations:
    - "Preserve the legacy /health response contract"
    - "Cloud Foundry may use the endpoint for HTTP health checks"
```

The endpoint reports process liveness only. It does not inspect dependencies because the application has none.
