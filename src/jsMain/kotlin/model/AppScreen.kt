package model

enum class AppScreen(val polyglotKey: String) {
    VALIDATOR("validate_heading"),
    SETTINGS("options_heading");

    companion object {
        private val map = AppScreen.values().associateBy(AppScreen::name)
        fun fromDisplay(type: String) = map[type]
    }
}