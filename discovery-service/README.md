# Cloud POS Discovery Service

Eureka Discovery Server for the SaaS Cloud POS microservice ecosystem.

This service is responsible only for service registration and discovery. It does not contain business logic, database access, JWT authentication, messaging, or RabbitMQ integration.

## Purpose

- Register Cloud POS microservices in one service registry.
- Let services discover each other dynamically.
- Replace hardcoded service URLs with logical service names.
- Prepare the architecture for scaling, load balancing, Docker, Kubernetes, distributed tracing, and gateway integration.

## Runtime

- Application name: `discovery-service`
- HTTP port: `8761`
- Eureka dashboard: `http://localhost:8761`
- Health endpoint: `GET http://localhost:8761/discovery/health`

Health response:

```json
{
  "status": "UP",
  "service": "Discovery Service"
}
```

## Service Registration

Client services register with Eureka by adding the Eureka client dependency and this configuration:

```yaml
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/

spring:
  application:
    name: pos-service
```

Use these service names for the current ecosystem:

- `api-gateway`
- `auth-service`
- `pos-service`
- `supplier-service`

After registration, the gateway can route by logical service name:

```text
gateway -> lb://pos-service
gateway -> lb://supplier-service
gateway -> lb://auth-service
```

## Adding A New Service

1. Add the Eureka client dependency to the new service.
2. Set a unique `spring.application.name`.
3. Configure `eureka.client.service-url.defaultZone`.
4. Start `discovery-service` before the dependent services.
5. Confirm the service appears in the Eureka dashboard.

## Docker Preparation

This module includes a lightweight `Dockerfile` for future containerization. Build and deployment orchestration are intentionally not configured yet.
