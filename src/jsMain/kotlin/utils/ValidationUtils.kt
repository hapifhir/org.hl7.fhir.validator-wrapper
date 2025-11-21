package utils

import model.IssueSeverity
import model.ValidationMessage

fun getHighestIssueSeverity(validationMessages: List<ValidationMessage>): IssueSeverity {
    var error = false
    var warning = false
    var info = false

    validationMessages.forEach {
        when (it.getLevel()) {
            IssueSeverity.INFORMATION -> info = true
            IssueSeverity.WARNING -> warning = true
            IssueSeverity.ERROR -> error = true
            IssueSeverity.FATAL -> return IssueSeverity.FATAL
            else -> {}
        }
    }

    return when {
        error -> IssueSeverity.ERROR
        warning -> IssueSeverity.WARNING
        info -> IssueSeverity.INFORMATION
        else -> IssueSeverity.NULL
    }
}

data class TypeCounts(var info: Int, var warning: Int, var error: Int, var fatal: Int)

fun getMessageTypeCounts(validationMessages: List<ValidationMessage>): TypeCounts {
    var fatal = 0
    var error = 0
    var warning = 0
    var info = 0

    validationMessages.forEach {
        when (it.getLevel()) {
            IssueSeverity.INFORMATION -> info++
            IssueSeverity.WARNING -> warning++
            IssueSeverity.ERROR -> error++
            IssueSeverity.FATAL -> fatal++
            else -> throw Exception("Bad message level.")
        }
    }

    return TypeCounts(info, warning, error, fatal)
}