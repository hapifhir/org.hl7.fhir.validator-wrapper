package css.component

import css.animation.FadeIn.quickFadeIn
import css.const.*
import kotlinx.css.*
import kotlinx.css.properties.border
import styled.StyleSheet

object ValidationResultDisplayMenuStyle : StyleSheet("ValidationResultDisplayMenuStyle", isStatic = true) {

    val titleField by css {
        width = 100.pct
        alignSelf = Align.center
    }

}