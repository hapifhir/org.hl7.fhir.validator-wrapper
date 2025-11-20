import com.google.gson.Gson
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.server.routing.*
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
}