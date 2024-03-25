import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import controller.conversion.conversionModule
import controller.debug.debugModule
import controller.ig.igModule
import controller.terminology.terminologyModule
import controller.uptime.uptimeModule
import controller.validation.validationModule
import controller.version.versionModule
import desktop.launchLocalApp
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.cors.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.server.http.content.*
import io.ktor.serialization.jackson.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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

    install(CallLogging)
    {

    }

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowNonSimpleContentTypes = true
        allowCredentials = true
        allowSameOrigin = true
        allowHost("*", listOf("http", "https"))
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
        igModule()
        versionModule()
        debugModule()
        validationModule()
        conversionModule()
        terminologyModule()
        uptimeModule()

        get("/") {
            call.respondText(
                this::class.java.classLoader.getResource("static-content/index.html")!!.readText(),
                ContentType.Text.Html
            )
        }


        get("/validator-wrapper.js") {
            call.respondText(
                this::class.java.classLoader.getResource("validator-wrapper.js")!!.readText(),
                ContentType.Text.JavaScript
            )
        }

        static("/") {
            resources("static-content")
        }
    }
}