package controller.ig

import controller.BaseControllerTest
import instrumentation.IgInstrumentation.givenANullReturnedListOfPackageInfo
import instrumentation.IgInstrumentation.givenAReturnedListOfValidPackageInfo
import instrumentation.IgInstrumentation.givenAnEmptyReturnedListOfPackageInfo
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.hl7.fhir.utilities.npm.PackageClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IgControllerTest : BaseControllerTest() {
    private val igPackageClient: PackageClient = mockk()
    private val igController: IgController by lazy { IgControllerImpl() }

    init {
        startInjection(
            module {
                single(override = true) { igPackageClient }
            }
        )
    }

    @BeforeEach
    override fun before() {
        super.before()
        clearMocks(igPackageClient)
    }

    @Test
    fun `test happy path ig controller returns list of valid ig urls`() {
        val igPackageInfoList = givenAReturnedListOfValidPackageInfo()
        coEvery { igPackageClient.listFromRegistry(any(), any(), any()) } returns igPackageInfoList

        runBlocking {
            val response = igController.listIgs()
            assertEquals(igPackageInfoList.map { it.url }.toMutableList(), response)
        }
    }

    @Test
    fun `test ig controller returns empty list on null return from service`() {
        val nullIgPackageInfoList = givenANullReturnedListOfPackageInfo()
        coEvery { igPackageClient.listFromRegistry(any(), any(), any()) } returns nullIgPackageInfoList

        runBlocking {
            val response = igController.listIgs()
            assertEquals(mutableListOf(), response)
        }
    }

    @Test
    fun `test ig controller returns empty list on empty list return from service`() {
        val emptyIgPackageInfoList = givenAnEmptyReturnedListOfPackageInfo()
        coEvery { igPackageClient.listFromRegistry(any(), any(), any()) } returns emptyIgPackageInfoList

        runBlocking {
            val response = igController.listIgs()
            assertEquals(mutableListOf(), response)
        }
    }
}