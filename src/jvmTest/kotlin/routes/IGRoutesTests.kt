package routes

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.http.*
import io.ktor.server.testing.*
import org.hl7.fhir.utilities.cache.PackageClient
import org.hl7.fhir.validation.cli.model.CliContext
import java.io.BufferedReader
import kotlin.test.Test
import kotlin.test.assertEquals


class IGRoutesTest {
    @Test
    fun testGetContext() {
        val client = PackageClient("https://packages.fhir.org/")
        val versions = client.search(null, null ,null, false)
        versions.forEach{println(it.url)}

//        val reader = BufferedReader(client.fetchCached(null).reader())
//        val content = StringBuilder()
//        reader.use { reader ->
//            var line = reader.readLine()
//            while (line != null) {
//                content.append(line)
//                line = reader.readLine()
//            }
//        }
//        println(content.toString())
    }
}