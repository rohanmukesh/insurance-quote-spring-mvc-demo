# Spring Boot 4 Translation: ServiceTask

Map **ServiceTask** to `@Scheduled` and `@EnableScheduling`. For the core translation guide, see [translation-instructions-spring-boot-4.md](../translation-instructions-spring-boot-4.md).

## Patterns

- **Cron:** `@Scheduled(cron = "${property}", zone = "...")` with optional concurrency guard (e.g. AtomicBoolean).
- **Fixed rate/delay:** `@Scheduled(fixedRate = ...)` or `fixedDelay`.
- **Error handling:** Map retry_count, notification_on_failure to try/catch and alert service.

For full example (cron-based daily report with retry and notification), see [translation-instructions-spring-boot-4-full.md](../translation-instructions-spring-boot-4-full.md) section "ServiceTask Examples" (lines 3260–3401).
