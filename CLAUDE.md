# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is the FHIR Validator Wrapper - a Kotlin Multiplatform project that provides CLI, Desktop GUI, and Web-based validation server for FHIR resources. It combines:
- **JVM backend** using Ktor for REST API and validation engine (wraps HAPI FHIR validation library)
- **JS frontend** using React/Redux with Kotlin/JS and Material-UI
- **Common shared code** for models and constants

A publicly hosted instance: https://validator.fhir.org/

## Build & Development Commands

### Building
```bash
./gradlew build              # Full build (JVM + JS)
./gradlew jvmJar             # Build JVM JAR only
./gradlew jsJar              # Build JS frontend only
./gradlew clean              # Clean build artifacts
```

### Running
```bash
./gradlew run                # Run local server (localhost:8080)
java -jar build/libs/validator-wrapper-jvm-*.jar -startServer   # Full-stack server
java -jar build/libs/validator-wrapper-jvm-*.jar -gui           # Desktop app mode
```

### Testing
```bash
./gradlew test               # Run all tests
./gradlew jvmTest            # JVM tests only
./gradlew jsTest             # JS tests only (uses Karma + Chrome Headless)
./gradlew jsBrowserTest      # JS browser tests
```

To run a single test class:
```bash
./gradlew jvmTest --tests "ClassName"
./gradlew jvmTest --tests "controller.validation.*"
```

### Version Management
Version is managed via semantic versioning plugin. Check `version.properties` for current version.
```bash
./gradlew printVersion       # Print current version
```

## Architecture Overview

### Kotlin Multiplatform Structure

The codebase uses Kotlin's `expect`/`actual` pattern to share code across platforms:

```
src/
├── commonMain/              # Shared models, constants, expected declarations
│   ├── kotlin/
│   │   ├── model/          # Data models (ValidationRequest, ValidationResponse, etc.)
│   │   └── constants/      # API endpoints, FHIR formats, MIME types
│   └── resources/          # Static content (index.html, Swagger UI)
├── jvmMain/                # JVM server implementation
│   ├── kotlin/
│   │   ├── Server.kt       # Main entry point (3 execution modes)
│   │   ├── Module.kt       # Ktor configuration
│   │   ├── controller/     # REST route handlers (by feature)
│   │   ├── api/            # Dependency injection (Koin)
│   │   └── utils/          # Caching, file handling
├── jsMain/                 # React/Redux frontend
│   ├── kotlin/
│   │   ├── Main.kt         # Bootstrap entry
│   │   ├── App.kt          # Root React component
│   │   ├── api/            # HTTP client (Ktor client)
│   │   ├── reactredux/     # Redux state management
│   │   │   ├── store/      # AppState, reducers, thunks
│   │   │   ├── slices/     # Redux slices (state + reducers per domain)
│   │   │   └── containers/ # React-Redux connected components
│   │   └── ui/components/  # React UI hierarchy
```

### JVM Backend Architecture

**Entry Point & Execution Modes** (Server.kt:39):
- `-startServer`: Full-stack hosted server (default)
- `-gui`: Desktop app with embedded Chromium window
- No args: Defaults to server mode

**Dependency Injection** (Koin):
- Controllers and services are injected via `ControllersInjection.kt` and `ApiInjection.kt`
- Enables testability and loose coupling

**Feature-Based Controllers**:
Each feature has a dedicated controller directory with:
- Interface definition
- Implementation class
- Module.kt with Ktor route definitions

Example structure:
```
controller/
├── validation/
│   ├── ValidationController.kt          # Interface
│   ├── ValidationControllerImpl.kt      # Implementation
│   └── ValidationModule.kt              # Routes: post("/validate")
├── ig/                                  # Implementation Guides
├── version/                             # Version info
└── terminology/                         # Terminology server validation
```

**Key Backend Classes**:
- `ValidationServiceFactory`: Creates validation service instances (wraps HAPI FHIR validator)
- `SessionCacheFactory`: Manages session-based caching with Guava
- `PackageCacheDownloaderRunnable`: Preloads package cache on startup (controlled by `PRELOAD_CACHE` env var)

### JS Frontend Architecture

**Redux State Management**:

The frontend uses Redux for centralized state management with the following slices:

```kotlin
AppState {
    localizationSlice          // i18n with Polyglot.js
    validationSessionSlice     // Session tracking
    validationContextSlice     // Validation config (FHIR version, IGs, profiles)
    manualEntrySlice          // Manual resource editor state
    uploadedResourceSlice     // File upload state
    appScreenSlice            // Current screen (VALIDATOR vs SETTINGS)
    presetsSlice              // Validation preset configurations
}
```

**State Flow**:
1. User interaction in React component
2. Dispatch action (or thunk for async)
3. Reducer updates state (pure function)
4. Connected components re-render with new state

**Async Operations**:
- Uses Redux-Thunk middleware for async actions
- Example: `FetchPolyglotThunk` fetches translations, dispatches `LocalizationSlice.SetPolyglot`
- Validation requests: `sendValidationRequest()` in `ValidatorApi.kt`

**UI Component Hierarchy**:
- `App.kt`: Root component, receives Redux state via `rConnect`
- `ui/components/`: Presentational components
  - `header/`: Language selector, status indicator
  - `tabs/`: Upload tab, manual entry tab
  - `options/`: Settings/configuration menus
  - `validation/`: Results display, issue list
- Uses Material-UI (MUI) via Kotlin wrappers
- Styling via kotlin-styled (CSS-in-JS)

### API Communication Flow

```
React Component
    ↓ dispatch action/thunk
Redux Thunk (async)
    ↓ HTTP request
Ktor HTTP Client (JS)
    ↓ POST /validate
Ktor Server Routes (JVM)
    ↓ DI injection
ValidationController
    ↓ factory
ValidationServiceFactory
    ↓ calls
HAPI FHIR Validation Library
    ↓ response
ValidationResponse (JSON)
    ↓ deserialize
Redux State Update
    ↓ re-render
Component Update
```

**Key API Endpoints**:
- `POST /validate`: Submit validation request
- `GET /validator/version`: App & validator versions
- `GET /validator/presets`: Available validation presets
- `GET /ig`: List implementation guides (with search)
- `GET /igVersions/{packageName}`: Versions of specific IG
- `GET /versions`: Available FHIR versions
- `POST /terminology`: Validate terminology server URL

### Multiplatform Data Models

Models use `expect`/`actual` pattern:
- **Common** (src/commonMain): Define contracts with `expect class`
- **JVM** (src/jvmMain): Type aliases to HAPI FHIR library classes
- **JS** (src/jsMain): Custom Kotlin classes with `@Serializable`

Example: `ValidationContext`
- Common: `expect class ValidationContext`
- JVM: `actual typealias ValidationContext = org.hl7.fhir.validation.ValidationContext`
- JS: `actual data class ValidationContext(...) { /* serializable fields */ }`

### Important Patterns & Decisions

1. **Session Tracking**: `ValidationSessionSlice` maintains session IDs; `SessionCacheFactory` manages server-side caching

2. **Immutable State Updates**: Redux reducers always return new state objects (enables time-travel debugging)

3. **Preset Configuration System**: Preloaded validation configurations for common FHIR version + IG combinations

4. **Localization**: Browser's `navigator.languages` auto-detects preferred language; Polyglot.js handles translations

5. **Hybrid Rendering**: Server serves static HTML + compiled JS bundle; desktop mode embeds same frontend in Chromium

6. **Validation Context Builder Pattern**: `.setFhirVersion().setIGs().build()` for configuration

## Development Notes

### Dependency Version Compatibility

The project uses tightly coupled dependencies. **Always update related versions together**. See comments in `gradle.properties`:
- `kotlinVersion`, `kotlinxHtmlVersion`, `kotlinxCoroutinesVersion`, `kotlinxSerializationVersion` must align
- `ktorVersion`, `jacksonVersion`, `koinVersion` are interdependent
- Reference projects for safe version combinations:
  - https://github.com/kotlin-hands-on/jvm-js-fullstack
  - https://github.com/kotlin-hands-on/web-app-react-kotlin-js-gradle

### FHIR Core Version

The `fhirCoreVersion` in `gradle.properties` determines which HAPI FHIR validation library is used. This is the core validation engine.

### Environment Variables

- `ENVIRONMENT`: Deployment type (defaults to 'dev'). Check `application.conf` for available types.
- `PRELOAD_CACHE`: Set to "true" to preload package cache on startup (useful for production)

### When Adding New Features

**Backend (JVM)**:
1. Create controller interface + implementation in `controller/<feature>/`
2. Add route definitions in `<Feature>Module.kt`
3. Register in `Module.kt` (main Ktor module)
4. Add DI bindings in `ControllersInjection.kt` or `ApiInjection.kt`

**Frontend (JS)**:
1. Define state shape in new slice (or extend existing) in `reactredux/slices/`
2. Add actions and reducer logic
3. Create thunk for async operations if needed
4. Add API function in `api/ValidatorApi.kt`
5. Connect component using `rConnect` in `containers/`
6. Implement UI in `ui/components/`

### Testing

- JVM tests use JUnit 5 + MockK for mocking
- JS tests use Karma with Chrome Headless
- Ktor provides `testApplication` for integration testing routes
- Use `io.ktor:ktor-server-test-host` for testing Ktor endpoints

## CI/CD

- **Pull Request Pipeline**: Builds project on every PR (`pull-request-pipeline.yml`)
- **Master Branch Pipeline**: Builds SNAPSHOT binaries on merge to master (`master-branch-pipeline.yml`)
- **Release Branch Pipeline**: Manual trigger for releases, publishes to GitHub, DockerHub, updates hosted validator (`release-branch-pipeline.yml`)

Hosted on Azure Pipelines: https://dev.azure.com/fhir-pipelines/validator-wrapper

## Additional Resources

- Detailed documentation: https://hl7.github.io/docs/validator-wrapper
- REST API documentation: https://validator.fhir.org/swagger-ui/index.html
- FHIR Validator usage: https://confluence.hl7.org/display/FHIR/Using+the+FHIR+Validator
- GitHub issues: https://github.com/hapifhir/org.hl7.fhir.validator-wrapper/issues
