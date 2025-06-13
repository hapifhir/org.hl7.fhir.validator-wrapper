import api.ApiInjection
import controller.ControllersInjection
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.jetty.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import utils.PackageCacheDownloaderRunnable
import java.util.concurrent.TimeUnit

private const val FULL_STACK_FLAG = "-startServer"
private const val LOCAL_APP_FLAG = "-gui"

lateinit var engine: JettyApplicationEngine

var runningAsDesktopStandalone: Boolean = false

/**
 * Here we define Java app main method. There are three possible ways this jar can be utilized:
 *
 * **1. As a full-stack hosted server:**
 *
 * Execute the jar by providing the argument `'-startServer'`. This boots the Ktor validation back end and KotlinJS
 * front end. Refer to the application.conf file in the resources directory to view the different deployment flavours
 * available. These deployment types can be set through the environment variable "ENVIRONMENT". If no such environment
 * variable is set, the application will default to a 'dev' type deployment.
 *
 * **2. As a locally run, short-lived, 'desktop' application:**
 *
 * Execute the jar by providing the argument `'-gui'`. This boots the Ktor server locally on the port 8080, and starts a
 * wrapped instance of the KotlinJS front end within a Chromium web window to appear as a desktop application. This
 * wrapped website should mimic all the same functionality of the full KotlinJS website as described in the first
 * option. Once the Chromium browser window is closed, the local Ktor server is stopped.
 *
 * **N.B.**
 * If you attempt to run this as both a full-stack server, and a locally hosted application, the full-stack server
 * takes priority, and the desktop version will not be booted.
 */
fun main(args: Array<String>) {
    runningAsDesktopStandalone = runningAsDesktopApp(args)
    startServer(args)
}

fun startServer(args: Array<String>) {

    val ktorConfig = ValidatorApplicationConfig.ktorConfig

    val preloadCache = System.getenv()["PRELOAD_CACHE"] ?: "false"

    if (preloadCache != null && preloadCache.equals("true")) {

        Thread(PackageCacheDownloaderRunnable()).start();
    }

    engine = embeddedServer(Jetty, host = ktorConfig.host, port = ktorConfig.port) {
        println("Starting instance in ${ktorConfig.host}:${ktorConfig.port}")
        module {
            install(Koin) {
                modules(
                    ApiInjection.koinBeans,
                    ControllersInjection.koinBeans
                )
            }
        }
        module()
    }

    engine.start(wait = true)

}

/**
 * Force shutdown the Ktor server.
 */
fun stopServer() {
    engine.stop(0, 5, TimeUnit.SECONDS)
}



private fun runningAsDesktopApp(args: Array<String>): Boolean {
    return args.isNotEmpty() && args.contains(LOCAL_APP_FLAG) && !args.contains(FULL_STACK_FLAG)
}



