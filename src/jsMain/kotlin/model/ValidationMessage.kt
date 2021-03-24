package model

import kotlinx.serialization.Serializable

@Serializable
actual class ValidationMessage {

    private var line = 0
    private var col = 0
    private var message: String = ""
    private lateinit var level: IssueSeverity

    actual fun getMessage(): String {
        return message
    }

    actual fun setMessage(message: String): ValidationMessage {
        this.message = message
        return this
    }

    actual fun getLevel(): IssueSeverity {
        return level
    }

    actual fun setLevel(level: IssueSeverity): ValidationMessage {
        this.level = level
        return this
    }

    actual fun getLine(): Int {
        return line
    }

    actual fun setLine(theLine: Int) {
        this.line = theLine
    }

    //TODO remove
    fun setLine(theLine: Int, any: Any): ValidationMessage {
        this.line = theLine
        return this
    }

    actual fun getCol(): Int {
        return col
    }

    actual fun setCol(theCol: Int) {
        this.col = theCol
    }
}

fun ValidationMessage.prettyPrint() {
    println("col:${this.getCol()} line:${this.getLine()}\n" +
            "level:${this.getLevel().display}\n" +
            "message:${this.getMessage()}")
}