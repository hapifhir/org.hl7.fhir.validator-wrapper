package i18n

import java.util.*

/**
 * This i18n file contains static classes and member variables that make up the i18n API.
 *
 * @author Keith Carlson
 *
 */

/**
 * Private member variables for the Java Locale and ResourceBundle objects. These are
 * instantied to English and the United States by default.
 */
private var currentLocale = Locale("en", "US")
private var messages = ResourceBundle.getBundle("MessagesBundle", currentLocale)

/**
 * Function for setting the locale and updating the member variables accordingly.
 *
 * @param language an ISO 639 langauge code
 * @param country an ISO 3166 country code
 *
 * More information on supported ISO codes can be found at <a href = "https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html">Java: Locale</a>
 */
fun setLocale(language: String, country: String) {
    currentLocale = Locale(language, country)
    messages = ResourceBundle.getBundle("MessagesBundle", currentLocale)
}

/**
 * Function for returning a String from the relevant MessagesBundle
 *
 * @param id the id of a String in the MessagesBundle
 * @return the corresponding message from the MessagesBundle
 */
fun getMessage(id: String): String {
    return messages.getString(id)
}
