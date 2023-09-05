package constants

enum class FhirFormat(var code: String) {
    JSON("json"),
    TURTLE("ttl"),
    XML("xml"),
    TEXT("text"),
    VBAR("hl7"),
    SHC("shc");

    companion object {
        fun fromCode(code: String) =
            when (code) {
                "json" -> JSON
                "ttl" -> TURTLE
                "xml" -> XML
                "text" -> TEXT
                "hl7" -> VBAR
                "shc" -> SHC
                else -> null
            }
    }
}