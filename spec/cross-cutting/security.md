# Security Requirements

No authentication or authorization controls were detected.

- `GET /api/quote` is anonymous.
- `GET /health` is anonymous.
- No Spring Security dependency or configuration exists in the legacy application.

The modernization compatibility requirement is to preserve anonymous access unless a separately approved security requirement changes it.
