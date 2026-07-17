# US-002: Check application health

## User Story

**As a** platform operator,  
**I want to** query the application's health endpoint,  
**So that** I can verify that the application process is responding.

## Acceptance Criteria

- [ ] **AC-1** `GET /health` returns a successful JSON response.
- [ ] **AC-2** The response body is exactly `{"status":"UP"}`.
- [ ] **AC-3** The endpoint accepts anonymous requests.
- [ ] **AC-4** The endpoint has no external service, database, or filesystem dependency.

## Technical Context

### Endpoint

```yaml
ServiceEndpoint:
  path: "/health"
  method: GET
  response_type: JSON
  response_body: HealthResponse
  authentication: ANONYMOUS
  authorization: []
  produces: ["application/json"]
```

The legacy endpoint returns a static process-liveness response and does not invoke a service operation.

### Data Structures Referenced

```yaml
DataStructure:
  name: HealthResponse
  description: "Application process health response"
  persistence_type: DTO
  fields:
    - name: status
      type: String
      nullable: false
      validations: ["constant value UP"]
```

### Integrations

None.

### Source Files (Legacy)

- `src/main/java/com/acme/quote/web/QuoteController.java`

## Test Scenarios

### Happy Path Tests

| ID | Scenario | Preconditions (Given) | Input (When) | Expected Result (Then) | AC Ref | Validates with | Source |
|----|----------|----------------------|--------------|----------------------|--------|----------------|--------|
| TC-002-01 | Report process health | Application is running | `GET /health` | HTTP 200 with `{"status":"UP"}` | AC-1, AC-2 | Endpoint integration test | Derived from code |
| TC-002-02 | Allow anonymous health check | No authenticated principal | `GET /health` | HTTP 200 | AC-3 | Endpoint integration test | Derived from code |

### Test case sources

| Source file | TC-* IDs |
|-------------|----------|
| Derived from `src/main/java/com/acme/quote/web/QuoteController.java` | TC-002-01, TC-002-02 |

### Strategic questions for test cases requiring verification

| TC ID | Question or decision | Resolution |
|-------|---------------------|------------|
| TC-002-01, TC-002-02 | Retain these derived endpoint compatibility tests? | _To be answered_ |

## Strategic Questions

No unresolved business-rule, data-integrity, or integration questions were identified.

## Priority

**MEDIUM** — Operationally useful but not part of premium calculation.

## Complexity

**LOW** — Static, anonymous JSON endpoint with no dependencies.

## Migration phase

**1** — Can be translated with the initial Spring Boot application skeleton.

## Reviewer Validation

### Business Accuracy
- [ ] User story reflects the intended operational need
- [ ] Acceptance criteria are complete and testable
- [ ] Priority assignment is correct

### Technical Completeness
- [ ] Endpoint response contract is documented correctly
- [ ] Anonymous access is correct
- [ ] Migration phase assignment is correct

### Test Coverage
- [ ] Derived endpoint tests are accepted, updated, or removed

### Questions Resolved
- [ ] Test verification decision is recorded

**Reviewed by:** _________________ **Date:** _________
