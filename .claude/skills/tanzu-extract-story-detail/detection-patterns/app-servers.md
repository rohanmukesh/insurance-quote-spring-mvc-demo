# Application Server Detection Patterns

This document defines detection patterns for identifying which application server a legacy Java EE application was designed for. These patterns help determine server-specific configurations that need migration to Spring Boot.

## Table of Contents

- [JBoss / WildFly](#jboss--wildfly)
- [WebLogic](#weblogic)
- [WebSphere](#websphere)
- [Tomcat](#tomcat)
- [Generic Java EE](#generic-java-ee)
- [Datasource connection pool extraction](#datasource-connection-pool-extraction)

---

## JBoss / WildFly

### JBoss Web Descriptor

| Pattern | `jboss-web.xml` (filename) |
|---------|----------------------------|
| **File Types** | `jboss-web.xml` |
| **Indicates** | JBoss/WildFly deployment |
| **Schema Entity** | `ServerMigrationRequirement.source_server = JBOSS` |

**Example:**
```xml
<jboss-web>
    <context-root>/orders</context-root>
    <resource-ref>
        <res-ref-name>jdbc/OrdersDS</res-ref-name>
        <jndi-name>java:jboss/datasources/OrdersDS</jndi-name>
    </resource-ref>
</jboss-web>
```

**Extraction Notes:**
- Extract `context-root` → `server.servlet.context-path`
- Extract `resource-ref` mappings → `ExternalResourceRequirement`

---

### JBoss Deployment Structure

| Pattern | `jboss-deployment-structure.xml` (filename) |
|---------|----------------------------------------------|
| **File Types** | `jboss-deployment-structure.xml` |
| **Indicates** | JBoss module dependencies and classloading |
| **Schema Entity** | `ServerMigrationRequirement.deployment_descriptors` |

---

### JBoss JNDI Pattern

| Pattern | `java:jboss/` |
|---------|---------------|
| **File Types** | `*.xml`, `*.java` |
| **Indicates** | JBoss-specific JNDI namespace |
| **Schema Entity** | `ExternalResourceRequirement.legacy_jndi_name` |

---

### JBoss Package Imports

| Pattern | `org\.jboss\.` |
|---------|----------------|
| **File Types** | `*.java` |
| **Indicates** | JBoss-specific code dependency |
| **Schema Entity** | `ServerMigrationRequirement.vendor_dependencies` |

---

## WebLogic

### WebLogic Deployment Descriptor

| Pattern | `weblogic.xml` (filename) |
|---------|---------------------------|
| **File Types** | `weblogic.xml` |
| **Indicates** | WebLogic deployment |
| **Schema Entity** | `ServerMigrationRequirement.source_server = WEBLOGIC` |

**Example:**
```xml
<weblogic-web-app>
    <context-root>/orders</context-root>
    <resource-description>
        <res-ref-name>jdbc/OrdersDS</res-ref-name>
        <jndi-name>jdbc/OrdersDS</jndi-name>
    </resource-description>
</weblogic-web-app>
```

---

### WebLogic Application Descriptor

| Pattern | `weblogic-application.xml` (filename) |
|---------|----------------------------------------|
| **File Types** | `weblogic-application.xml` |
| **Indicates** | WebLogic EAR-level configuration |
| **Schema Entity** | `ServerMigrationRequirement.deployment_descriptors` |

---

### WebLogic EJB Descriptor

| Pattern | `weblogic-ejb-jar.xml` (filename) |
|---------|-----------------------------------|
| **File Types** | `weblogic-ejb-jar.xml` |
| **Indicates** | WebLogic EJB configuration |
| **Schema Entity** | `ServerMigrationRequirement.deployment_descriptors` |

---

### WebLogic Package Imports

| Pattern | `weblogic\.` |
|---------|--------------|
| **File Types** | `*.java` |
| **Indicates** | WebLogic-specific code |
| **Schema Entity** | `ServerMigrationRequirement.vendor_dependencies` |

**Common patterns to watch for:**
- `weblogic.jndi.WLInitialContextFactory`
- `weblogic.transaction.*`
- `weblogic.servlet.*`
- `weblogic.security.*`

---

## WebSphere

### WebSphere Binding Descriptor

| Pattern | `ibm-web-bnd.xml` (filename) |
|---------|-------------------------------|
| **File Types** | `ibm-web-bnd.xml` |
| **Indicates** | WebSphere deployment |
| **Schema Entity** | `ServerMigrationRequirement.source_server = WEBSPHERE` |

**Example:**
```xml
<web-bnd xmlns="http://websphere.ibm.com/xml/ns/javaee">
    <virtual-host name="default_host"/>
    <resource-ref name="jdbc/OrdersDS" binding-name="jdbc/OrdersDS"/>
</web-bnd>
```

---

### WebSphere Extension Descriptor

| Pattern | `ibm-web-ext.xml` (filename) |
|---------|-------------------------------|
| **File Types** | `ibm-web-ext.xml` |
| **Indicates** | WebSphere web extensions |
| **Schema Entity** | `ServerMigrationRequirement.deployment_descriptors` |

---

### WebSphere Application Binding

| Pattern | `ibm-application-bnd.xml` (filename) |
|---------|---------------------------------------|
| **File Types** | `ibm-application-bnd.xml` |
| **Indicates** | WebSphere EAR-level bindings |
| **Schema Entity** | `ServerMigrationRequirement.deployment_descriptors` |

---

### WebSphere Package Imports

| Pattern | `com\.ibm\.websphere\.` |
|---------|-------------------------|
| **File Types** | `*.java` |
| **Indicates** | WebSphere-specific code |
| **Schema Entity** | `ServerMigrationRequirement.vendor_dependencies` |

---

## Tomcat

### Tomcat Context Configuration

| Pattern | `context.xml` (filename in META-INF) |
|---------|---------------------------------------|
| **File Types** | `context.xml` |
| **Indicates** | Tomcat deployment |
| **Schema Entity** | `ServerMigrationRequirement.source_server = TOMCAT` |

**Example:**
```xml
<Context>
    <Resource name="jdbc/OrdersDS"
              type="javax.sql.DataSource"
              driverClassName="com.mysql.cj.jdbc.Driver"
              url="jdbc:mysql://localhost:3306/orders"
              username="app"
              password="secret"
              maxTotal="20"/>
</Context>
```

**Extraction Notes:**
- Extract `<Resource>` definitions → `ExternalResourceRequirement`
- Resource properties map to Spring Boot datasource configuration

---

### Tomcat Package Imports

| Pattern | `org\.apache\.catalina\.` |
|---------|---------------------------|
| **File Types** | `*.java` |
| **Indicates** | Tomcat-specific code |
| **Schema Entity** | `ServerMigrationRequirement.vendor_dependencies` |

---

## Generic Java EE

### Standard web.xml

| Pattern | `web.xml` (filename) |
|---------|----------------------|
| **File Types** | `web.xml` |
| **Indicates** | Java EE web application |
| **Schema Entity** | Multiple entities |

**Key patterns to extract:**
- `<servlet>` definitions
- `<filter>` definitions
- `<listener>` definitions
- `<resource-ref>` references
- `<security-constraint>` rules
- `<welcome-file-list>` configuration

---

### Standard ejb-jar.xml

| Pattern | `ejb-jar.xml` (filename) |
|---------|--------------------------|
| **File Types** | `ejb-jar.xml` |
| **Indicates** | EJB deployment descriptor |
| **Schema Entity** | Multiple entities (EJB configuration) |

---

### Standard application.xml

| Pattern | `application.xml` (filename) |
|---------|------------------------------|
| **File Types** | `application.xml` |
| **Indicates** | Java EE EAR application |
| **Schema Entity** | Application structure |

---

## Datasource connection pool extraction

When a JNDI resource is a **datasource** (Spring Web or EJB), extract connection pool settings from server configuration where present. Map to `ExternalResourceRequirement.connection_pool` and `spring_boot_config.properties` with `spring.datasource.hikari.*` keys for Spring Boot translation.

### JBoss / WildFly

| Pattern | `<pool>`, `<min-pool-size>`, `<max-pool-size>` |
|---------|--------------------------------------------------|
| **File Types** | `*-ds.xml`, `standalone.xml`, `domain.xml` |
| **Indicates** | Datasource connection pool configuration |
| **Schema Entity** | `ExternalResourceRequirement.connection_pool`, `spring_boot_config.properties` |

**Example:**
```xml
<datasource jndi-name="java:jboss/datasources/OrdersDS" ...>
    <pool>
        <min-pool-size>5</min-pool-size>
        <max-pool-size>20</max-pool-size>
    </pool>
</datasource>
```

**Extraction Notes:**
- `max-pool-size` → `connection_pool.maximum_pool_size` and `spring.datasource.hikari.maximum-pool-size`
- `min-pool-size` → `connection_pool.minimum_idle` and `spring.datasource.hikari.minimum-idle`
- If timeout or lifetime settings exist in the descriptor, map to `connection_timeout_ms`, `idle_timeout_ms`, `max_lifetime_ms` and corresponding HikariCP properties

### WebLogic

Connection pool settings appear in WebLogic datasource configuration (console or JNDI datasource XML). Map `Initial Capacity`, `Maximum Capacity`, and timeout settings to `ExternalResourceRequirement.connection_pool` and `spring.datasource.hikari.*` (maximum-pool-size, minimum-idle, connection-timeout, idle-timeout, max-lifetime, auto-commit).

### WebSphere / Tomcat

- **WebSphere**: Pool settings in datasource or connection pool configuration in the admin console or config files. Map to `connection_pool` and HikariCP properties.
- **Tomcat**: In `context.xml` `<Resource>`, attributes such as `maxTotal`, `maxIdle` map to `maximum_pool_size`, `minimum_idle`. Map to `ExternalResourceRequirement.connection_pool` and `spring.datasource.hikari.*`.

### Schema target

For each datasource with pool configuration:
- Populate `ExternalResourceRequirement.connection_pool` (maximum_pool_size, minimum_idle, connection_timeout_ms, idle_timeout_ms, max_lifetime_ms, auto_commit when available).
- Populate `spring_boot_config.properties` with `spring.datasource.hikari.maximum-pool-size`, `spring.datasource.hikari.minimum-idle`, `spring.datasource.hikari.connection-timeout`, `spring.datasource.hikari.idle-timeout`, `spring.datasource.hikari.max-lifetime`, `spring.datasource.hikari.auto-commit`.
