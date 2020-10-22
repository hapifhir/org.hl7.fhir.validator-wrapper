package constants

enum class FhirFormat(val code: String) {
    JSON("json"),
    TURTLE("ttl"),
    XML("xml"),
    TEXT("text"),
    VBAR("hl7");

    companion object {
        fun fromCode(code: String) =
            when (code) {
                "json" -> JSON
                "ttl" -> TURTLE
                "xml" -> XML
                "text" -> TEXT
                "hl7" -> VBAR
                else -> null
            }
    }
}