package org.hl7.fhir.validator.routes

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import org.hl7.fhir.validation.cli.model.CliContext
import org.hl7.fhir.validation.cli.model.ValidationRequest
import routes.testWithApp

class ValidationRouteTests {

//    @Test TODO add mocks to fix
//    fun testPostValidationRequest() = testWithApp {
//        val request = ValidationRequest().setCliContext(CliContext())
//        handleRequest(HttpMethod.Post, "/validate"){
//            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
//            setBody(ObjectMapper().writeValueAsString(request))
//        }.apply {
//            assertEquals(HttpStatusCode.OK, response.status())
//        }
//    }

}