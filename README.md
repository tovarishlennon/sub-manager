# Инструкция запуска **Sub Manager**

> Полностью пошаговое руководство — в формате Markdown.  
> Используйте его как `README.md` или просто откройте в любом редакторе.

---

## Содержание
1. [Запуск через Docker (рекомендуется)](#docker)
2. [Запуск локально без Docker](#local)
3. [Частые команды](#common)
4. [Устранение неполадок](#troubleshoot)

---

## <a id="docker"></a>1. Запуск через Docker + docker‑compose

| Что нужно | Проверка |
|-----------|----------|
| **Docker Engine ≥ 24** | `docker --version` |
| **docker compose** | `docker compose version` <br>*(CLI v2+ уже содержит compose)* |

```bash
git clone https://github.com/ваш‑репозиторий/sub-manager.git
cd sub-manager

# ① Сборка Spring‑Boot JAR
mvn clean package -DskipTests        # target/sub-manager-0.0.1-SNAPSHOT.jar

# ② Старт контейнеров (база + приложение)
docker compose up --build
```

После строки  
`Started SubManagerApplication in … seconds`  
проверьте:

```bash
curl http://localhost:8080/actuator/health
# {"status":"UP"}
```

**Остановка**

```bash
Ctrl+C                # прервать логи
docker compose down   # выключить
```

---

## <a id="local"></a>2. Запуск локально (Maven + PostgreSQL на хосте)

| Что нужно | Проверка |
|-----------|----------|
| **JDK 17+** | `java -version` |
| **Maven 3.9+** | `mvn -v` |
| **PostgreSQL 16** | `psql --version` |

```bash
# ① Поднимаем Postgres
brew services start postgresql@16                   # пример для macOS
createuser -s subuser
createdb subdb -O subuser
psql -c "ALTER USER subuser PASSWORD 'secret';"

# ② Запускаем Spring‑Boot
mvn spring-boot:run

# класс main: com.azim.submgr.SubManagerApplication
```

Если порт / пароль другие — поменяйте в `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:6543/subdb
    username: myuser
    password: mypass
```

---

## <a id="common"></a>3. Частые команды

| Цель                         | Команда |
|------------------------------|---------|
| Тесты + сборка               | `mvn clean verify` |
| Сборка без тестов            | `mvn package -DskipTests` |
| Логи только приложения (Docker) | `docker compose logs -f app` |
| Консоль psql в контейнере    | `docker exec -it sub-manager-db-1 psql -U subuser subdb` |

---

## <a id="troubleshoot"></a>4. Устранение неполадок

| Симптом | Решение |
|---------|---------|
| **Port 5432 занят** | В `docker-compose.yml` замените `5432:5432` на `6543:5432`; при локальном запуске поменяйте URL. |
| **Flyway `schema_history` конфликт** | Удалите таблицу `flyway_schema_history` или создайте новую БД (`subdb2`). |
| **M1/M2 Mac + Alpine JDK** | `eclipse-temurin:17-jre-alpine` работает на arm64. Проблем нет. |
| **Email already exists** | У каждого пользователя почта уникальна — используйте другой адрес или удалите запись. |

---

**Готово!**  
Теперь сервис доступен: `http://localhost:8080`.  
Если потребуется помощь — пишите.