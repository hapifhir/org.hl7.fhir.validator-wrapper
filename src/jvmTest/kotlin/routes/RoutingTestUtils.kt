package routes

import io.ktor.server.testing.*
import module

/**
 * Private method used to reduce boilerplate when testing the application.
 */
fun testWithApp(callback: TestApplicationEngine.() -> Unit, ) {
    withTestApplication({ module() }) { callback() }
}