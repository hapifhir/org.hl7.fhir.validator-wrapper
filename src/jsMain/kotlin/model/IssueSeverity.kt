package model

actual enum class IssueSeverity {
    FATAL, ERROR, WARNING, INFORMATION;

    actual fun toCode(): String {
        return when (this) {
            FATAL -> "fatal"
            ERROR -> "error"
            WARNING -> "warning"
            INFORMATION -> "information"
        }
    }

    val display: String
        get() {
            return when (this) {
                FATAL -> "Fatal"
                ERROR -> "Error"
                WARNING -> "Warning"
                INFORMATION -> "Information"
            }
        }

    val isError: Boolean
        get() = this == FATAL || this == ERROR

    companion object {
        fun fromCode(codeString: String?): IssueSeverity? {
            return if (codeString != null && "" != codeString) {
                when (codeString) {
                    "fatal" -> {
                        FATAL
                    }
                    "error" -> {
                        ERROR
                    }
                    "warning" -> {
                        WARNING
                    }
                    "information" -> {
                        INFORMATION
                    }
                    else -> {
                        throw Exception("Unknown IssueSeverity code '$codeString'")
                    }
                }
            } else {
                null
            }
        }
    }
}