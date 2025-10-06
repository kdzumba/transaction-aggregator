# Capitec Transaction Aggregator

This project provides an API for managing users, accounts, and transactions, built with Spring Boot and deployed using Docker.

## Prerequisites

* Docker and Docker Compose installed
* Java 17 (for local development if needed)
* Maven (for building locally if needed)

## Project Structure

* **Database Dockerfile**: `src/main/java/za/co/shyftit/capitectransactionaggregator/database/Dockerfile`
* **API Dockerfile & Docker Compose**: Located at the project root

## Getting Started

### 1. Build Docker Images

#### Build Database Image

```bash
cd src/main/java/za/co/shyftit/capitectransactionaggregator/database
docker build -t capitec-transaction-aggregator-db:1.0.0 .
```

#### Build API Image

```bash
cd [project_root]
docker build -t transactions_aggregator_api:1.0.2 .
```

### 2. Start Services with Docker Compose

```bash
docker-compose up -d
```

This will start:

* `transactions_aggregator_database` on port 5432
* `transactions_aggregator_api` on port 8080

### 3. Initial Setup

1. Create a new user by sending a POST request to the `/api/users/register` endpoint with `username` and `password`.
2. Log in via `/api/users/login` to receive an access token.

### 4. Using the API

All subsequent API requests must include the access token as a Bearer token in the `Authorization` header:

```
Authorization: Bearer <access_token>
```

Endpoints include:

* `/api/accounts` for managing accounts
* `/api/transactions` for creating and querying transactions

### 5. Environment Variables

Set the following variables in a `.env` file or in your environment before starting the containers:

```
TRANSACTION_AGGREGATOR_DATABASE_URL=<db_url>
TRANSACTION_AGGREGATOR_DB_USERNAME=<db_username>
TRANSACTION_AGGREGATOR_DB_PASSWORD=<db_password>
TRANSACTIONS_AGGREGATOR_SECRET_KEY=<secret_key>
JWT_ACCESS_ACTIVE_DURATION=<duration_in_seconds>
FLYWAY_MIGRATIONS_PATH=/data/sql_migrations
```

### 6. Logs

API logs can be accessed from the mounted volume:

```
/data/logs
```

## Notes

* Ensure the database service is running before making API requests.
* The API automatically runs Flyway migrations from `/data/sql_migrations` on startup.
* Ports:

    * API: 8080
    * Database: 5432
