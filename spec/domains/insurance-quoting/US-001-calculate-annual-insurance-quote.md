# US-001: Calculate an annual insurance quote

## User Story

**As an** insurance quote consumer,  
**I want to** calculate an annual premium from coverage and risk level,  
**So that** I can obtain a deterministic insurance quote.

## Acceptance Criteria

- [ ] **AC-1** A positive coverage amount and supported risk level return a quote containing the submitted coverage, risk, and calculated annual premium.
- [ ] **AC-2** The annual premium equals `coverage × 0.02 × risk factor`, rounded to two decimal places with `HALF_UP`.
- [ ] **AC-3** Risk factors are `LOW = 1.00`, `MEDIUM = 1.25`, and `HIGH = 1.60`.
- [ ] **AC-4** Null, zero, or negative coverage is rejected with `Coverage must be greater than zero`.
- [ ] **AC-5** A null risk level is rejected with `Risk level is required`.
- [ ] **AC-6** Business validation failures exposed through the quote endpoint return HTTP 400 with a JSON `error` field.

## Technical Context

### Endpoint

```yaml
ServiceEndpoint:
  path: "/api/quote"
  method: GET
  query_parameters:
    - name: coverage
      type: BigDecimal
      required: true
    - name: risk
      type: RiskLevel
      required: true
  response_type: JSON
  response_body: Quote
  authentication: ANONYMOUS
  authorization: []
  produces: ["application/json"]
  operation: calculateQuote
  error_responses:
    - status_code: 400
      condition: "Coverage is null or not greater than zero, or risk is null"
      response_body: ErrorResponse
```

### Service Operation

```yaml
ServiceOperation:
  name: calculateQuote
  description: "Calculate an annual insurance premium from coverage and risk classification"
  type: QUERY
  inputs:
    - name: coverage
      type: BigDecimal
      required: true
      validation: ["not null", "greater than zero"]
    - name: risk
      type: RiskLevel
      required: true
      validation: ["not null", "LOW, MEDIUM, or HIGH"]
  output: Quote
  transactional: false
  read_only: true
  preconditions:
    - id: coverage-positive
      description: "Coverage must be present and greater than zero"
      condition: "coverage != null && coverage > 0"
      inputs_used: ["coverage"]
      failure_exception: IllegalArgumentException
      failure_message: "Coverage must be greater than zero"
    - id: risk-required
      description: "Risk level must be present"
      condition: "risk != null"
      inputs_used: ["risk"]
      failure_exception: IllegalArgumentException
      failure_message: "Risk level is required"
  steps:
    - "Set $basePremium = coverage × 0.02"
    - "Set $riskAdjustedPremium = $basePremium × risk.factor"
    - "Set $annualPremium = $riskAdjustedPremium rounded to scale 2 using HALF_UP"
    - "Set output.coverage = coverage"
    - "Set output.risk = risk"
    - "Set output.annualPremium = $annualPremium"
  structural_events: []
  exceptions:
    - type: IllegalArgumentException
      condition: "Coverage is null, zero, or negative"
    - type: IllegalArgumentException
      condition: "Risk is null"
```

### Data Structures Referenced

```yaml
DataStructures:
  - name: Quote
    description: "Immutable insurance quote response"
    persistence_type: DTO
    fields:
      - name: coverage
        type: BigDecimal
      - name: risk
        type: RiskLevel
      - name: annualPremium
        type: BigDecimal
  - name: RiskLevel
    description: "Supported risk classifications and premium multipliers"
    persistence_type: NONE
    values:
      LOW: "1.00"
      MEDIUM: "1.25"
      HIGH: "1.60"
  - name: ErrorResponse
    description: "Business validation error payload"
    persistence_type: DTO
    fields:
      - name: error
        type: String
```

### Integrations

None.

### Source Files (Legacy)

- `src/main/java/com/acme/quote/web/QuoteController.java`
- `src/main/java/com/acme/quote/service/QuoteService.java`
- `src/main/java/com/acme/quote/domain/Quote.java`
- `src/main/java/com/acme/quote/domain/RiskLevel.java`
- `src/test/java/com/acme/quote/service/QuoteServiceTest.java`

## Test Scenarios

### Happy Path Tests

| ID | Scenario | Preconditions (Given) | Input (When) | Expected Result (Then) | AC Ref | Validates with | Source |
|----|----------|----------------------|--------------|----------------------|--------|----------------|--------|
| TC-001-01 | Calculate low-risk premium | None | `coverage=100000`, `risk=LOW` | Quote has coverage `100000`, risk `LOW`, premium `2000.00` | AC-1, AC-2, AC-3 | `calculate(100000, LOW)` | `QuoteServiceTest.java` |
| TC-001-02 | Calculate high-risk premium | None | `coverage=100000`, `risk=HIGH` | Quote has premium `3200.00` | AC-1, AC-2, AC-3 | `calculate(100000, HIGH)` | `QuoteServiceTest.java` |
| TC-001-03 | Calculate medium-risk premium | None | `coverage=100000`, `risk=MEDIUM` | Quote has premium `2500.00` | AC-1, AC-2, AC-3 | `calculate(100000, MEDIUM)` | Derived from code |

### Error/Validation Tests

| ID | Scenario | Input (When) | Expected Error (Then) | AC Ref | Validates with | Source |
|----|----------|--------------|---------------------|--------|----------------|--------|
| TC-001-04 | Reject zero coverage | `coverage=0`, `risk=MEDIUM` | `Coverage must be greater than zero` | AC-4 | `calculate(0, MEDIUM)` | `QuoteServiceTest.java` |
| TC-001-05 | Reject negative coverage | `coverage=-1`, `risk=LOW` | `Coverage must be greater than zero` | AC-4 | `calculate(-1, LOW)` | Derived from code |
| TC-001-06 | Reject null risk | `coverage=100000`, `risk=null` | `Risk level is required` | AC-5 | `calculate(100000, null)` | Derived from code |
| TC-001-07 | Map business validation to HTTP 400 | `GET /api/quote?coverage=0&risk=LOW` | HTTP 400 and `{"error":"Coverage must be greater than zero"}` | AC-6 | Endpoint integration test | Derived from code |

### Edge Cases

| ID | Scenario | Input (When) | Expected Result (Then) | AC Ref | Validates with | Source |
|----|----------|--------------|----------------------|--------|----------------|--------|
| TC-001-08 | Round fractional premium | `coverage=1000.005`, `risk=LOW` | Premium is `20.00` after scale-2 `HALF_UP` rounding | AC-2 | `calculate(1000.005, LOW)` | Derived from code |

### Test case sources

| Source file | TC-* IDs |
|-------------|----------|
| `src/test/java/com/acme/quote/service/QuoteServiceTest.java` | TC-001-01, TC-001-02, TC-001-04 |
| Derived from implementation | TC-001-03, TC-001-05 through TC-001-08 |

### Strategic questions for test cases requiring verification

| TC ID | Question or decision | Resolution |
|-------|---------------------|------------|
| TC-001-03 | Retain explicit coverage for the MEDIUM risk factor? | _To be answered_ |
| TC-001-05 through TC-001-08 | Retain the derived validation, HTTP mapping, and rounding tests? | _To be answered_ |

## Strategic Questions

### Business Rules

| ID | Question | Why It Matters | Answer |
|----|----------|----------------|--------|
| Q-001-01 | Are the base rate (`2%`) and three risk factors fixed business rules? | Determines whether exact constants must remain part of the compatibility contract. | _To be answered_ |

## Priority

**HIGH** — This is the application's primary business capability.

## Complexity

**LOW** — Pure deterministic calculation with no persistence or integrations.

## Migration phase

**1** — Establishes the Spring Boot REST and service patterns without external dependencies.

## Reviewer Validation

### Business Accuracy
- [ ] User story reflects actual business need
- [ ] Acceptance criteria are complete and testable
- [ ] Priority assignment is correct

### Technical Completeness
- [ ] Endpoint and data structures are documented correctly
- [ ] No integrations or persistence requirements are missing
- [ ] Migration phase assignment is correct

### Test Coverage
- [ ] Existing test behavior is retained
- [ ] Derived test cases are accepted, updated, or removed

### Questions Resolved
- [ ] Strategic questions are answered or explicitly deferred

**Reviewed by:** _________________ **Date:** _________
