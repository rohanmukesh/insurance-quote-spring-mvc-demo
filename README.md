# Insurance Quote —  Tanzu modernization demo

A deliberately small, stateless Spring MVC WAR used to demonstrate a short path from Tanzu Hub App Assessment to AI-assisted modernization and `cf push`.

The application calculates an annual insurance premium from coverage and a risk level:

```text
annual premium = coverage × 2% × risk factor
```

It has:

- one REST controller
- one pure business service
- no database or service binding
- no JNDI, EJB, messaging, filesystem writes, or external integrations
- Java 17 and current Spring Framework dependencies
- WAR packaging and an external servlet-container bootstrap

## Intended assessment signal

The repository intentionally uses non-Boot Spring Web through `DispatcherServlet`. It is designed to produce the focused AI-assisted recommendation:

- `spring-web [ai-assisted]` — migrate Spring MVC to Spring Boot

Tanzu assessment rules can evolve, so verify the actual Hub result rather than presenting this expected signal as guaranteed product output.

## Build

```bash
mvn clean package
```

The legacy artifact is `target/insurance-quote.war` and requires a Jakarta Servlet 6.1-compatible application server.

## API

After deployment to a servlet container:

```http
GET /insurance-quote/api/quote?coverage=100000&risk=LOW
```

Example response:

```json
{
  "coverage": 100000,
  "risk": "LOW",
  "annualPremium": 2000.00
}
```

Risk values are `LOW`, `MEDIUM`, and `HIGH`.

## Fast demo flow

1. Add this GitHub repository and its `main` branch to Tanzu Hub App Portfolio Assessment.
2. Review the onboarding plan and confirm the actual challenge count.
3. Clone the repository and connect the Tanzu cf CLI to Hub.
4. Run `cf repo unpack-skills`.
5. In Cursor, run `/tanzu-onboard`.
6. For the `spring-web [ai-assisted]` row, run `/tanzu-modernize-app`.
7. Modernize the single quote endpoint:
   - add Spring Boot
   - replace WAR packaging with an executable JAR
   - add the Boot application entry point
   - remove `web.xml` and `WebConfig`
   - retain the controller, service, domain records, and tests
8. Build with `mvn clean package`.
9. Confirm the generated `manifest.yml`.
10. Log in to the Elastic Application Runtime foundation and run `cf push`.
11. Test `/api/quote?coverage=100000&risk=LOW` on the generated route.

## Expected modernization size

The target rewrite should be reviewable in minutes:

- one `pom.xml` conversion
- one Spring Boot main class
- deletion of two servlet-container bootstrap files
- little or no business-code change
- no backing-service provisioning

## TrueSource Spring Maven repositories

The `truesource` Maven profile configures:

- Spring Enterprise tooling, recipes, extensions, and patch releases:
  `https://packages.broadcom.com/artifactory/spring-enterprise/`
- Rebuilt Spring transitive dependencies:
  `https://packages.broadcom.com/artifactory/tanzu-spring-dependencies/`

The dependency repository is checked before Spring Enterprise for dependencies
available from both. Maven Central remains available as a fallback. Enabling
these repositories does not change the dependency versions declared by this app.

### Build through the shared Nexus instance

Use the read-only Nexus Maven settings supplied by your repository administrator:

```bash
mvn -s /path/to/nexus-settings.xml -Ptruesource clean verify
```

For the current lab, the Maven group is
`https://hub.local:8444/repository/truesource-maven/`. The build machine needs
network access to that address and JVM trust for the lab's HTTPS certificate.
The settings file and credentials stay outside this repository.

### Build directly against Broadcom

Set `BROADCOM_EMAIL` and `BROADCOM_REGISTRY_TOKEN` in your shell or secret store,
then run:

```bash
mvn -s .mvn/truesource-settings.xml -Ptruesource clean verify
```

The checked-in settings contain environment-variable references. Store no real
credentials in this file or in `pom.xml`.

### GitHub Actions

Set the repository Actions secrets `BROADCOM_EMAIL` and
`BROADCOM_REGISTRY_TOKEN`. Maven CI enables the `truesource` profile when both
are available. GitHub-hosted runners use the public Broadcom endpoints because
the lab Nexus hostname is on a private network.

Without those secrets, CI uses the default Maven repositories; builds that
already require commercial-only versions still need Broadcom credentials.
