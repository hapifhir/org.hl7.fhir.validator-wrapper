package api

import api.terminogy.TerminologyApi
import api.terminogy.TerminologyApiImpl
import api.uptime.EndpointApi
import api.uptime.EndpointApiImpl
import org.hl7.fhir.utilities.npm.PackageClient
import org.hl7.fhir.validation.cli.services.ValidationService
import org.koin.dsl.module

object ApiInjection {

    private const val PACKAGE_CLIENT_ADDRESS = "http://packages.fhir.org"

    val koinBeans = module {
        single<ValidationService> { ValidationService() }
        single<PackageClient> { PackageClient(PACKAGE_CLIENT_ADDRESS) }
        single<TerminologyApi> { TerminologyApiImpl() }
        single<EndpointApi> { EndpointApiImpl() }
    }
}