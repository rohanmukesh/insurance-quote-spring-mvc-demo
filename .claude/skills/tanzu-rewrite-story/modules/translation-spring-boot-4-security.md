# Spring Boot 4 Translation: SecurityRequirement

Map **SecurityRequirement** to Spring Security 7 (component-based configuration). For the core translation guide, see [translation-instructions-spring-boot-4.md](../translation-instructions-spring-boot-4.md).

## Patterns

- **Authentication:** Map authentication_method to form login, BASIC, Bearer, OAuth2, etc. Use `SecurityFilterChain` and `UserDetailsService`.
- **Authorization:** `@PreAuthorize("hasRole('ADMIN')")` on controllers; map protected_endpoints to path matchers and access rules.
- **Roles:** Map role definitions to authorities; use `hasRole`, `hasAnyRole` in `@PreAuthorize`.

For full example (role-based access control, UserDetailsService), see [translation-instructions-spring-boot-4-full.md](../translation-instructions-spring-boot-4-full.md) section "SecurityRequirement Examples" (lines 3050–3259).
