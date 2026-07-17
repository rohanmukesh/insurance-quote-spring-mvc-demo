# Data Structures

The application has no persistent entities or database schema.

## DTOs

### Quote

Immutable quote response with `coverage: BigDecimal`, `risk: RiskLevel`, and `annualPremium: BigDecimal`.

Used by [US-001](../domains/insurance-quoting/US-001-calculate-annual-insurance-quote.md).

### ErrorResponse

JSON error response with `error: String`.

Used by [US-001](../domains/insurance-quoting/US-001-calculate-annual-insurance-quote.md).

### HealthResponse

JSON process-health response with `status: String`, currently fixed to `UP`.

Used by [US-002](../domains/platform-operations/US-002-check-application-health.md).

## Enumerations

### RiskLevel

Non-persistent risk classification:

- `LOW` — factor `1.00`
- `MEDIUM` — factor `1.25`
- `HIGH` — factor `1.60`

Used by [US-001](../domains/insurance-quoting/US-001-calculate-annual-insurance-quote.md).
