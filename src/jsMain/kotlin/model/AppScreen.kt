package model

enum class AppScreen(val display: String) {
    VALIDATOR("Validate"),
    SETTINGS("Options");

    companion object {
        private val map = AppScreen.values().associateBy(AppScreen::display)
        fun fromDisplay(type: String) = map[type]
    }
}