# Moove (Running) API

API for managing running training plans, goals, and performed trainings, built with Spring Boot. It integrates Google OAuth2, Strava webhooks, AI-based plan generation, and asynchronous processing with RabbitMQ.

## 🚀 Key Features

### 🔐 Authentication & User Management
- Local authentication: Email/password signup and login with JWT tokens.
- Google OAuth2: Seamless login with Google accounts.
- Strava integration: Connect Strava accounts for activity sync.
- Password reset: Email-based recovery flow.

### 🏃‍♂️ Training Management
- AI-powered training plans: Personalized plan generation using OpenAI (Spring AI).
- Training tracking: Record and monitor performed trainings.
- Objectives: Create and manage running goals.
- Period queries: Retrieve data by date ranges.

### 🤖 AI & Customization
- Prompt templates: Manage customizable prompts for the AI.
- Form-based generation: Consider fitness level, goals, and constraints.
- Intelligent recommendations: AI adapts plans to user needs.

### 🔗 External Integrations
- Strava webhooks: Real-time activity synchronization.
- Email notifications: Transactional emails via Gmail SMTP.
- RabbitMQ messaging: Asynchronous event processing.

## 🏗️ Architecture

### Tech Stack
- Java 21 and Spring Boot 3.4.x.
- Spring Security (JWT + OAuth2).
- Spring Data JPA and PostgreSQL.
- Spring AMQP and RabbitMQ.
- Spring AI and OpenAI.
- SpringDoc OpenAPI (Swagger).
- Maven and Docker.

### Project Structure
```
src/main/java/com/runningapi/runningapi/
├── configuration/     # Swagger, Strava, and queue settings.
├── controllers/       # REST endpoints (Auth, Training, Objectives, etc.).
├── security/          # JWT, filters, and security configuration.
├── dto/               # Data Transfer Objects.
├── model/             # JPA entities.
├── repository/        # Data repositories.
├── service/           # Business logic.
├── queue/             # RabbitMQ processing.
└── utils/             # Utilities.
```

## ⚙️ Configuration

### Prerequisites
- Java 21+.
- Maven 3.9+.
- PostgreSQL 14+.
- RabbitMQ 3.8+.

### Environment Configuration

Copy `application.sample.properties` to `application.properties` and set the values.

#### Database
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/runningapi
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
```

#### Security (JWT)
```properties
spring.security.secret.token=your-secret-token
```

#### Google OAuth2
```properties
spring.security.oauth2.client.registration.google.client-id=your-client-id
spring.security.oauth2.client.registration.google.client-secret=your-client-secret
spring.security.oauth2.client.registration.google.redirect-uri=http://localhost:8080/api/v1/auth/google/callback
```

#### Strava
```properties
strava.client.id=your-strava-client-id
strava.client.secret=your-strava-client-secret
strava.client.redirect_uri=http://localhost:8080/api/v1/auth/strava/callback
strava.client.verify_token_callback=your-webhook-verify-token
```

#### RabbitMQ
```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

#### Email (Gmail SMTP)
```properties
spring.mail.host=xxxx.xxxxx.com
spring.mail.port=xxx
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

#### OpenAI
```properties
spring.ai.openai.api-key=sk-your-openai-key
```

> Never commit secrets. Use environment variables or a secrets manager.

## 🚀 Running the Application

### Locally with Maven
```bash
1. Start the infrastructure:
```

2. **Execute a aplicação:**
```bash
2. Run the application:
```

A API estará disponível em: `http://localhost:8080`

The API will be available at: `http://localhost:8080`.

### With Docker
```bash
1. Build the image:
```

2. **Execute o container:**
```bash
2. Run the container:
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/runningapi \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=postgres \
  -e SPRING_SECURITY_SECRET_TOKEN=change-me \
  -e SPRING_AI_OPENAI_API_KEY=sk-*** \
  runningapi:local
```

## 📚 Documentação da API

## 📚 API Documentation
- **OpenAPI Spec**: http://localhost:8080/v3/api-docs
- Swagger UI: http://localhost:8080/swagger-ui/index.html.
- OpenAPI Spec: http://localhost:8080/v3/api-docs.

Authentication: Bearer JWT (`Authorization: Bearer <token>`).

## 🔐 Authentication Endpoints

### Local Login
- **Method:** `POST`
- URL: `/api/v1/auth/local/login`
- Method: `POST`
- Request Body:
{
  "email": "user@example.com",
  "password": "password123"
}
```

- **Response:**

- Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "email": "user@example.com",
    "name": "John Doe"
  }
}
```

### Google OAuth2 Redirect

- **URL:** `/api/v1/auth/google/`
- **Method:** `GET`
- URL: `/api/v1/auth/google/`
- Method: `GET`
- Response: `302 Redirect` to Google consent.

- **URL:** `/api/v1/auth/google/callback`
- **Method:** `GET`
- URL: `/api/v1/auth/google/callback`
- Method: `GET`
- Parameters:
{
  "code": "..."
}
```

- **Response:** `302 Redirect` com token JWT no fragmento da URL

- Response: `302 Redirect` with a JWT token in the URL fragment.

## 🏃‍♂️ Core Endpoints

### Create Training with AI
- **Method:** `POST`
- URL: `/api/v1/training/`
- Method: `POST`
- Headers: `Authorization: Bearer <token>`
- Request Body:
{
  "fitnessLevel": "BEGINNER",
  "timeFrame": "4_WEEKS",
  "targetDistance": 5000,
  "availableDays": ["MONDAY", "WEDNESDAY", "FRIDAY"],
  "physicalLimitations": [
    {
      "type": "KNEE_INJURY",
      "description": "Lesão no joelho direito"
    }
      "description": "Right knee injury"
}
```

- **Response:**

- Response:
{
  "id": 1,
  "trainings": [
    {
      "day": "MONDAY",
      "type": "EASY_RUN",
      "duration": 30,
      "distance": 3000,
      "description": "Corrida leve de aquecimento"
    }
      "description": "Easy warm-up run"
  "createdAt": "2024-01-15T10:30:00"
}
```

### Listar Treinos do Usuário

### List User Trainings
- **Method:** `GET`
- URL: `/api/v1/training/`
- Method: `GET`
- Headers: `Authorization: Bearer <token>`
- Response:
[
  {
    "id": 1,
    "type": "EASY_RUN",
    "duration": 30,
    "distance": 3000,
    "description": "Corrida leve de aquecimento",
    "scheduledDate": "2024-01-15"
    "description": "Easy warm-up run",
]
```

### Treinos por Período

### Trainings by Period
- **Method:** `GET`
- URL: `/api/v1/training/period/`
- Method: `GET`
- Headers: `Authorization: Bearer <token>`
- Parameters:
{
  "startPeriod": "YYYY-MM-DD",
  "endPeriod": "YYYY-MM-DD"
}
```

- **Response:**

- Response:
[
  {
    "id": 1,
    "type": "EASY_RUN",
    "duration": 30,
    "distance": 3000,
    "description": "Corrida leve de aquecimento",
    "scheduledDate": "2024-01-15"
    "description": "Easy warm-up run",
]
```

## 🎯 Endpoints de Objetivos

## 🎯 Objectives Endpoints

### Create Objective
- **Method:** `POST`
- URL: `/api/v1/objectives/`
- Method: `POST`
- Headers: `Authorization: Bearer <token>`
- Request Body:
{
  "description": "Correr 5km em 25 minutos",
  "targetDate": "2024-06-01",
  "description": "Run 5km in 25 minutes",
  "targetTime": "00:25:00"
}
```

- **Response:**

- Response:
{
  "id": 1,
  "description": "Correr 5km em 25 minutos",
  "targetDate": "2024-06-01",
  "description": "Run 5km in 25 minutes",
  "targetTime": "00:25:00",
  "status": "ACTIVE",
  "createdAt": "2024-01-15T10:30:00"
}
```

### Listar Objetivos

### List Objectives
- **Method:** `GET`
- URL: `/api/v1/objectives/`
- Method: `GET`
- Headers: `Authorization: Bearer <token>`
- Response:
[
  {
    "id": 1,
    "description": "Correr 5km em 25 minutos",
    "targetDate": "2024-06-01",
    "description": "Run 5km in 25 minutes",
    "targetTime": "00:25:00",
    "status": "ACTIVE",
    "createdAt": "2024-01-15T10:30:00"
  }
]
```

### Cancelar Objetivo

### Cancel Objective
- **Method:** `DELETE`
- URL: `/api/v1/objectives/cancel/{id}`
- Method: `DELETE`
- Headers: `Authorization: Bearer <token>`
- Response: `204 No Content`.

## 📊 Performed Trainings Endpoints

### Register Performed Training
- **Method:** `POST`
- URL: `/api/v1/training-performed/{idTraining}`
- Method: `POST`
- Headers: `Authorization: Bearer <token>`
- Request Body:
{
  "duration": 1800,
  "distance": 5000,
  "pace": "05:30",
  "heartRate": 150,
  "notes": "Treino realizado no parque"
}
  "notes": "Park training session"

- **Response:**

- Response:
{
  "id": 1,
  "trainingId": 1,
  "duration": 1800,
  "distance": 5000,
  "pace": "05:30",
  "heartRate": 150,
  "notes": "Treino realizado no parque",
  "performedAt": "2024-01-15T18:30:00"
  "notes": "Park training session",
```

## 🤖 Endpoints de Templates de Prompt

## 🤖 Prompt Templates Endpoints

### Create Template
- **Method:** `POST`
- URL: `/prompt-templates/`
- Method: `POST`
- Headers: `Authorization: Bearer <token>`
- Request Body:
{
  "name": "Plano 5K Iniciante",
  "description": "Template para planos 5K iniciantes",
  "name": "Beginner 5K Plan",
  "description": "Template for beginner 5K plans",
  "promptText": "Create a {timeFrame} plan for a {fitnessLevel} runner targeting {targetDistance}m...",
```

- **Response:**

- Response:
{
  "id": 1,
  "name": "Plano 5K Iniciante",
  "description": "Template para planos 5K iniciantes",
  "name": "Beginner 5K Plan",
  "description": "Template for beginner 5K plans",
  "promptText": "Create a {timeFrame} plan for a {fitnessLevel} runner targeting {targetDistance}m...",
}
```

### Listar Templates

### List Templates
- **Method:** `GET`
- URL: `/prompt-templates/`
- Method: `GET`
- Headers: `Authorization: Bearer <token>`
- Response:
[
  {
    "id": 1,
    "name": "Plano 5K Iniciante",
    "description": "Template para planos 5K iniciantes",
    "name": "Beginner 5K Plan",
    "description": "Template for beginner 5K plans",
  }
]
```

### Atualizar Template

### Update Template
- **Method:** `PUT`
- URL: `/prompt-templates/{id}`
- Method: `PUT`
- Headers: `Authorization: Bearer <token>`
- Request Body:
{
  "name": "Plano 5K Iniciante Atualizado",
  "description": "Template atualizado para planos 5K iniciantes",
  "name": "Updated Beginner 5K Plan",
  "description": "Updated template for beginner 5K plans",
  "promptText": "Updated prompt...",
```

- **Response:**

- Response:
{
  "id": 1,
  "name": "Plano 5K Iniciante Atualizado",
  "description": "Template atualizado para planos 5K iniciantes",
  "name": "Updated Beginner 5K Plan",
  "description": "Updated template for beginner 5K plans",
  "promptText": "Updated prompt...",
}
```

### Obter Template por ID

### Get Template by ID
- **Method:** `GET`
- URL: `/prompt-templates/{id}`
- Method: `GET`
- Headers: `Authorization: Bearer <token>`
- Response:
{
  "id": 1,
  "name": "Plano 5K Iniciante",
  "description": "Template para planos 5K iniciantes",
  "name": "Beginner 5K Plan",
  "description": "Template for beginner 5K plans",
  "promptText": "Create a {timeFrame} plan for a {fitnessLevel} runner...",
}
```

### Deletar Template

### Delete Template
- **Method:** `DELETE`
- URL: `/prompt-templates/{id}`
- Method: `DELETE`
- Headers: `Authorization: Bearer <token>`
- Response: `204 No Content`.

## 🔑 Password Reset Endpoints

### Request Password Reset
- **Method:** `POST`
- URL: `/api/v1/password-reset/request`
- Method: `POST`
- Parameters:
{
  "email": "user@example.com"
}
```

- **Response:** `200 OK` (Email enviado com código de reset)

- Response: `200 OK` (Email sent with reset code).

### Confirm Password Reset
- **Method:** `POST`
- URL: `/api/v1/password-reset/reset`
- Method: `POST`
- Request Body:
{
  "code": "RESET123",
  "password": "newPassword123"
}
```

- **Response:** `200 OK`

- Response: `200 OK`.

## 👥 Users Endpoints

### Create User
- **Method:** `POST`
- URL: `/api/v1/user/`
- Method: `POST`
- Request Body:
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

- **Response:**

- Response:
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "createdAt": "2024-01-15T10:30:00"
}
```

## 🔗 Integração Strava

## 🔗 Strava Integration

### Start Strava OAuth2
- **Method:** `GET`
- URL: `/api/v1/auth/strava/{userId}`
- Method: `GET`
- Response: `302 Redirect` to Strava authorization.

### Strava Callback
- **Method:** `GET`
- URL: `/api/v1/auth/strava/callback`
- Method: `GET`
- Parameters:
{
  "code": "...",
  "state": "<userId>"
}
```

- **Response:** `200 OK` com dados de conexão

- Response: `200 OK` with connection data.

### Strava Webhook Validation
- **Method:** `GET`
- URL: `/api/v1/strava/athlete-activity/webhook`
- Method: `GET`
- Parameters:
{
  "hub.mode": "subscribe",
  "hub.challenge": "...",
  "hub.verify_token": "..."
}
```

- **Response:** `200 OK` com challenge quando token válido

- Response: `200 OK` echoing the challenge when the verify token matches.

### Strava Webhook Event Intake
- **Method:** `POST`
- URL: `/api/v1/strava/athlete-activity/webhook`
- Method: `POST`
- Request Body:
{
  "object_type": "activity",
  "object_id": 12345,
  "aspect_type": "create",
  "owner_id": 67890,
  "subscription_id": 1,
  "event_time": 1234567890
}
```

- **Response:** `200 OK`

- Response: `200 OK`.

## 📨 Messaging
- **Exchange**: `exchange.activity.update.strava`
### RabbitMQ - Queue Configuration
- Exchange: `exchange.activity.update.strava`.
- Routing Keys:
  - `key.activity.created.strava`.
  - `key.activity.updated.strava`.
  - `key.activity.deleted.strava`.
- Retry: configurable via `rabbitmq.webhook.activity.strava.retry`.

## 🛠️ Development
- **Endpoints Públicos**: `/swagger-ui/**`, `/api/v1/auth/**`, `/api/v1/strava/**`, `POST /api/v1/user/`
### Security
- Public endpoints: `/swagger-ui/**`, `/api/v1/auth/**`, `/api/v1/strava/**`, `POST /api/v1/user/`.
- Protected endpoints: All others require a valid JWT.
- JWT: Issuer "Moove API", expiration +1 hour.
CORS permissivo por padrão - refinar para produção.
### CORS
Permissive by default; change for production.

| Problema | Solução |
|----------|---------|
| Issue | Resolution |
|-------|------------|
| RabbitMQ connection error. | Verify host/port (5672) and credentials. |
| PostgreSQL connection error. | Verify port 5432 and database existence. |
| 401 Unauthorized. | Ensure the Authorization: Bearer <token> header is set and valid. |
| Swagger not loading. | Check the app is running on port 8080. |
| OAuth2 callbacks failing. | Ensure redirect URIs match exactly. |

## 📄 License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).