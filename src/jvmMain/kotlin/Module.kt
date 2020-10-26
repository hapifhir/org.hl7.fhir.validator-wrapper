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
import kotlinx.html.*
import org.slf4j.event.Level
import routes.debugRoutes
import routes.igRoutes
import routes.validationRoutes
import routes.versionRoutes

/**
 * Entry point of the application. This function is referenced in the resources/application.conf file (see the line
 * that says `modules = [ ModuleKt.module ]`, pointing to this method.
 */
fun Application.module() {
    // Any DB initialization will go here.
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

/**
 * Application extension function where we configure Ktor application with features, interceptors and routing.
 */
fun Application.start() {

    install(CallLogging) {
        level = Level.DEBUG
    }

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

    install(Routing) {
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
        // Only enable if things have gone horribly, and you need to add a debug logging endpoint.
        //debugRoutes()
    }
}