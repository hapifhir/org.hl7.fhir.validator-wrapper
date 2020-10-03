package css.component

import css.const.*
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.css.properties.boxShadow
import styled.StyleSheet

object DropDownMultiChoiceStyle : StyleSheet("DropDownMultiChoice", isStatic = true) {

    val mainDiv by css {
        position = Position.relative
        display = Display.inlineBlock
    }

    val dropDownMultiChoiceButton by css {
        backgroundColor = GRAY_700
        color = WHITE
        padding(1.rem)
        fontSize = 1.rem
        borderStyle = BorderStyle.none
        cursor = Cursor.pointer
        minWidth = 20.pct
    }

    val dropDownMultiChoiceContent by css {
        //display = Display.none
        position = Position.absolute
        backgroundColor = GRAY_700
        overflowY = Overflow.scroll
        minWidth = 160.px
        maxHeight = 240.px
        boxShadow(color = SHADOW, offsetX = 0.px, offsetY = 5.px, blurRadius = 5.px)
        zIndex = 1
        children("span") {
            padding(vertical = 12.px, horizontal = 16.px)
            +TextStyle.codeLight
            display = Display.block
            hover {
                backgroundColor = GRAY_400
            }
        }
// TODO I'm leaving this here, so I remember how to do it later.
//        ancestorHover(".${ContextSettingsStyle.name}-${ContextSettingsStyle::dropdown.name}") {
//            display = Display.block
//        }
    }
}