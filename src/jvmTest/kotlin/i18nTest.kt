import kotlin.test.*

import java.text.MessageFormat

class i18nTest {
    // Testing the United States, English locale
    @Test
    fun testEnglish() {
        i18n.setLocale("en", "US")
        assertEquals(i18n.getMessage("GREETING"), "Hello World!")
    }

    // Testing the Germany, German locale
    @Test
    fun testGerman() {
        i18n.setLocale("de", "DE")
        assertEquals(i18n.getMessage("GREETING"), "Hallo Welt!")
    }
}