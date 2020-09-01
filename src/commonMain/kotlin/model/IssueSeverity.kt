package model

expect enum class IssueSeverity {
    FATAL, ERROR, WARNING, INFORMATION;

    fun toCode(): String
}