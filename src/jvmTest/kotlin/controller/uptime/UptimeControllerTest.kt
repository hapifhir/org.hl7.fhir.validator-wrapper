package controller.validation

import api.uptime.ENDPOINT_API_TIMEOUT
import api.uptime.EndpointApi
import controller.BaseControllerTest
import controller.uptime.UptimeController
import controller.uptime.UptimeControllerImpl
import io.mockk.FunctionAnswer
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UptimeControllerTest : BaseControllerTest() {
    private val endpointApi: EndpointApi = mockk()
    private val uptimeController: UptimeController by lazy { UptimeControllerImpl() }

    init {
        startInjection(
            module {
                single(override = true) { endpointApi }
            }
        )
    }

    @BeforeEach
    override fun before() {
        super.before()
        clearMocks(endpointApi)
    }

    @Test
    fun `test happy path, tx server is up`() {
        coEvery { endpointApi.getFileAtEndpoint(any()) } returns byteArrayOf(1, 2, 3, 4)

        runBlocking {
            val response = uptimeController.isTerminologyServerUp()
            assertTrue(response)
        }
    }

    @Test
    fun `test sad path, tx server req throws error`() {
        coEvery { endpointApi.getFileAtEndpoint(any()) } throws (Exception("bad stuff"))

        runBlocking {
            val response = uptimeController.isTerminologyServerUp()
            assertFalse(response)
        }
    }

    @Test
    fun `test sad path, tx server times out`() {
        val fncResp = FunctionAnswer { Thread.sleep(ENDPOINT_API_TIMEOUT + 1000); byteArrayOf() }
        coEvery { endpointApi.getFileAtEndpoint(any()) }.answers(fncResp)

        runBlocking {
            val response = uptimeController.isTerminologyServerUp()
            assertFalse(response)
        }
    }
}