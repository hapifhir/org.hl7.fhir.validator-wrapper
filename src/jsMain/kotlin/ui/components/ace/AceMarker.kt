package ui.components.ace

data class AceMarker(
    val startRow: Int,
    val startCol: Int,
    val endRow: Int,
    val endCol: Int,
    val className: String,
    val type: String, // "fullLine" | "screenLine" | "text"
    val inFront : Boolean
    )
