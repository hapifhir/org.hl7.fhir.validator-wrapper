package css.component

import css.const.GRAY_400
import css.const.GRAY_700
import css.const.SHADOW
import css.const.WHITE
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.boxShadow
import styled.StyleSheet

object DropdownChoiceStyle : StyleSheet("DropdownChoiceStyle", isStatic = true) {

    val dropDownChoiceButton by css {
        backgroundColor = GRAY_700
        color = WHITE
        padding(1.rem)
        fontSize = 1.rem
        borderStyle = BorderStyle.none
        cursor = Cursor.pointer
        minWidth = 20.pct
    }

    val dropDownChoiceContainer by css {
        position = Position.relative
        display = Display.inlineBlock
    }

    val dropDownChoiceContent by css {
        position = Position.absolute
        backgroundColor = GRAY_700
        overflowY = Overflow.scroll
        minWidth = 20.pct
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
    }

    val show by css {
        display = Display.block
    }
}