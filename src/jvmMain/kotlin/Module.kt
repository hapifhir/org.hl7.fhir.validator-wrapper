import controller.debug.debugModule
import controller.ig.igModule
import controller.validation.validationModule
import controller.version.versionModule
import desktop.launchLocalApp
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.routing.*
import kotlinx.html.*
import org.slf4j.event.Level

/**
 * Entry point of the application.
 */
fun Application.module() {
    // Any DB initialization or logging initialization will go here.
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
    setup()
}

/**
 * Application extension function where we configure Ktor application with features, interceptors and routing.
 */
fun Application.setup() {

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
        gson {
           setLenient()
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

        debugModule()
        igModule()
        validationModule()
        versionModule()
    }
}