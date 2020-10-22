package routes

import init
import io.ktor.server.testing.*
import org.hl7.fhir.validation.cli.model.CliContext

/**
 * Private method used to reduce boilerplate when testing the application.
 */
fun testWithApp(
    cliContext: CliContext = CliContext(),
    callback: TestApplicationEngine.() -> Unit,
) {
    withTestApplication({ init(context = cliContext) }) { callback() }
}