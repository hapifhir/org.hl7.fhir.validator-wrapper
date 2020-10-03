package css.component

import css.const.*
import css.text.TextStyle
import kotlinx.css.*
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

    val sectionTitleBar by css {
        display = Display.flex
        flexDirection = FlexDirection.row
    }

    val dropDownArrowDiv by css {
        display = Display.flex
        flex(flexGrow = 1.0)
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.flexEnd
    }

    val dropDownArrow by css {
        width = ICON_SMALL_DIM
        height = ICON_SMALL_DIM
        alignSelf = Align.center
    }

    val dropDownAndSelectedIgDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        paddingTop = 0.5.rem
    }

    val dropDownButtonAndContentDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.column
    }

    val dropbtn by css {
        backgroundColor = GRAY_700
        color = WHITE
        padding(1.rem)
        fontSize = 1.rem
        borderStyle = BorderStyle.none
        cursor = Cursor.pointer
        minWidth = 20.pct
        hover {
            children("div") {
                display = Display.block
            }
        }
    }

    val dropdown by css {
        position = Position.relative
        display = Display.inlineBlock
    }

    val dropdownContent by css {
        display = Display.none
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
//        ancestorHover(".${ContextSettingsStyle.name}-${ContextSettingsStyle::dropdown.name}") {
//            display = Display.block
//        }
    }

    val selectedIgsDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        flexWrap = FlexWrap.wrap
    }
}