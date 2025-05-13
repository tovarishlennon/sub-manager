# Инструкция запуска

## Docker

### Сначала собираем jar
```bash
mvn clean package -DskipTests
```

### Потом запускаем образ
```bash
docker compose up --build
```
