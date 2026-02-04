package controller.ig


import controller.BaseControllerTest
import instrumentation.IgInstrumentation.givenANullReturnedListOfPackageInfo
import instrumentation.IgInstrumentation.givenAnEmptyReturnedListOfPackageInfo
import instrumentation.IgInstrumentation.givenAProcessedListOfValidPackageInfoA
import instrumentation.IgInstrumentation.givenAReturnedListOfValidPackageInfoA
import instrumentation.IgInstrumentation.givenAProcessedListOfValidPackageInfoDSTU2
import instrumentation.IgInstrumentation.givenAReturnedListOfValidPackageInfoDSTU2
import instrumentation.IgInstrumentation.givenAProcessedListOfValidPackageInfoSTU3
import instrumentation.IgInstrumentation.givenAReturnedListOfValidPackageInfoSTU3
import instrumentation.IgInstrumentation.givenAProcessedListOfValidPackageInfoR4
import instrumentation.IgInstrumentation.givenAReturnedListOfValidPackageInfoR4
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
import kotlin.test.assertTrue
import kotlin.test.fail

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IgControllerTest : BaseControllerTest() {
    private val igPackageClient: PackageClient = mockk()
    private val igController: IgController by lazy { IgControllerImpl() }

    init {
        startInjection(
            module {
                single() { igPackageClient }
            }
        )
    }

    @BeforeEach
    override fun before() {
        super.before()
        clearMocks(igPackageClient)
    }

    @Test
    fun `test happy path ig controller returns list of valid ig urls from registry`() {
        val igPackageInfoList = givenAReturnedListOfValidPackageInfoA()
        val resultingPackageInfoList = givenAProcessedListOfValidPackageInfoA()

        coEvery { igPackageClient.listFromRegistry(any(), any(), any()) } returns igPackageInfoList

        runBlocking {
            val response = igController.listIgsFromRegistry()
            (resultingPackageInfoList sameContentWith response)?.let { assertTrue(it) } ?: fail("null packageinfo")
        }
    }

    @Test
    fun `test ig controller returns empty list on null return from registry`() {
        val nullIgPackageInfoList = givenANullReturnedListOfPackageInfo()
        coEvery { igPackageClient.listFromRegistry(any(), any(), any()) } returns nullIgPackageInfoList

        runBlocking {
            val response = igController.listIgsFromRegistry()
            assertEquals(mutableListOf(), response)
        }
    }

    @Test
    fun `test ig controller returns empty list on empty list return from registry`() {
        val emptyIgPackageInfoList = givenAnEmptyReturnedListOfPackageInfo()
        coEvery { igPackageClient.listFromRegistry(any(), any(), any()) } returns emptyIgPackageInfoList

        runBlocking {
            val response = igController.listIgsFromRegistry()
            assertEquals(mutableListOf(), response)
        }
    }

    @Test
    fun `test happy path ig controller returns list of valid ig urls from simplifier`() {
        val igPackageInfoListDSTU2 = givenAReturnedListOfValidPackageInfoDSTU2()
        val resultingPackageInfoListDSTU2 = givenAProcessedListOfValidPackageInfoDSTU2()
        val igPackageInfoListSTU3 = givenAReturnedListOfValidPackageInfoSTU3()
        val resultingPackageInfoListSTU3 = givenAProcessedListOfValidPackageInfoSTU3()
        val igPackageInfoListR4 = givenAReturnedListOfValidPackageInfoR4()
        val resultingPackageInfoListR4 = givenAProcessedListOfValidPackageInfoR4()


        coEvery { igPackageClient.search(any(), any(), eq("DSTU2"), any(), any()) } returns igPackageInfoListDSTU2
        coEvery { igPackageClient.search(any(), any(), eq("STU3"), any(), any()) } returns igPackageInfoListSTU3
        coEvery { igPackageClient.search(any(), any(), eq("R4"), any(), any()) } returns igPackageInfoListR4

        runBlocking {
            val response = igController.listIgsFromSimplifier()
            println(response)
            ((resultingPackageInfoListDSTU2 + resultingPackageInfoListSTU3 + resultingPackageInfoListR4) sameContentWith response)?.let { assertTrue(it) } ?: fail("null packageinfo")
        }
    }

    @Test
    fun `test ig controller returns empty list on null return from simplifier`() {
        val nullIgPackageInfoList = givenANullReturnedListOfPackageInfo()

        coEvery { igPackageClient.search(any(), any(), eq("DSTU2"), any(), any()) } returns nullIgPackageInfoList
        coEvery { igPackageClient.search(any(), any(), eq("STU3"), any(), any()) } returns nullIgPackageInfoList
        coEvery { igPackageClient.search(any(), any(), eq("R4"), any(), any()) } returns nullIgPackageInfoList

        runBlocking {
            val response = igController.listIgsFromSimplifier()
            assertEquals(mutableListOf(), response)
        }
    }

    @Test
    fun `test ig controller returns empty list on empty list return from simplifier`() {
        val emptyIgPackageInfoList = givenAnEmptyReturnedListOfPackageInfo()

        coEvery { igPackageClient.search(any(), any(), eq("DSTU2"), any(), any()) } returns emptyIgPackageInfoList
        coEvery { igPackageClient.search(any(), any(), eq("STU3"), any(), any()) } returns emptyIgPackageInfoList
        coEvery { igPackageClient.search(any(), any(), eq("R4"), any(), any()) } returns emptyIgPackageInfoList

        runBlocking {
            val response = igController.listIgsFromSimplifier()
            assertEquals(mutableListOf(), response)
        }
    }

    @Test
    fun `test happy path ig controller returns list of valid ig versions from simplifier`() {
        val igPackageInfoList = givenAReturnedListOfValidPackageInfoA()
        val resultingPackageInfoList = givenAProcessedListOfValidPackageInfoA()

        coEvery { igPackageClient.getVersions(eq("dummy.package")) } returns igPackageInfoList

        runBlocking {
            val response = igController.listIgVersionsFromSimplifier("dummy.package")
            (resultingPackageInfoList sameContentWith response)?.let { assertTrue(it) } ?: fail("null packageinfo")
        }
    }

    @Test
    fun `test ig controller returns empty version list on null return from simplifier`() {
        val nullIgPackageInfoList = givenANullReturnedListOfPackageInfo()

        coEvery { igPackageClient.getVersions(eq("dummy.package")) } returns nullIgPackageInfoList

        runBlocking {
            val response = igController.listIgVersionsFromSimplifier("dummy.package")
            assertEquals(mutableListOf(), response)
        }
    }

    @Test
    fun `test ig controller returns empty version list on empty list return from simplifier`() {
        val emptyIgPackageInfoList = givenAnEmptyReturnedListOfPackageInfo()

        coEvery { igPackageClient.getVersions(eq("dummy.package")) } returns emptyIgPackageInfoList

        runBlocking {
            val response = igController.listIgVersionsFromSimplifier("dummy.package")
            assertEquals(mutableListOf(), response)
        }
    }

    infix fun <T> Collection<T>.sameContentWith(collection: Collection<T>?)
            = collection?.let { this.size == it.size && this.containsAll(it) }
}

