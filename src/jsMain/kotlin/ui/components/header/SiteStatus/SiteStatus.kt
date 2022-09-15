package ui.components.header.SiteStatus

import css.const.TEXT_BLACK
import css.text.TextStyle
import kotlinx.css.*
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan

external interface SiteStatusProps : Props {
    var label: String
    var status: SiteState
    var url: String
}

class SiteStatus : RComponent<SiteStatusProps, State>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                +SiteStatusStyle.mainContainer
            }
            siteStatusIndicator {
                siteState = props.status
            }
            styledSpan {
                css {
                    +SiteStatusStyle.weblabel
                }
                +props.label
            }
        }
    }
}

/**
 * Convenience method for instantiating the component.
 */
fun RBuilder.siteStatus(handler: SiteStatusProps.() -> Unit) {
    return child(SiteStatus::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object SiteStatusStyle : StyleSheet("SiteStatus", isStatic = true) {
    val mainContainer by css {
        display = Display.inlineFlex
        flexDirection = FlexDirection.row
        alignItems = Align.center
        alignSelf = Align.start
        padding(vertical = 4.px)
    }
    val weblabel by css {
        fontFamily = TextStyle.FONT_FAMILY_MAIN
        fontSize = 10.pt
        paddingLeft = 8.px
        color = TEXT_BLACK
        fontWeight = FontWeight.w600
    }
}