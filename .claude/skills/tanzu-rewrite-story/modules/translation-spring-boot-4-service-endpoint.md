# Spring Boot 4 Translation: ServiceEndpoint

Map **ServiceEndpoint** (and related ServiceOperation, DataStructure) from the technical context to Spring Boot 4 REST controllers, services, repositories, entities, and DTOs. For the core translation guide and context management, see [translation-instructions-spring-boot-4.md](../translation-instructions-spring-boot-4.md).

## Full REST CRUD example

For a complete end-to-end example (user story, YAML, entity, DTOs, repository, service, controller, exceptions, global exception handler, unit tests, integration tests with Testcontainers, application.yml, manifest.yml), see [translation-spring-boot-4-example-rest-crud.md](translation-spring-boot-4-example-rest-crud.md).

## Patterns

- **Controller:** `@RestController`, `@RequestMapping("/api/...")`, `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`. Map `path`, `path_variables`, `query_parameters`, `request_body`, `response_body`, `authorization` to method parameters and `@PreAuthorize`.
- **Response:** Use record DTOs and `ResponseEntity`; for collections, return `List<Dto>` or `Page<Dto>`. Use ProblemDetail for errors and a `@RestControllerAdvice` for exception handling.
- **Validation:** `@Valid` on request body; Bean Validation on DTOs; map spec `error_responses` to exception handlers.
- **API versioning:** When the spec has `api_version`, use Spring Boot 4 API versioning: `ApiVersionConfigurer`, path prefix `/api/v{version}`, and `@GetMapping(version = "1")` / `@GetMapping(version = "2")` on the same path. See full doc [translation-instructions-spring-boot-4-full.md](../translation-instructions-spring-boot-4-full.md) section "ServiceEndpoint Examples > Example 2: API Versioning".
- **File upload/download:** POST with `MultipartFile` and `consumes = MULTIPART_FORM_DATA`; GET returning `Resource` or `StreamingResponseBody` with `Content-Disposition: attachment`. See full doc section "ServiceEndpoint Examples > Example 3: File Upload/Download".

## Example 2: API Versioning (summary)

When technical context has `api_version` on endpoints, use `ApiVersioningConfig` (path segment, add path prefix `/api/v{version}`) and on the controller use `@GetMapping(version = "1")` / `@GetMapping(version = "2")` with different response DTOs (e.g. OrderV1Response vs OrderV2Response, pagination for v2). Full code: [translation-instructions-spring-boot-4-full.md](../translation-instructions-spring-boot-4-full.md) lines 947–1216.

## Example 3: File Upload/Download (summary)

- **Upload:** `@PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)`, `@RequestParam("file") MultipartFile file`, service stores file and saves Document metadata, returns DocumentMetadata DTO.
- **Download:** `@GetMapping("/{id}")` returns `ResponseEntity<Resource>` with `Content-Disposition: attachment; filename="..."`; optional `@GetMapping("/{id}/stream")` with `StreamingResponseBody`. Full code: [translation-instructions-spring-boot-4-full.md](../translation-instructions-spring-boot-4-full.md) lines 1220–1397.

## Testing and Cloud Foundry

Use testing patterns from [translation-spring-boot-4-testing.md](translation-spring-boot-4-testing.md) (unit, integration with MockMvc/RestTestClient, Testcontainers). Use Cloud Foundry patterns from [translation-spring-boot-4-cloud-foundry.md](translation-spring-boot-4-cloud-foundry.md) for application.yml and manifest.yml.
