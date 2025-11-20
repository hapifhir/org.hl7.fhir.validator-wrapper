import com.google.gson.Gson
import constants.VERSIONS_ENDPOINT
import controller.version.VersionController
import controller.version.versionModule
import instrumentation.VersionsInstrumentation.givenAListOfSupportedVersions
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import io.mockk.coEvery
import io.mockk.mockk
import model.FhirVersionsResponse
import model.TerminologyServerResponse
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.core.context.startKoin
import org.koin.dsl.module
import kotlin.test.assertEquals

//FIXME remove me after all deprecated withTestApplication tests are removed
class SanityCheckRoutingTest {

    private val gson = Gson()

    private val versionController: VersionController = mockk()

    @BeforeEach
    fun setup() {
        startKoin {
            modules(
                module {
                    single<VersionController> { versionController }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `when requesting requesting to check a terminology server, return valid terminology response body`() =
        testApplication {
            environment {
                developmentMode = false
                config = MapApplicationConfig()
            }

            application {
                install(ContentNegotiation) {
                    gson { }
                }
                routing {
                    versionModule()
                }
            }

            val versionResponse = givenAListOfSupportedVersions()
            coEvery { versionController.listSupportedVersions() } returns versionResponse

            val response = client.get(VERSIONS_ENDPOINT) {
                contentType(ContentType.Application.Json)
            }
            val responseBody = response.parseBody(FhirVersionsResponse::class.java)
            assertEquals(HttpStatusCode.OK, response.status)
            Assertions.assertIterableEquals(versionResponse, responseBody.versions)
        }

    suspend fun <R> HttpResponse.parseBody(clazz: Class<R>): R {
        return gson.fromJson(body<String>(), clazz)
    }
}