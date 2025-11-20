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

    suspend fun <R> HttpResponse.parseBody(clazz: Class<R>): R {
        return gson.fromJson(body<String>(), clazz)
    }

    protected fun testApplication(test: suspend ApplicationTestBuilder.() -> Unit) {
        io.ktor.server.testing.testApplication {
            environment {
                developmentMode = false
                config = MapApplicationConfig()
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