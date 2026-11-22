# telegrambot-java-template

A Spring Boot template for building Telegram bots in Java. Provides a ready-to-use project skeleton with all the essential dependencies, configuration profiles, and boilerplate wired up — so you can skip the setup and go straight to writing bot logic.

## Stack

- **Java 25** · **Spring Boot 3.5.14**
- **TelegramBots 9.5.0** — long-polling via `telegrambots-springboot-longpolling-starter` + extensions
- **Spring Data JPA** + **PostgreSQL** — persistence
- **Flyway** — database migrations (`src/main/resources/db/migration/`)
- **Lombok** — boilerplate reduction
- **H2** — in-memory database for tests
- **JaCoCo** — code coverage reports

## Project structure

```
src/
├── main/
│   ├── java/com/example/telegrambot/
│   │   ├── TelegramBotApplication.java   # Spring Boot entrypoint
│   │   ├── bot/
│   │   │   └── TelegramBot.java          # Long-polling bot skeleton
│   │   └── config/
│   │       └── AppConfig.java            # TelegramClient & Clock beans
│   └── resources/
│       ├── application.yml               # Base config
│       ├── application-dev.yml           # Dev profile (verbose logging)
│       ├── application-prod.yml          # Prod profile (Hikari tuning)
│       └── db/migration/                 # Flyway SQL migrations
└── test/
    ├── java/com/example/telegrambot/
    └── resources/
        └── application-test.yml          # Test profile (H2, Flyway off)
```

## Getting started

1. **Clone / use as template**
2. **Rename the package** — replace `com.example.telegrambot` with your own package name throughout the source tree
3. **Update `pom.xml`** — set your `groupId`, `artifactId`, and `version`
4. **Configure the bot token** — create a `.env` file in the project root (it is gitignored):
   ```properties
   TELEGRAM_BOT_TOKEN=your_token_here
   SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/yourdb
   ```
5. **Add your first migration** — create `src/main/resources/db/migration/V1__init.sql`
6. **Run** with the `dev` Spring profile for local development:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```

## Configuration

| Property | Default | Description |
|---|---|---|
| `TELEGRAM_BOT_TOKEN` | *(required)* | Bot token from [@BotFather](https://t.me/BotFather) |
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/telegrambot` | JDBC URL |
| `APP_TIMEZONE` | `UTC` | Timezone used by the `Clock` bean |

