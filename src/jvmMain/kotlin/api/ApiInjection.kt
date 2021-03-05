package api

import org.hl7.fhir.utilities.VersionUtilities
import org.hl7.fhir.utilities.npm.PackageClient
import org.hl7.fhir.validation.cli.services.ValidationService
import org.koin.dsl.module

object ApiInjection {

    private const val PACKAGE_CLIENT_ADDRESS = "https://packages.fhir.org"

    val koinBeans = module {
        single<ValidationService> { ValidationService() }
        single<PackageClient> { PackageClient(PACKAGE_CLIENT_ADDRESS) }
    }
}