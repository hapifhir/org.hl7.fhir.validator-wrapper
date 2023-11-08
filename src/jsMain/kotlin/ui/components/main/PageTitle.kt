package ui.components.main

import css.text.TextStyle
import kotlinx.css.*
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan

external interface PageTitleProps : Props {
    var majorText: String
    var minorText: String
}

class PageTitle : RComponent<PageTitleProps, State>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +PageTitleStyle.sectionTitle
            }
            styledSpan {
                css {
                    +TextStyle.pageTitle
                    +PageTitleStyle.titleStyle
                }
                +props.majorText
            }
            styledSpan {
                css {
                    +TextStyle.pageDetails
                }
                +props.minorText
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.sectionTitle(handler: PageTitleProps.() -> Unit) {
    return child(PageTitle::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object PageTitleStyle : StyleSheet("PageTitleStyle", isStatic = true) {
    val sectionTitle by css {
        textAlign = TextAlign.center
        marginTop = 64.px
        marginBottom = 64.px
        justifyContent = JustifyContent.center
        display = Display.flex
        flexDirection = FlexDirection.column
    }
    val titleStyle by css {
        paddingBottom = 4.px
    }
}