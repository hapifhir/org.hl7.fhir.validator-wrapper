package css.component

import css.const.*
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.border
import kotlinx.css.properties.boxShadow
import styled.StyleSheet

object ContextSettingsStyle : StyleSheet("ContextSettingsStyle", isStatic = true) {

    val mainDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        padding(1.rem)
        margin(horizontal = 2.rem, vertical = 1.rem)
        border(width = 1.px, style = BorderStyle.solid, color = GRAY_300, borderRadius = 4.px)
    }

    val dropbtn by css {
        backgroundColor = Color.indianRed
        color = Color.white
        padding(16.px)
        fontSize = 16.px
        borderStyle = BorderStyle.none
        cursor = Cursor.pointer
    }

    val dropdown by css {
        position = Position.relative
        display = Display.inlineBlock
    }

    val dropdownContent by css {
        display = Display.none
        position = Position.absolute
        backgroundColor = Color.gray
        minWidth = 160.px
        boxShadow(color = SHADOW, offsetX = 0.px, offsetY = 5.px, blurRadius = 5.px)
        zIndex = 1
        children("span") {
            color = Color.black
            padding(vertical = 12.px, horizontal = 16.px)
            textDecoration = TextDecoration.none
            display = Display.block
            hover {
                backgroundColor = Color.bisque
            }
        }
        ancestorHover(".${ContextSettingsStyle.name}-${ContextSettingsStyle::dropdown.name}") {
            display = Display.block
        }
    }

//    val element by css {
//        backgroundColor = Color.green
//
//        hover {
//            backgroundColor = Color.red
//        }
//    }
//
//    // Example of a ".wrapper:hover .inner" selector
//    val wrapper by css {
//        minWidth = 160.px
//        minHeight = 40.px
//        backgroundColor = Color.yellowGreen
//    }
//
//    val inner by css {
//        backgroundColor = Color.green
//        // Use reflection to refer to other elements, it's longer but safer than using hard-coded class names
//        ancestorHover(".${ContextSettingsStyle.name}-${ContextSettingsStyle::wrapper.name}") {
//            backgroundColor = Color.red
//        }
//
//    }
}