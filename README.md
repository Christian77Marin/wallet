# Wallet

## Getting Started

### Prerequisites

- Java 21 
- Maven
- PostgreSQL 
- Stripe Secret Key

**1. Clone the repository:**
```shell
git clone https://github.com/Christian77Marin/wallet.git
```
### Initialize

**1. Build the app:**
```shell
mvn clean install
```

**2. Build the app:**
```shell
mvn spring-boot:run
```
 
## PostgreSQL Server Database / Stripe
**1. Modify `application.yml`:**
```shell
spring:
  datasource:
    url: ${URL_JDBC}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
    driver-class-name: org.postgresql.Driver
  stripe:
    secret-key: ${STRIPE_KEY}
```


