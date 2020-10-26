import com.typesafe.config.ConfigFactory
import io.ktor.config.*
import io.ktor.server.engine.*
import io.ktor.server.jetty.*
import io.ktor.util.*
import org.hl7.fhir.validation.ValidatorCli
import org.hl7.fhir.validation.cli.utils.Params
import java.util.concurrent.TimeUnit

private const val DEFAULT_ENVIRONMENT: String = "dev"
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
 * **3. As the traditional validator clr:**
 *
 * Users can still execute this jar as done previously, from the command line and access all validator cli
 * functionality as detailed here: `https://confluence.hl7.org/display/FHIR/Using+the+FHIR+Validator`
 *
 * **N.B.**
 * If you attempt to run this as both a full-stack server, and a locally hosted application, the full-stack server
 * takes priority, and the desktop version will not be booted.
 */
fun main(args: Array<String>) {
    when {
        runningAsCli(args) -> {
            ValidatorCli.main(args)
        }
        else -> {
            runningAsDesktopStandalone = runningAsDesktopApp(args)
            startServer(args)
        }
    }
}

fun startServer(args: Array<String>) {
    val environment = System.getenv()["ENVIRONMENT"] ?: handleDefaultEnvironment()
    val config = extractConfig(environment, HoconApplicationConfig(ConfigFactory.load()))

    embeddedServer(Jetty,host = config.host, port = config.port) {
        println("Starting instance in ${config.host}:${config.port}")
        module()
    }.start(wait = true)
}

/**
 * Force shutdown the Ktor server.
 */
fun stopServer() {
    engine.stop(0, 5, TimeUnit.SECONDS)
}

private fun runningAsCli(args: Array<String>): Boolean {
    return args.isNotEmpty() && !Params.hasParam(args, LOCAL_APP_FLAG) && !Params.hasParam(args, FULL_STACK_FLAG)
}

private fun runningAsDesktopApp(args: Array<String>): Boolean {
    return args.isNotEmpty() && Params.hasParam(args, LOCAL_APP_FLAG) && !Params.hasParam(args, FULL_STACK_FLAG)
}

data class Config(
    val host: String,
    val port: Int,
)

@KtorExperimentalAPI
fun extractConfig(environment: String, hoconConfig: HoconApplicationConfig): Config {
    val hoconEnvironment = hoconConfig.config("ktor.deployment.$environment")
    return Config(
        hoconEnvironment.property("host").getString(),
        Integer.parseInt(hoconEnvironment.property("port").getString()),
    )
}

/**
 * Returns default environment.
 */
fun handleDefaultEnvironment(): String {
    println("Falling back to default environment 'dev'")
    return DEFAULT_ENVIRONMENT
}