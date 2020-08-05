import com.fasterxml.jackson.databind.SerializationFeature
import desktop.launchLocalApp
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.jackson.jackson
import org.hl7.fhir.utilities.VersionUtilities
import org.hl7.fhir.validation.ValidationEngine
import org.hl7.fhir.validation.cli.model.CliContext
import org.hl7.fhir.validation.cli.utils.Common
import org.hl7.fhir.validation.cli.utils.Params
import routes.contextRoutes
import routes.validationRoutes

lateinit var validationEngine: ValidationEngine
lateinit var cliContext: CliContext

var runningAsDesktopStandalone: Boolean = false

fun main(args: Array<String>) {
    if (Params.hasParam(args, "-gui")) {
        runningAsDesktopStandalone = true
    }
    startServer(args)
}

fun startServer(args: Array<String>) {
    io.ktor.server.jetty.EngineMain.main(args)
}

fun Application.module() {
    val v = VersionUtilities.CURRENT_FULL_VERSION
    val definitions = VersionUtilities.packageForVersion(v) + "#" + v
    val engine = Common.getValidationEngine(v, definitions, null)
    init(false, engine, CliContext())
}

/**
 * Entry Point of the application. This function is referenced in the resources/application.conf file.
 */
fun Application.init(testing: Boolean = false, engine: ValidationEngine, context: CliContext) {
    // Any DB initialization will go here.

    // Any event subscriptions or other setup will go here.
    // Set ValidationEngine
    validationEngine = engine
    cliContext = context

    val starting: (Application) -> Unit = { log.info("Application starting: $it") }
    val started: (Application) -> Unit = {
        log.info("Application started: $it")
        if (runningAsDesktopStandalone) {
            launchLocalApp()
        }
    }
    val stopping: (Application) -> Unit = { log.info("Application stopping: $it") }
    var stopped: (Application) -> Unit = {}

    stopped = {
        log.info("Application stopped: $it")
        environment.monitor.unsubscribe(ApplicationStarting, starting)
        environment.monitor.unsubscribe(ApplicationStarted, started)
        environment.monitor.unsubscribe(ApplicationStopping, stopping)
        environment.monitor.unsubscribe(ApplicationStopped, stopped)
    }

    environment.monitor.subscribe(ApplicationStarted, starting)
    environment.monitor.subscribe(ApplicationStarted, started)
    environment.monitor.subscribe(ApplicationStopping, stopping)
    environment.monitor.subscribe(ApplicationStopped, stopped)

    // Now we call to a main with the dependencies as arguments.
    // Separating this function with its dependencies allows us to provide several modules with
    // the same code and different data-sources living in the same application,
    // and to provide mocked instances for doing integration tests.
    start()
}

fun Application.start() {

    install(CallLogging)

    install(CORS) {
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Delete)
        anyHost()
    }

    install(Compression) {
        gzip()
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    routing {
        get("/") {
            call.respondText(
                    this::class.java.classLoader.getResource("index.html")!!.readText(),
                    ContentType.Text.Html
            )
        }
        static("/") {
            resources("")
        }

        contextRoutes()
        validationRoutes()
    }
}