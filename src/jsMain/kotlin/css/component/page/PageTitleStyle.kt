package css.component.page

import kotlinx.css.*
import styled.StyleSheet

object PageTitleStyle : StyleSheet("SectionTitleStyle", isStatic = true) {

    val sectionTitle by css {
        textAlign = TextAlign.center
        marginTop = 4.rem
        marginBottom = 5.rem
        justifyContent = JustifyContent.center
        display = Display.flex
        flexDirection = FlexDirection.column
    }

}