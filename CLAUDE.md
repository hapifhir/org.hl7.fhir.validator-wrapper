# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is the FHIR Validator Wrapper - a Kotlin Multiplatform project that provides CLI, Desktop GUI, and Web-based validation server for FHIR resources. It combines:
- **JVM backend** using Ktor for REST API and validation engine (wraps HAPI FHIR validation library)
- **JS frontend** using React with Context API (Kotlin/JS) and Material-UI
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
├── jsMain/                 # React frontend with Context API
│   ├── kotlin/
│   │   ├── Main.kt         # Bootstrap entry & context provider setup
│   │   ├── App.kt          # Root React component (consumes contexts)
│   │   ├── api/            # HTTP client (Ktor client)
│   │   ├── context/        # React Context providers & state
│   │   │   ├── AppScreenContext.kt      # Screen navigation state
│   │   │   ├── LocalizationContext.kt   # i18n & language state
│   │   │   └── ValidationContext.kt     # Validation state (files, config, IGs)
│   │   ├── ui/components/  # React UI hierarchy (context consumers)
│   │   ├── utils/          # Helper functions
│   │   └── css/            # Styling utilities
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

**React Context State Management**:

The frontend uses React's Context API for centralized state management with three context providers:

**Context Provider Hierarchy:**
```kotlin
// Main.kt - Provider nesting order
AppScreenProvider {           // Outermost - Screen navigation
  LocalizationProvider {      // Middle - i18n & translations
    ValidationProvider {      // Innermost - Validation state (most complex)
      App { ... }
    }
  }
}
```

**The Three Contexts:**

1. **AppScreenContext** (`context/AppScreenContext.kt`):
   - **State**: `appScreen: AppScreen` (VALIDATOR | SETTINGS enum)
   - **Callback**: `setAppScreen: (AppScreen) -> Unit`
   - **Purpose**: Controls which major screen is displayed

2. **LocalizationContext** (`context/LocalizationContext.kt`):
   - **State**: `polyglot: Polyglot`, `selectedLanguage: Language`, `isLoading: Boolean`
   - **Callback**: `setLanguage: (Language) -> Unit`
   - **Purpose**: Manages i18n translations and language selection
   - **Features**: Auto-detects browser language via `window.navigator.languages`, async loads translations via `getPolyglotPhrases()`

3. **ValidationContext** (`context/ValidationContext.kt`) - **Most Complex**:
   - **Configuration State**: `validationContext`, `sessionId`, `presets`, `igPackageInfoSet`, `extensionSet`, `profileSet`, `bundleValidationRuleSet`
   - **Manual Entry State**: `currentManualEntryText`, `manualValidationOutcome`, `manualValidatingInProgress`
   - **File Upload State**: `uploadedFiles: List<ValidationOutcome>`
   - **12+ Callback Functions**: `updateValidationContext()`, `setSessionId()`, `uploadFile()`, `deleteFile()`, `addValidationOutcome()`, `toggleFileValidationInProgress()`, `updateManualEntryText()`, `setManualValidationOutcome()`, `toggleManualValidationInProgress()`, `updateIgPackageInfoSet()`, `updateExtensionSet()`, `updateProfileSet()`, `updateBundleValidationRuleSet()`
   - **Purpose**: Manages all validation-related state (consolidates what were 5 Redux slices)

**State Flow Pattern:**
1. User interaction triggers event in React component
2. Component consumes Context via `Context.Consumer { ctx -> ... }`
3. Component calls callback function from context: `ctx?.updateValidationContext?.invoke(newValue)`
4. Provider's `setState { ... }` updates internal state
5. Context value changes, triggering re-render of ALL consuming components

**Async Operations:**
- Uses Kotlin Coroutines with `MainScope().launch { ... }` (replaces Redux-Thunk)
- Context providers can launch coroutines in init block (e.g., `ValidationProvider` fetches presets on mount)
- Components launch coroutines for API calls with proper error handling and timeout support

**Context Consumption Pattern:**
```kotlin
// Single context
LocalizationContext.Consumer { localizationContext ->
    val polyglot = localizationContext?.polyglot ?: Polyglot()
    // Use polyglot for translations
}

// Multiple contexts (nested)
LocalizationContext.Consumer { localizationContext ->
    ValidationContext.Consumer { validationContext ->
        val polyglot = localizationContext?.polyglot ?: Polyglot()
        val files = validationContext?.uploadedFiles ?: emptyList()

        Button {
            onClick = { validationContext?.uploadFile?.invoke(fileInfo) }
        }
    }
}
```

**UI Component Hierarchy**:
- `Main.kt`: Wraps `App` in 3 nested context providers
- `App.kt`: Root component, consumes all three contexts to route between screens
- `ui/components/`: React components consuming contexts as needed
  - `header/`: Language selector (`Header.kt`, `LanguageSelect.kt`), status indicators
  - `tabs/`: Upload tab (`FileUploadTab.kt`), manual entry tab (`ManualEntryTab.kt`)
  - `options/`: Settings page (`OptionsPage.kt`), IG selector, preset selector
  - `validation/`: Results display, issue list, filtered views
  - `buttons/`, `footer/`: Reusable components
- Uses Material-UI (MUI) via Kotlin wrappers (version `kotlinWrappersVersion=1.0.0-pre.561`)
- Styling via kotlin-styled (CSS-in-JS)
- Most components consume `LocalizationContext` for translations; validation-heavy components also consume `ValidationContext`

### API Communication Flow

```
React Component (consumes Context)
    ↓ invoke callback OR launch coroutine
MainScope().launch { ... }
    ↓ suspend function call
Ktor HTTP Client (JS) - suspend functions in api/ValidatorApi.kt
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
    ↓ callback invocation
Context Provider setState { ... }
    ↓ context value changes
All Consumer Components Re-render
```

**Key Differences from Previous Redux Architecture:**
- No centralized store - each context manages its own state
- No action types/dispatching - direct callback invocation
- No reducers - `setState` handles updates
- No Redux middleware - Kotlin coroutines handle async operations
- Simpler mental model with type-safe callbacks via Kotlin interfaces

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

1. **Session Tracking**: `ValidationContext` maintains session IDs via `sessionId` state; `SessionCacheFactory` manages server-side caching

2. **React State Immutability**: Context providers use React's `setState` to trigger re-renders; state updates create new objects using Kotlin's `.copy()` for data classes

3. **Preset Configuration System**: Preloaded validation configurations for common FHIR version + IG combinations

4. **Localization**: Browser's `navigator.languages` auto-detects preferred language; Polyglot.js handles translations

5. **Hybrid Rendering**: Server serves static HTML + compiled JS bundle; desktop mode embeds same frontend in Chromium

6. **Validation Context Builder Pattern**: `.setFhirVersion().setIGs().build()` for configuration

### React Context Migration (2025)

In January 2025, the project migrated from Redux to React Context API alongside the Kotlin 1.8.21 upgrade.

**Why the Migration:**
- Simplify state management (removed Redux, Redux-Thunk, and react-redux dependencies)
- Reduce bundle size and complexity
- Align with modern React patterns
- Improve maintainability with fewer abstraction layers

**Redux Slices → Context Providers Mapping:**
- `localizationSlice` → `LocalizationContext`
- `validationSessionSlice` + `validationContextSlice` + `manualEntrySlice` + `uploadedResourceSlice` + `presetsSlice` → **Consolidated into `ValidationContext`**
- `appScreenSlice` → `AppScreenContext`

**Key Pattern Changes:**

| Aspect | Redux (Before) | Context (After) |
|--------|----------------|-----------------|
| State Distribution | Store → mapStateToProps → Props | Context Provider → Consumer |
| Action Dispatching | `dispatch(UpdateState(value))` | `ctx?.updateState?.invoke(value)` |
| Async Operations | Redux-Thunk middleware | `MainScope().launch { ... }` with coroutines |
| Component Connection | `rConnect(mapStateToProps, mapDispatchToProps)` | `Context.Consumer { ctx -> ... }` |

**Important Implementation Notes:**
- Context consumers must handle nullable contexts with defaults: `val polyglot = ctx?.polyglot ?: Polyglot()`
- Components consuming multiple contexts use nested `Consumer` blocks (can be verbose)
- Any context update re-renders ALL consumers of that context (unlike Redux memoized selectors)
- `MainScope` defined at module level in `Main.kt` and reused across components for coroutines

**Related Commits:**
- `02f2762`: Remove redux dependencies
- `4edcec1`: Remove redux + minimal React context implementation
- `8c519c5`: Eliminate prop drilling by using context

## Development Notes

### Dependency Version Compatibility

The project uses tightly coupled dependencies. **Always update related versions together**. See comments in `gradle.properties`.

**Current Major Versions (as of Kotlin 1.8.21 upgrade):**
- `kotlinVersion = 1.8.21` (updated from 1.6.21)
- `kotlinxHtmlVersion = 0.8.0` (updated from 0.7.5)
- `kotlinxCoroutinesVersion = 1.7.1` (updated from 1.6.3)
- `kotlinxSerializationVersion = 1.5.1` (updated from 1.3.2)
- `kotlinWrappersVersion = 1.0.0-pre.561` (updated from 0.0.1-pre.332-kotlin-1.6.21)
- `ktorVersion = 2.3.0` (updated from 2.0.2)
- `jacksonVersion = 2.15.0` (updated from 2.12.6)
- `koinVersion = 3.4.0` (updated from 3.2.1)

**Important:** Redux and React-Redux dependencies have been completely removed. State management now uses React Context API.

**Build Configuration Updates:**
- `kotlin.js.compiler=ir` - Explicitly enables IR (Intermediate Representation) compiler for JavaScript
- `kotlin.daemon.jvmargs=-Xmx2048m` - Increases memory for Kotlin daemon
- CSS support uses new DSL: `cssSupport { enabled.set(true) }` instead of `cssSupport.enabled = true`

Reference projects for safe version combinations:
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
1. **Identify which context needs to change**:
   - `AppScreenContext`: For new screens/navigation
   - `LocalizationContext`: For new translations/languages
   - `ValidationContext`: For validation config, files, or results (most common)

2. **Update context provider** (`context/<ContextName>.kt`):
   - Add new state properties to the provider's `State` class
   - Add new callback functions to the context value interface (e.g., `external interface ValidationContextValue`)
   - Implement callbacks in the `render()` method, typically wrapping `setState { ... }`

3. **Add API function** in `api/ValidatorApi.kt` if server communication is needed (use `suspend` functions)

4. **Update or create component** in `ui/components/`:
   - Consume context(s) using `Context.Consumer { ctx -> ... }` pattern
   - Call callbacks to update state: `ctx?.updateSomething?.invoke(newValue)`
   - For async operations, use `MainScope().launch { ... }` with proper error handling
   - Always provide default values for null contexts: `val value = ctx?.value ?: default`

5. **Example: Adding file deletion to ValidationContext**:
   ```kotlin
   // In ValidationContext.kt provider
   val deleteFile: (FileInfo) -> Unit = { fileInfo ->
       setState {
           uploadedFiles = uploadedFiles.filter { it.fileInfo != fileInfo }
       }
   }
   contextValue.deleteFile = deleteFile

   // In component
   ValidationContext.Consumer { validationContext ->
       Button {
           onClick = { validationContext?.deleteFile?.invoke(fileInfo) }
       }
   }
   ```

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
