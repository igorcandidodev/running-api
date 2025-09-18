# Running API

API para gestão de planos de treino de corrida desenvolvida com Spring Boot, integrando autenticação OAuth2, webhooks do Strava, IA para geração de treinos e processamento assíncrono com RabbitMQ.

## 🚀 Principais Funcionalidades

### 🔐 Autenticação & Gestão de Usuários
- **Autenticação Local**: Login/registro com email/senha e tokens JWT
- **Google OAuth2**: Integração com contas Google para login simplificado
- **Integração Strava**: Conexão com contas Strava para sincronização de atividades
- **Reset de Senha**: Sistema de recuperação por email

### 🏃‍♂️ Gestão de Treinos
- **Planos de Treino com IA**: Geração personalizada usando OpenAI (Spring AI)
- **Acompanhamento**: Registro e monitoramento de treinos realizados
- **Objetivos**: Criação e gestão de metas de corrida
- **Consultas Periódicas**: Busca de dados por intervalo de datas

### 🤖 IA & Personalização
- **Templates de Prompt**: Gestão de prompts personalizados para IA
- **Geração Baseada em Formulário**: Criação considerando nível, objetivos e limitações
- **Recomendações Inteligentes**: IA adapta planos às necessidades específicas

### 🔗 Integrações Externas
- **Webhooks Strava**: Sincronização em tempo real de atividades
- **Notificações Email**: Emails transacionais via Gmail SMTP
- **Mensageria RabbitMQ**: Processamento assíncrono de eventos

## 🏗️ Arquitetura

### Stack Tecnológica
- **Java 21** + **Spring Boot 3.4.x**
- **Spring Security** (JWT + OAuth2)
- **Spring Data JPA** + **PostgreSQL**
- **Spring AMQP** + **RabbitMQ**
- **Spring AI** + **OpenAI**
- **SpringDoc OpenAPI** (Swagger)
- **Maven** + **Docker**

### Estrutura do Projeto
```
src/main/java/com/runningapi/runningapi/
├── configuration/     # Configurações de Swagger, Strava, filas
├── controllers/       # Endpoints REST (Auth, Training, Objectives, etc.)
├── security/          # JWT, filtros, configurações de segurança
├── dto/              # Data Transfer Objects
├── model/            # Entidades JPA
├── repository/       # Repositórios de dados
├── service/          # Lógica de negócio
├── queue/            # Processamento RabbitMQ
└── utils/            # Utilitários
```

## ⚙️ Configuração

### Pré-requisitos
- Java 21+
- Maven 3.9+
- PostgreSQL 14+
- RabbitMQ 3.8+

### Configuração de Ambiente

Copie `application.sample.properties` para `application.properties` e configure:

#### Banco de Dados
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/runningapi
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
```

#### Segurança JWT
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
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

#### OpenAI
```properties
spring.ai.openai.api-key=sk-your-openai-key
```

## 🚀 Executando a Aplicação

### Localmente com Maven

1. **Inicie a infraestrutura:**
```bash
docker compose up -d postgres-db rabbitmq
```

2. **Execute a aplicação:**
```bash
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

### Com Docker

1. **Build da imagem:**
```bash
docker build -t runningapi:local .
```

2. **Execute o container:**
```bash
docker run --rm -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/runningapi \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=postgres \
  -e SPRING_SECURITY_SECRET_TOKEN=change-me \
  -e SPRING_AI_OPENAI_API_KEY=sk-*** \
  runningapi:local
```

## 📚 Documentação da API

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI Spec**: http://localhost:8080/v3/api-docs

**Autenticação**: Bearer JWT (`Authorization: Bearer <token>`)

## 🔐 Endpoints de Autenticação

### Login Local

- **URL:** `/api/v1/auth/local/login`
- **Method:** `POST`
- **Request Body:**

```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

- **Response:**

```json
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
- **Response:** `302 Redirect` para consentimento Google

### Google OAuth2 Callback

- **URL:** `/api/v1/auth/google/callback`
- **Method:** `GET`
- **Parameters:**

```json
{
  "code": "..."
}
```

- **Response:** `302 Redirect` com token JWT no fragmento da URL

## 🏃‍♂️ Endpoints Principais

### Criar Treino com IA

- **URL:** `/api/v1/training/`
- **Method:** `POST`
- **Headers:** `Authorization: Bearer <token>`
- **Request Body:**

```json
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
  ]
}
```

- **Response:**

```json
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
  ],
  "createdAt": "2024-01-15T10:30:00"
}
```

### Listar Treinos do Usuário

- **URL:** `/api/v1/training/`
- **Method:** `GET`
- **Headers:** `Authorization: Bearer <token>`
- **Response:**

```json
[
  {
    "id": 1,
    "type": "EASY_RUN",
    "duration": 30,
    "distance": 3000,
    "description": "Corrida leve de aquecimento",
    "scheduledDate": "2024-01-15"
  }
]
```

### Treinos por Período

- **URL:** `/api/v1/training/period/`
- **Method:** `GET`
- **Headers:** `Authorization: Bearer <token>`
- **Parameters:**

```json
{
  "startPeriod": "YYYY-MM-DD",
  "endPeriod": "YYYY-MM-DD"
}
```

- **Response:**

```json
[
  {
    "id": 1,
    "type": "EASY_RUN",
    "duration": 30,
    "distance": 3000,
    "description": "Corrida leve de aquecimento",
    "scheduledDate": "2024-01-15"
  }
]
```

## 🎯 Endpoints de Objetivos

### Criar Objetivo

- **URL:** `/api/v1/objectives/`
- **Method:** `POST`
- **Headers:** `Authorization: Bearer <token>`
- **Request Body:**

```json
{
  "description": "Correr 5km em 25 minutos",
  "targetDate": "2024-06-01",
  "targetDistance": 5000,
  "targetTime": "00:25:00"
}
```

- **Response:**

```json
{
  "id": 1,
  "description": "Correr 5km em 25 minutos",
  "targetDate": "2024-06-01",
  "targetDistance": 5000,
  "targetTime": "00:25:00",
  "status": "ACTIVE",
  "createdAt": "2024-01-15T10:30:00"
}
```

### Listar Objetivos

- **URL:** `/api/v1/objectives/`
- **Method:** `GET`
- **Headers:** `Authorization: Bearer <token>`
- **Response:**

```json
[
  {
    "id": 1,
    "description": "Correr 5km em 25 minutos",
    "targetDate": "2024-06-01",
    "targetDistance": 5000,
    "targetTime": "00:25:00",
    "status": "ACTIVE",
    "createdAt": "2024-01-15T10:30:00"
  }
]
```

### Cancelar Objetivo

- **URL:** `/api/v1/objectives/cancel/{id}`
- **Method:** `DELETE`
- **Headers:** `Authorization: Bearer <token>`
- **Response:** `204 No Content`

## 📊 Endpoints de Treinos Realizados

### Registrar Treino Realizado

- **URL:** `/api/v1/training-performed/{idTraining}`
- **Method:** `POST`
- **Headers:** `Authorization: Bearer <token>`
- **Request Body:**

```json
{
  "duration": 1800,
  "distance": 5000,
  "pace": "05:30",
  "heartRate": 150,
  "notes": "Treino realizado no parque"
}
```

- **Response:**

```json
{
  "id": 1,
  "trainingId": 1,
  "duration": 1800,
  "distance": 5000,
  "pace": "05:30",
  "heartRate": 150,
  "notes": "Treino realizado no parque",
  "performedAt": "2024-01-15T18:30:00"
}
```

## 🤖 Endpoints de Templates de Prompt

### Criar Template

- **URL:** `/prompt-templates/`
- **Method:** `POST`
- **Headers:** `Authorization: Bearer <token>`
- **Request Body:**

```json
{
  "name": "Plano 5K Iniciante",
  "description": "Template para planos 5K iniciantes",
  "promptText": "Crie um plano de {timeFrame} para corredor {fitnessLevel} com objetivo de {targetDistance}m...",
  "category": "TRAINING_PLAN"
}
```

- **Response:**

```json
{
  "id": 1,
  "name": "Plano 5K Iniciante",
  "description": "Template para planos 5K iniciantes",
  "promptText": "Crie um plano de {timeFrame} para corredor {fitnessLevel} com objetivo de {targetDistance}m...",
  "category": "TRAINING_PLAN",
  "createdAt": "2024-01-15T10:30:00"
}
```

### Listar Templates

- **URL:** `/prompt-templates/`
- **Method:** `GET`
- **Headers:** `Authorization: Bearer <token>`
- **Response:**

```json
[
  {
    "id": 1,
    "name": "Plano 5K Iniciante",
    "description": "Template para planos 5K iniciantes",
    "category": "TRAINING_PLAN",
    "createdAt": "2024-01-15T10:30:00"
  }
]
```

### Atualizar Template

- **URL:** `/prompt-templates/{id}`
- **Method:** `PUT`
- **Headers:** `Authorization: Bearer <token>`
- **Request Body:**

```json
{
  "name": "Plano 5K Iniciante Atualizado",
  "description": "Template atualizado para planos 5K iniciantes",
  "promptText": "Prompt atualizado...",
  "category": "TRAINING_PLAN"
}
```

- **Response:**

```json
{
  "id": 1,
  "name": "Plano 5K Iniciante Atualizado",
  "description": "Template atualizado para planos 5K iniciantes",
  "promptText": "Prompt atualizado...",
  "category": "TRAINING_PLAN",
  "updatedAt": "2024-01-15T11:30:00"
}
```

### Obter Template por ID

- **URL:** `/prompt-templates/{id}`
- **Method:** `GET`
- **Headers:** `Authorization: Bearer <token>`
- **Response:**

```json
{
  "id": 1,
  "name": "Plano 5K Iniciante",
  "description": "Template para planos 5K iniciantes",
  "promptText": "Crie um plano de {timeFrame} para corredor {fitnessLevel}...",
  "category": "TRAINING_PLAN",
  "createdAt": "2024-01-15T10:30:00"
}
```

### Deletar Template

- **URL:** `/prompt-templates/{id}`
- **Method:** `DELETE`
- **Headers:** `Authorization: Bearer <token>`
- **Response:** `204 No Content`

## 🔑 Endpoints de Reset de Senha

### Solicitar Reset de Senha

- **URL:** `/api/v1/password-reset/request`
- **Method:** `POST`
- **Parameters:**

```json
{
  "email": "user@example.com"
}
```

- **Response:** `200 OK` (Email enviado com código de reset)

### Confirmar Reset de Senha

- **URL:** `/api/v1/password-reset/reset`
- **Method:** `POST`
- **Request Body:**

```json
{
  "code": "RESET123",
  "password": "newPassword123"
}
```

- **Response:** `200 OK`

## 👥 Endpoints de Usuários

### Criar Usuário

- **URL:** `/api/v1/user/`
- **Method:** `POST`
- **Request Body:**

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

- **Response:**

```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "createdAt": "2024-01-15T10:30:00"
}
```

## 🔗 Integração Strava

### Iniciar OAuth2 Strava

- **URL:** `/api/v1/auth/strava/{userId}`
- **Method:** `GET`
- **Response:** `302 Redirect` para autorização Strava

### Callback Strava

- **URL:** `/api/v1/auth/strava/callback`
- **Method:** `GET`
- **Parameters:**

```json
{
  "code": "...",
  "state": "<userId>"
}
```

- **Response:** `200 OK` com dados de conexão

### Validação Webhook Strava

- **URL:** `/api/v1/strava/athlete-activity/webhook`
- **Method:** `GET`
- **Parameters:**

```json
{
  "hub.mode": "subscribe",
  "hub.challenge": "...",
  "hub.verify_token": "..."
}
```

- **Response:** `200 OK` com challenge quando token válido

### Recebimento de Eventos Webhook

- **URL:** `/api/v1/strava/athlete-activity/webhook`
- **Method:** `POST`
- **Request Body:**

```json
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

## 📨 Sistema de Mensageria

### RabbitMQ - Configuração de Filas
- **Exchange**: `exchange.activity.update.strava`
- **Routing Keys**: 
  - `key.activity.created.strava`
  - `key.activity.updated.strava`
  - `key.activity.deleted.strava`
- **Retry**: Configurável via `rabbitmq.webhook.activity.strava.retry`

## 🛠️ Desenvolvimento

### Segurança
- **Endpoints Públicos**: `/swagger-ui/**`, `/api/v1/auth/**`, `/api/v1/strava/**`, `POST /api/v1/user/`
- **Endpoints Protegidos**: Todos os demais requerem JWT válido
- **JWT**: Emissor "Moove API", expiração +1 hora

### Configuração de CORS
CORS permissivo por padrão - refinar para produção.

## 🐛 Troubleshooting

| Problema | Solução |
|----------|---------|
| Erro conexão RabbitMQ | Verificar host/porta (5672) e credenciais |
| Erro conexão PostgreSQL | Verificar porta 5432 e existência do DB |
| 401 Unauthorized | Verificar header Authorization: Bearer \<token\> |
| Swagger não carrega | Verificar se app roda na porta 8080 |
| Callbacks OAuth2 | URIs de redirect devem coincidir exatamente |

## 📄 Licença

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).
