package model

import css.const.FATAL_PINK

data class MessageFilter(
    var showFatal: Boolean = true,
    var showError: Boolean = true,
    var showWarning: Boolean = true,
    var showInfo: Boolean = true,
) {
    // Checks if we are filtering out the given message type
    fun showEntry(message: ValidationMessage): Boolean {
        return when (message.getLevel()) {
            IssueSeverity.FATAL -> showFatal
            IssueSeverity.ERROR -> showError
            IssueSeverity.WARNING -> showWarning
            IssueSeverity.INFORMATION -> showInfo
            IssueSeverity.NULL -> false
        }
    }

    fun determineNumberDisplayedIssues(messages: List<ValidationMessage>): Int {
        return messages.filter {
                    (showFatal && it.getLevel() == IssueSeverity.FATAL) ||
                    (showError && it.getLevel() == IssueSeverity.ERROR) ||
                    (showWarning && it.getLevel() == IssueSeverity.WARNING) ||
                    (showInfo && it.getLevel() == IssueSeverity.INFORMATION)
        }.size
    }
}


