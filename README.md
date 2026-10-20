# LLD Playground (Spring Boot Application)

A fully working Spring Boot application tailored for Low-Level Design (LLD) experiments and object-oriented design practice.

---

## 🛠️ Tech Stack & Requirements

- **Java**: OpenJDK 21 LTS
- **Framework**: Spring Boot 4.1.x
- **Build Tool**: Apache Maven (Wrapper included: `mvnw.cmd` / `mvnw`)
- **Key Modules**:
  - `spring-boot-starter-webmvc` (RESTful Web APIs)
  - `spring-boot-starter-actuator` (Health checks and metrics)
  - `lombok` (Boilerplate reduction for models and DTOs)
  - `spring-boot-starter-test` & JUnit 5 (Unit and integration testing)

---

## 🚀 Getting Started

### 1. Run the Application
You don't need Maven installed globally; use the included wrapper:

**Windows (PowerShell or CMD):**
```powershell
.\mvnw.cmd spring-boot:run
```

**macOS / Linux / Git Bash:**
```bash
./mvnw spring-boot:run
```

The application starts on port `8080` by default.

---

### 2. Run Tests
To run unit and integration tests:

```powershell
.\mvnw.cmd test
```

---

### 3. Build Executable JAR
To package into a production-ready runnable JAR:

```powershell
.\mvnw.cmd package
```
Run the JAR directly:
```powershell
java -jar target/lld-playground-0.0.1-SNAPSHOT.jar
```

---

## 🌐 Sample Endpoints

Once running, access the following endpoints:

| Endpoint | Method | Description | Example URL |
|---|---|---|---|
| `/api/hello` | GET | Sample REST endpoint with greeting & status | `http://localhost:8080/api/hello?name=Developer` |
| `/actuator/health` | GET | Health status of the application | `http://localhost:8080/actuator/health` |
| `/actuator/info` | GET | Application metadata and info | `http://localhost:8080/actuator/info` |

---

## 📂 Project Structure

```text
LLD_PLAYGROUND/
├── .mvn/                     # Maven wrapper configuration & binaries
├── src/
│   ├── main/
│   │   ├── java/com/lld/playground/
│   │   │   ├── LldPlaygroundApplication.java   # Spring Boot entry point
│   │   │   ├── controller/
│   │   │   │   └── HelloController.java        # REST Controller
│   │   │   ├── dto/
│   │   │   │   └── ApiResponse.java            # Standardized API response
│   │   │   └── RateLimiter/
│   │   │       └── RateLimiter.java            # LLD workspace example
│   │   └── resources/
│   │       └── application.properties          # Server port & config
│   └── test/
│       └── java/com/lld/playground/
│           ├── LldPlaygroundApplicationTests.java
│           └── HelloControllerTest.java
├── mvnw                      # Shell script for Linux/macOS
├── mvnw.cmd                  # Batch script for Windows
├── pom.xml                   # Maven dependencies and plugins
└── README.md
```
