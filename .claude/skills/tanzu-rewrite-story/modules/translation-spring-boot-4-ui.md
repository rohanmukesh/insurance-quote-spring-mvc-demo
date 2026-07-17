# Spring Boot 4 Translation: UI (ViewRequirement, FormDefinition)

Map **ViewRequirement** and **FormDefinition** to Thymeleaf `@Controller` + templates or to REST/SPA. For the core translation guide, see [translation-instructions-spring-boot-4.md](../translation-instructions-spring-boot-4.md).

## Patterns

- **JSP/legacy to Thymeleaf:** `@Controller`, `@GetMapping`/`@PostMapping`, return view name; `Model` for attributes. Templates in `templates/` with `th:`, `th:field`, `th:errors`. Use Layout Dialect for layout.
- **Forms:** `@ModelAttribute` for form object, `@Valid` with BindingResult; on errors return view name, on success redirect with RedirectAttributes.
- **i18n:** `messages.properties` and `th:text="#{key}"`.
- **JSP to REST SPA:** Replace view endpoints with `@RestController` and JSON; front-end consumes API. Document API in spec.

For full examples (JSP to Thymeleaf profile form, JSP to REST SPA product management), see [translation-instructions-spring-boot-4-full.md](../translation-instructions-spring-boot-4-full.md) section "UI Migration Examples" (lines 2342–2786).
