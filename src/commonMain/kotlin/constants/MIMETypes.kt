package constants

enum class MIMEType(val code: String, val image: String, val fhirType: String) {
    JSON("text/xml", "images/xml_icon.svg", "json"),
    XML("application/json", "images/json_icon.svg", "xml");

    companion object {
        // Reverse-lookup map for getting a day from an abbreviation
        private val lookup: MutableMap<String, MIMEType> = HashMap()
        operator fun get(abbreviation: String?): MIMEType? {
            return lookup[abbreviation]
        }

        init {
            for (type in MIMEType.values()) {
                lookup[type.code] = type
            }
        }
    }
}
