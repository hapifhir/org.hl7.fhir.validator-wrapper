package routes

import init
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.withTestApplication
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
        engine = Common.getValidationEngine(v, definitions, null)
    }
    fun engine(): ValidationEngine {
        return engine;
    }
 }

/**
 * Private method used to reduce boilerplate when testing the application.
 */
fun testWithApp(callback: TestApplicationEngine.() -> Unit) {
    withTestApplication({ init(testing = true, engine = SingletonEngine.engine(), context = CliContext()) }) { callback() }
}

