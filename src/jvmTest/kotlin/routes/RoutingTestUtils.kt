package routes

import init
import io.ktor.server.testing.*
import org.hl7.fhir.utilities.VersionUtilities
import org.hl7.fhir.validation.ValidationEngine
import org.hl7.fhir.validation.cli.model.CliContext
import org.hl7.fhir.validation.cli.utils.Common

/**
 * You don't want to instantiate a new instance of the ValidationEngine each time you run a test, so we create a
 * singleton companion Object.
 */
object SingletonEngine {
    private val engine: ValidationEngine

    init {
        val v = VersionUtilities.CURRENT_FULL_VERSION
        val definitions = VersionUtilities.packageForVersion(v) + "#" + v
        engine = Common.getValidationEngine(v, definitions, null, null)
    }

    fun engine(): ValidationEngine {
        return engine;
    }
}

/**
 * Private method used to reduce boilerplate when testing the application.
 */
fun testWithApp(
    cliContext: CliContext = CliContext(),
    callback: TestApplicationEngine.() -> Unit,
) {
    withTestApplication({ init(context = cliContext) }) { callback() }
}