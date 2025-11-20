package routing

import com.google.gson.Gson
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

abstract class BaseRoutingTest {

    private val gson = Gson()

    @BeforeEach
    fun setup() {
        stopKoin() // Just in case some other test didn't
        startKoin {
            modules(
                module {
                    getKoinModules()
                })
        }
    }

    @AfterEach
    fun tearDown() {
        stopKoin()
    }

    protected abstract fun Routing.getRoutingModules()

    protected abstract fun Module.getKoinModules()

    fun toJsonBody(obj: Any): String = gson.toJson(obj)

    suspend fun <R> HttpResponse.parseBody(clazz: Class<R>): R {
        return gson.fromJson(body<String>(), clazz)
    }

    /**
     * Sets up a testApplication with gson content negotiation and whatever koin or routing modules the implementing
     * test class has in its getRoutingModules() and .getKoinModules() methods.
     */
    protected fun withBaseTestApplication(test: suspend ApplicationTestBuilder.() -> Unit) {
        testApplication {
            environment {
                developmentMode = false
                config = MapApplicationConfig() // don't configure from application.conf
            }

            application {
                install(ContentNegotiation) {
                    gson { }
                }
                routing {
                    getRoutingModules()
                }
            }
            test()
        }
    }
}