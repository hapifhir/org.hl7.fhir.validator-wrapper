package css

import css.const.*
import kotlinx.css.*
import kotlinx.css.properties.*
import styled.StyleSheet
import styled.animation

object FileErrorDisplayStyle : StyleSheet("TabBar", isStatic = true) {

    val mainDiv by css {
        width = 100.pct
        flexGrow = 1.0
        display = Display.flex
        overflow = Overflow.scroll
        flexDirection = FlexDirection.column
    }

    val lineStyle by css {
        //width = 100.pct
        display = Display.flex
        whiteSpace = WhiteSpace.pre
    }

    val textHighlight by css {
        +lineStyle
        borderRadius = 3.px
    }

    val textHighlightFatal by css {
       +textHighlight
        backgroundColor = ALMOST_PERFECT_PINK
        hover {
            backgroundColor = PERFECT_PINK
        }
    }

    val textHighlightError by css {
        +textHighlight
        backgroundColor = ALMOST_OVERT_ORANGE
        hover {
            backgroundColor = OVERT_ORANGE
        }
    }

    val textHighlightWarning by css {
        +textHighlight
        backgroundColor = ALMOST_YAPPY_YELLOW
        hover {
            backgroundColor = YAPPY_YELLOW
        }
    }

    val textHighlightInfo by css {
        +textHighlight
        backgroundColor = ALMOST_BARELY_BLUE
        hover {
            backgroundColor = BARELY_BLUE
        }
    }



//    val fileContent by css {
//        width = 100.pct
//        flexGrow = 1.0
//        display = Display.flex
//        overflow = Overflow.scroll
//        whiteSpace = WhiteSpace.preWrap
//    }
}