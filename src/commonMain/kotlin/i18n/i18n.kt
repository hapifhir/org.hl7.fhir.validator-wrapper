package i18n

import java.util.*

var currentLocale = Locale("en", "US")
var messages = ResourceBundle.getBundle("MessagesBundle", currentLocale)

fun setLocale(language: String, country: String) {
    currentLocale = Locale(language, country)
    messages = ResourceBundle.getBundle("MessagesBundle", currentLocale)
}

fun getMessage(id: String): String {
    return messages.getString(id)
}

fun number(): Int = 5
