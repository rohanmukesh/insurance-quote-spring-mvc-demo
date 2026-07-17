# Domain Mapping

## Insurance Quoting

Business capability for calculating an annual insurance premium from requested coverage and a risk classification.

- Endpoint: `GET /api/quote`
- Operation: `QuoteService#calculate`
- Data structures: `Quote`, `RiskLevel`

## Platform Operations

Operational capability for reporting whether the application process is running.

- Endpoint: `GET /health`
- Operation: static health response

## Cross-Cutting Context

- Source technology: non-Boot Spring Web MVC
- Deployment: WAR on an external Jakarta Servlet 6.1 container
- Security: no authentication or authorization detected
- Persistence: none
- Integrations: none
- Scheduled tasks: none
