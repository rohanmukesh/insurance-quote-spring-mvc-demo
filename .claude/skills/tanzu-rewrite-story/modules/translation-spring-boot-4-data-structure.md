# Spring Boot 4 Translation: DataStructure

Map **DataStructure** (entity, DTO, enum) to JPA entities, Java records, and enums. For the core translation guide, see [translation-instructions-spring-boot-4.md](../translation-instructions-spring-boot-4.md).

## Patterns

- **Entity:** `@Entity`, `@Table(name)`, `@Id`, `@GeneratedValue`, `@Column`, `@ManyToOne`/`@OneToMany`/etc. with `@JoinColumn`. Use JSpecify `@NonNull`/`@Nullable` on getters. Map fields to columns; relationships to JPA associations.
- **DTO:** Java records with Bean Validation (`@NotNull`, `@Size`, `@Email`) and JSpecify. For DTOs with entity-typed fields, use `loaded_shape` when present to define which properties are loaded.
- **Jackson 3.x:** Use `tools.jackson` package; configure `JsonMapper` if needed. Records serialize by default.
- **Enum:** Java enum; use `@Enumerated(EnumType.STRING)` when persisting.

For full examples (JPA entity with relationships, DTO with records and Jackson 3.x), see [translation-instructions-spring-boot-4-full.md](../translation-instructions-spring-boot-4-full.md) section "DataStructure Examples" (lines 1992–2341).
