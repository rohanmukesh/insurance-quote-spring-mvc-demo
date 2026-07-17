# Service Catalog

## `QuoteService#calculate`

- Type: query/calculation
- Inputs:
  - `coverage` (`BigDecimal`)
  - `risk` (`RiskLevel`)
- Output: `Quote`
- Transactional: no
- Dependencies: none
- Rules:
  1. Coverage must be non-null and greater than zero.
  2. Risk level must be non-null.
  3. Annual premium equals `coverage × 0.02 × risk.factor`.
  4. Annual premium is rounded to two decimal places using `HALF_UP`.
- Errors:
  - Invalid coverage: `IllegalArgumentException("Coverage must be greater than zero")`
  - Missing risk: `IllegalArgumentException("Risk level is required")`
- Source files:
  - `src/main/java/com/acme/quote/service/QuoteService.java`
  - `src/main/java/com/acme/quote/domain/Quote.java`
  - `src/main/java/com/acme/quote/domain/RiskLevel.java`
  - `src/test/java/com/acme/quote/service/QuoteServiceTest.java`
