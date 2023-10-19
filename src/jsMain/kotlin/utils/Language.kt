package utils

enum class Language(val code: String, val display: String) {
    ENGLISH("en", "English"),
    GERMAN("de_DE", "Deutsch"),
    SPANISH("es", "Español");

    fun getLanguageCode() : String {
        return this.code.substring(0, 2)
    }
    companion object {

        fun getSelectedLanguage(targetLanguage: String) : Language? {
        for (language in Language.values()) {
            if (targetLanguage == language.getLanguageCode()) {
                return language
            }
        }
        return null
    }
    }
}

