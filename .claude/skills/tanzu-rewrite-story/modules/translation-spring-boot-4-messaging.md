# Spring Boot 4 Translation: MessagingRequirement

Map **MessagingRequirement** to JMS with Spring Boot 4 (JmsClient, `@JmsListener`). For the core translation guide, see [translation-instructions-spring-boot-4.md](../translation-instructions-spring-boot-4.md).

## Patterns

- **Consumer:** `@JmsListener(destination = "...")` on a method; use `JmsClient` for send. Map destination, acknowledgment, error handling from spec.
- **Producer:** `JmsClient.send(destination, payload)` or equivalent. Map retry and error handling.
- **Configuration:** `spring.artemis.*` or broker URL from ExternalResourceRequirement; for Cloud Foundry use VCAP bindings.

For full examples (JMS consumer with error handling, producer with JmsClient API), see [translation-instructions-spring-boot-4-full.md](../translation-instructions-spring-boot-4-full.md) section "MessagingRequirement Examples" (lines 2787–3049).
