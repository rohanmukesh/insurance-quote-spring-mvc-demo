# Migration Order

## Phase 1 — LOW complexity

1. **US-001 — Calculate an annual insurance quote**
   - Primary business capability
   - Establishes the Spring Boot application, REST controller, service, DTO, validation, and tests
   - No persistence or integration dependencies

2. **US-002 — Check application health**
   - Adds the operational endpoint after the application skeleton exists
   - Depends only on the web layer created with US-001

## Later phases

No MEDIUM, HIGH, or VERY_HIGH complexity stories were identified.
