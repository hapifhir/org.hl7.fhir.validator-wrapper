package constants

enum class MIMEType(val code: String, val image: String, val fhirType: String) {
    JSON("text/xml", "images/xml_icon.svg", "xml"),
    XML("application/json", "images/json_icon.svg", "json");

    companion object {
        // Reverse-lookup map for getting a day from an abbreviation
        private val codeLookup: MutableMap<String, MIMEType> = HashMap()
        private val fhirTypeLookup: MutableMap<String, MIMEType> = HashMap()

        fun fromFileType(abbreviation: String?): MIMEType? {
            return codeLookup[abbreviation]
        }

        fun fromFhirType(fhirType: String?): MIMEType? {
            return fhirTypeLookup[fhirType]
        }

        init {
            for (type in values()) {
                codeLookup[type.code] = type
                fhirTypeLookup[type.fhirType] = type
            }
        }
    }
}
