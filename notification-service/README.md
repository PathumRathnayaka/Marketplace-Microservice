# Cloud POS Notification Service

Enterprise notification infrastructure microservice for the Cloud POS SaaS ecosystem.

This service only handles notification delivery concerns: email, SMS preparation, push preparation, notification history, status tracking, and retry orchestration. It does not contain POS or supplier business logic.

## Runtime

- Service name: `notification-service`
- Port: `8084`
- Swagger UI: `http://localhost:8084/swagger-ui.html`
- Health: `GET http://localhost:8084/api/notification/health`

## APIs

- `POST /api/notifications/send`
- `GET /api/notifications`
- `GET /api/notifications/{id}`
- `PUT /api/notifications/{id}/read`
- `DELETE /api/notifications/{id}`
- `POST /api/email/send`
- `POST /api/email/send-template`
- `POST /api/sms/send`

## Configuration

Database, SMTP, and RabbitMQ values are environment-variable friendly. The service uses PostgreSQL with Hibernate `ddl-auto=update` for the current development phase.

Important environment variables:

- `NOTIFICATION_DB_URL`
- `NOTIFICATION_DB_USERNAME`
- `NOTIFICATION_DB_PASSWORD`
- `SMTP_HOST`
- `SMTP_PORT`
- `SMTP_USERNAME`
- `SMTP_PASSWORD`
- `RABBITMQ_HOST`
- `RABBITMQ_PORT`

## Future Prepared Areas

- RabbitMQ consumers
- Kafka integration
- WhatsApp delivery
- Firebase push notifications
- Retry queues and dead letter queues
- Notification analytics
- Distributed tracing
- Monitoring
