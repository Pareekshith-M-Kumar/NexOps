# NexOps — AI-Native DevOps & LLMOps Platform

NexOps is an AI-native platform designed to centralize Large Language Model (LLM) access, monitor AI usage, track token consumption and costs, and enforce application-level budgets and rate limits.

The project combines backend engineering, AI integration, usage analytics, and infrastructure technologies to explore how AI applications can be monitored, governed, and operated more reliably.

## Project Objectives

- Centralize LLM requests through a single AI gateway.
- Track token consumption, request latency, and AI usage costs.
- Monitor application-level AI spending and enforce budgets.
- Control request frequency using Redis-based rate limiting.
- Analyze prompt length to identify potential token optimization opportunities.
- Build a foundation for AI governance, observability, and DevOps automation.

## Features

### 1. AI Gateway

- Integrates with the Groq API for LLM inference.
- Routes AI requests through a centralized Spring Boot backend.
- Supports configurable model selection and output token limits.
- Records request status and latency.

### 2. Token Usage Tracking

- Tracks input, output, and total tokens returned by the provider.
- Stores AI request history in MySQL.
- Provides APIs to retrieve request logs and usage analytics.

### 3. AI Cost Management

- Calculates request costs using configurable model pricing.
- Separates input-token and output-token pricing.
- Aggregates AI usage costs for reporting.

### 4. Budget Management

- Configures spending budgets for individual applications.
- Tracks used and remaining budget.
- Calculates budget utilization.
- Identifies normal, warning, and exceeded budget states.
- Rejects requests when the estimated cost exceeds the available budget.

### 5. Redis-Based Rate Limiting

- Tracks request counts per application using Redis.
- Enforces a configurable request limit within a time window.
- Uses key expiration to reset counters automatically.
- Rejects requests that exceed the configured limit.

### 6. Application Management

- Provides CRUD operations for registered applications.
- Uses DTOs to separate API input from persistence entities.
- Includes application status and timestamps.
- Implements centralized exception handling.

### 7. AI Usage Analytics

- Aggregates total AI requests.
- Tracks input, output, and total token consumption.
- Calculates total AI usage cost.
- Calculates average request latency.

### 8. Token Optimization Foundation

- Estimates prompt token usage.
- Classifies prompts by estimated token consumption.
- Establishes a foundation for future optimization recommendations.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java | Backend programming |
| Spring Boot | REST API and application services |
| Spring Data JPA | Database persistence |
| MySQL | Application data, request logs, pricing, and budgets |
| Redis | Request counters and rate limiting |
| Groq API | LLM inference |
| Maven | Dependency management and build |
| Git and GitHub | Version control and source management |
| Docker | Containerization and local infrastructure |

Additional technologies planned for the DevOps and observability phases include Docker Compose, GitHub Actions, Kubernetes, Prometheus, and Grafana.

## Architecture

```text
Client Application
       |
       v
Spring Boot AI Gateway
       |
       v
Request Validation and Token Limits
       |
       v
Redis Rate Limiting
       |
       v
Application Budget Check
       |
       v
Groq LLM API
       |
       v
Token Usage and Cost Calculation
       |
       v
MySQL Request Logging
       |
       v
AI Usage Analytics
```

The optimization service provides an initial prompt-analysis capability. A unified frontend dashboard and production-grade infrastructure are planned for later development phases.

## Project Structure

The backend follows a modular package structure:

```text
src/main/java/com/nexops/
├── application/       # Application management
├── gateway/           # AI gateway and request logging
├── cost/              # Model pricing and cost calculation
├── budget/            # Budget management and enforcement
├── analytics/         # AI usage analytics
├── optimization/      # Prompt token analysis
└── rate/              # Redis-based rate limiting
```

The exact package contents may evolve as development continues.

## Prerequisites

Install the following before running the project:

- Java version compatible with the configured Spring Boot project
- Maven, or use the included Maven Wrapper if available
- MySQL
- Docker Desktop
- A Groq API key

Redis can run locally through Docker.

## Setup and Installation

### 1. Clone the repository

```bash
git clone https://github.com/Pareekshith-M-Kumar/NexOps.git
cd NexOps
```

### 2. Configure MySQL

Create a MySQL database for NexOps and configure the connection settings in `src/main/resources/application.properties`.

Use your own database credentials. Do not commit passwords or other secrets.

### 3. Configure environment variables

Set the Groq API key as an environment variable.

In Windows PowerShell:

```powershell
$env:GROQ_API_KEY="your-groq-api-key"
```

Replace the placeholder with your actual key. This sets the variable for the current terminal session.

Never publish your API key in source code, screenshots, or GitHub commits.

### 4. Start Redis

If your Redis Docker container is already configured, start it:

```bash
docker start my-redis
```

Verify that Redis responds:

```bash
docker exec -it my-redis redis-cli ping
```

Expected output:

```text
PONG
```

If you do not have the container yet, create one:

```bash
docker run -d --name my-redis -p 6379:6379 redis
```

If a container named `my-redis` already exists, use `docker start my-redis` instead of creating another one.

### 5. Run the Spring Boot application

From the project root, run the Maven Wrapper if it is included.

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Alternatively, if Maven is installed:

```bash
mvn spring-boot:run
```

Make sure MySQL and Redis are running and the required configuration is correct before starting the application.

## API Endpoints

The following endpoints are implemented in the backend:

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/ai/chat` | Send an AI request through the gateway |
| GET | `/api/ai/logs` | Retrieve AI request history |
| GET | `/api/ai/analytics/summary` | Retrieve aggregated AI usage analytics |
| POST | `/api/budgets` | Create an application budget |
| GET | `/api/budgets/{applicationId}` | Retrieve budget details |
| GET | `/api/budgets/{applicationId}/status` | Retrieve budget status |

Application CRUD endpoints are also available under `/api/applications`.

### Example AI Request

```http
POST http://localhost:8080/api/ai/chat
Content-Type: application/json
```

```json
{
  "applicationId": 4,
  "provider": "GROQ",
  "model": "openai/gpt-oss-20b",
  "prompt": "Explain what a REST API is in one sentence.",
  "maxOutputTokens": 200
}
```

The application ID must refer to an existing application with a configured budget.

The selected model must be available to your Groq account, and its pricing must be configured in the database.

## Roadmap

The following areas represent the planned development direction. They should be marked complete only after implementation and verification.

- [ ] Spring Boot foundation and application management
- [ ] AI gateway integration
- [ ] Token usage tracking and request logging
- [ ] AI request cost calculation
- [ ] Application-level budgets and enforcement
- [ ] AI usage analytics
- [ ] Redis-based rate limiting
- [ ] Initial prompt token analysis
- [ ] Persistent optimization recommendations and measurable savings analysis
- [ ] Expanded governance policies and audit logging
- [ ] AI adoption and ROI metrics
- [ ] Docker Compose deployment
- [ ] Automated CI/CD using GitHub Actions
- [ ] Kubernetes deployment
- [ ] Prometheus and Grafana observability
- [ ] Unified dashboard
- [ ] Final integration testing and deployment documentation

## Future Vision

NexOps aims to evolve into a unified platform for operating AI applications, combining LLM observability, cost management, usage governance, optimization, and DevOps monitoring in one system.

## Author

**Pareekshith M. Kumar**

GitHub: [Pareekshith-M-Kumar](https://github.com/Pareekshith-M-Kumar)

Project Repository: [NexOps](https://github.com/Pareekshith-M-Kumar/NexOps)
