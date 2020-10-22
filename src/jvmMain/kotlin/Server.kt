import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import desktop.launchLocalApp
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.jackson.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.jetty.*
import kotlinx.html.*
import org.hl7.fhir.utilities.VersionUtilities
import org.hl7.fhir.validation.ValidationEngine
import org.hl7.fhir.validation.ValidatorCli
import org.hl7.fhir.validation.cli.model.CliContext
import org.hl7.fhir.validation.cli.utils.Common
import org.hl7.fhir.validation.cli.utils.Params
import routes.*
import java.util.concurrent.TimeUnit

lateinit var cliContext: CliContext
lateinit var engine: JettyApplicationEngine

var runningAsDesktopStandalone: Boolean = false

fun main(args: Array<String>) {
    if (args.isNotEmpty() && !Params.hasParam(args, "-gui")) {
        ValidatorCli.main(args)
    } else {
        runningAsDesktopStandalone = Params.hasParam(args, "-gui")
        startServer(args)
    }
}

fun startServer(args: Array<String>) {
    val applicationEnvironment = commandLineEnvironment(args)
    engine = JettyApplicationEngine(applicationEnvironment) {
        loadCommonConfiguration(applicationEnvironment.config)
    }
    engine.start(true)
}

fun stopServer() {
    engine.stop(0, 5, TimeUnit.SECONDS)
}

fun Application.module() {
    init(CliContext())
}

/**
 * Entry Point of the application. This function is referenced in the resources/application.conf file.
 */
fun Application.init(context: CliContext) {
    // Any DB initialization will go here.

    // Any event subscriptions or other setup will go here.
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
            /*
             * Right now we need to ignore unknown fields because we take a very simplified version of many of the fhir
             * model classes, and map them to classes across JVM/Common/JS.
             */
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }
    }

    routing {
        get("/") {
            call.respondHtml(HttpStatusCode.OK, HTML::index)
        }
        static("/static") {
            resources()
        }
        resources()
        validationRoutes()
        versionRoutes()
        igRoutes()
        debugRoutes()
    }
}

fun HTML.index() {
    head {
        meta {
            charset = "UTF-8"
        }
        title("Validator GUI")
        link(
            href = "https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap",
            rel = "stylesheet"
        )
        link(
            href = "https://fonts.googleapis.com/css2?family=Source+Code+Pro:ital,wght@0,200;0,300;0,400;0,500;0,600;0,700;0,900;1,200;1,300;1,400;1,500;1,600;1,700;1,900&display=swap",
            rel = "stylesheet"
        )
    }
    body {
        div {
            id = "root"
        }
        script(src = "/static/output.js") {}
    }
}