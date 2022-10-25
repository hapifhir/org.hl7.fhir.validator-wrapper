package utils

import kotlin.test.Test
import kotlin.test.assertEquals

import utils.getLanguageFromBrowser

class BrowserLanguageTests {
    
    @Test
    fun testEnUs() {

        val actualLanguage = getLanguageFromBrowser(arrayOf("en_US"))
        val expectedLanguage = Language.US_ENGLISH
        //println("Testing en_US")
        assertEquals(actualLanguage, expectedLanguage);
    }

}


//