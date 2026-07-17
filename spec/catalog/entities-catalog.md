# Data Structure Catalog

The application has no database and no persistent entities.

## `Quote`

- Kind: immutable response DTO (`record`)
- Persistence: none
- Fields:
  - `coverage` (`BigDecimal`)
  - `risk` (`RiskLevel`)
  - `annualPremium` (`BigDecimal`)
- Source: `src/main/java/com/acme/quote/domain/Quote.java`

## `RiskLevel`

- Kind: enum
- Persistence: none
- Values and factors:
  - `LOW`: `1.00`
  - `MEDIUM`: `1.25`
  - `HIGH`: `1.60`
- Source: `src/main/java/com/acme/quote/domain/RiskLevel.java`
