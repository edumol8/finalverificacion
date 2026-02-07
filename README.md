# Music Library API (Java + Maven + JUnit)

API REST para una **biblioteca de música digital** (CRUD de canciones).

## Requisitos
- Java 17
- Maven 3.9+

## Ejecutar
```bash
mvn spring-boot:run
```
API: `http://localhost:8080`

## Endpoints (CRUD)
- `GET /api/songs`
- `GET /api/songs/{id}`
- `POST /api/songs`
- `PUT /api/songs/{id}`
- `DELETE /api/songs/{id}`

## Ejemplos (JSON)
### POST
```json
{
  "title": "Style",
  "artist": "Taylor Swift",
  "album": "1989",
  "year": 2014,
  "durationSeconds": 231
}
```

## Tests + Calidad
```bash
mvn clean verify
```
Incluye:
- JUnit (tests)
- JaCoCo (cobertura)
- Checkstyle (calidad)
- Reportes: `target/site/jacoco/index.html`

## SonarQube (local)
1. Levanta SonarQube (ejemplo con Docker):
```bash
docker run -d --name sonarqube -p 9000:9000 sonarqube:lts-community
```
2. Ejecuta:
```bash
mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=TU_TOKEN
```

## Jenkins
Incluye `Jenkinsfile` para pipeline con: build + test + sonar.

## Seguridad (Burp Suite)
- Probar endpoints y documentar hallazgos (ej. validación de entradas).
