package controller.version

import controller.BaseControllerTest
import instrumentation.VersionsInstrumentation.givenAListOfExpectedVersions
import instrumentation.VersionsInstrumentation.givenANullListOfExpectedVersions
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import org.hl7.fhir.utilities.VersionUtilities
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VersionControllerTest : BaseControllerTest() {

    private val versionController: VersionController by lazy { VersionControllerImpl() }

    init {
        startInjection(
            module {

            }
        )
    }

    @BeforeEach
    override fun before() {
        super.before()
    }

    @Test
    fun `test happy path version controller returns list of valid version labels`() {
        val expectedVersionsList = givenAListOfExpectedVersions()
        mockkStatic(VersionUtilities::class)
        every { VersionUtilities.listSupportedVersions() } returns (expectedVersionsList)

        runBlocking {
            val response = versionController.listSupportedVersions()
            assertEquals(expectedVersionsList.split(',').map { it.replace("\\s".toRegex(), "") }.toMutableList(),
                response)
        }
    }

    @Test
    fun `test null version list returns empty list`() {
        val expectedVersionsList = givenANullListOfExpectedVersions()
        mockkStatic(VersionUtilities::class)
        every { VersionUtilities.listSupportedVersions() } returns (expectedVersionsList)

        runBlocking {
            val response = versionController.listSupportedVersions()
            assertEquals(mutableListOf(), response)
        }
    }
}