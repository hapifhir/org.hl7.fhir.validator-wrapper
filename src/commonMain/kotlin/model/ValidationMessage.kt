package model

expect class ValidationMessage() {

    fun getMessage(): String
    fun setMessage(message: String): ValidationMessage

    fun getLevel(): IssueSeverity
    fun setLevel(level: IssueSeverity): ValidationMessage

    fun getLine(): Int
    fun setLine(theLine: Int)

    fun getCol(): Int
    fun setCol(theCol: Int)

}
