package model

enum class AppScreen(val display: String) {
    VALIDATOR("HEADER_VALIDATE"),
    SETTINGS("HEADER_OPTIONS");

    companion object {
        private val map = AppScreen.values().associateBy(AppScreen::display)
        fun fromDisplay(type: String) = map[type]
    }
}