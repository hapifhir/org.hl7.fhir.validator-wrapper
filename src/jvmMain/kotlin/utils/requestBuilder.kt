package utils

import model.ValidationRequest
import model.FileInfo
import model.CliContext
import java.io.FileReader
import com.google.gson.Gson


fun buildRequest(body: String): ValidationRequest {
    val gson = Gson()
    val json = gson.fromJson(body, ValidationRequest::class.java)
    if (json.getCliContext() != null) {
        // request body is a ValidationRequest
        return json
    } else {
        // request body is a resource
        val filesToValidate = listOf<FileInfo>(FileInfo("file.json", body, "json"))
        val context = CliContext()
        context.setTargetVer("4.0.1")
        context.setSv("4.0.1")
        // can hard code a default IG
        //context.addIg("hl7.fhir.us.core#4.0.0")
        return ValidationRequest().setCliContext(context).setFilesToValidate(filesToValidate)
    }
}