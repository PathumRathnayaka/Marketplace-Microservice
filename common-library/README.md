# Cloud POS Common Library

Shared Java library for the Cloud POS microservice ecosystem.

This module is a plain Maven JAR. It is not a runnable Spring Boot application and does not contain controllers, repositories, database configuration, service business logic, Eureka server code, gateway routing, or messaging infrastructure.

## Maven Dependency

Other services can depend on this module after it is built or installed by the parent reactor:

```xml
<dependency>
    <groupId>com.cloudpos</groupId>
    <artifactId>common-library</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## Shared Components

- Base audit model: `BaseEntity`
- Response models: `ApiResponse`, `ErrorResponse`, `PageResponse`
- Shared enums: `UserRole`, `OfferStatus`, `LowStockStatus`, `SupplierSource`
- Event DTOs prepared for future RabbitMQ or Kafka usage
- Common runtime exceptions
- Security constants, authenticated user model, and JWT helpers
- Email, phone, UUID, date/time, logging, and correlation ID utilities

## Architecture Notes

This module is prepared for future distributed tracing, centralized logging, monitoring, gateway authentication validation, and message-based communication. Those integrations are intentionally not implemented here so each microservice can choose its runtime configuration.
