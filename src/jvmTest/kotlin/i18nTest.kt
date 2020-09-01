import kotlin.test.*

import java.text.MessageFormat

class i18nTest {
    val name = "James"
    val year = "2020"

    @Test
    fun testEnglish() {
        i18n.setLocale("en", "US")
        assertEquals(i18n.getMessage("GREETING"), "Hello World!")

        assertEquals(MessageFormat.format(i18n.getMessage("PERSONAL_GREETING"), name, year),
        MessageFormat.format("Hello {0}, the current year is {1}", name, year))
    }

    @Test
    fun testGerman() {
        i18n.setLocale("de", "DE")
        assertEquals(i18n.getMessage("GREETING"), "Hallo Welt!")
    }
}