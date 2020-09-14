package model

expect enum class IssueSeverity {
    FATAL, ERROR, WARNING, INFORMATION, NULL;

    fun toCode(): String
}