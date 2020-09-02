package model

actual enum class IssueSeverity {
    FATAL, ERROR, WARNING, INFORMATION, NULL;

    actual fun toCode(): String {
        return when (this) {
            FATAL -> "fatal"
            ERROR -> "error"
            WARNING -> "warning"
            INFORMATION -> "information"
            NULL -> "null"
        }
    }

    val display: String
        get() {
            return when (this) {
                FATAL -> "Fatal"
                ERROR -> "Error"
                WARNING -> "Warning"
                INFORMATION -> "Information"
                NULL -> "Null"
            }
        }

    val isError: Boolean
        get() = this == FATAL || this == ERROR

    companion object {
        fun fromCode(codeString: String?): IssueSeverity? {
            return when (codeString) {
                "fatal" -> FATAL
                "error" -> ERROR
                "warning" -> WARNING
                "information" -> INFORMATION
                "null" -> NULL
                else -> throw Exception("Unknown IssueSeverity code '$codeString'")
            }
        }
    }
}