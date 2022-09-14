package ui.components.footer

import css.const.HL7_RED
import css.const.WHITE
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.*
import react.dom.attrs
import styled.*

external interface FooterLineItemProps : Props {
    var icon: String
    var href: String
    var label: String
}

class FooterLineItem : RComponent<FooterLineItemProps, State>() {

    override fun RBuilder.render() {
        styledA(href = props.href) {
            css {
                +FooterLineItemStyle.lineItem
            }
            styledImg {
                css {
                    +FooterLineItemStyle.lineItemIcon
                }
                attrs {
                    src = props.icon
                }
            }
            styledSpan {
                css {
                    +FooterLineItemStyle.lineItemLabel
                }
                +props.label
            }
        }
    }
}

/**
 * Convenience method for instantiating the component.
 */
fun RBuilder.footerLineItem(handler: FooterLineItemProps.() -> Unit){
    return child(FooterLineItem::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object FooterLineItemStyle : StyleSheet("FooterLineItemStyle", isStatic = true) {
    private val FOOTER_HEIGHT = 200.px
    val lineItem by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        alignContent = Align.center
        textDecoration = TextDecoration.none
        margin(bottom = 6.px)
    }
    val lineItemIcon by css {
        alignSelf = Align.center
        height = 18.px
    }
    val lineItemLabel by css {
        fontFamily = TextStyle.FONT_FAMILY_MAIN
        fontSize = 12.pt
        margin(left = 12.px)
        fontWeight = FontWeight.w400
        alignSelf = Align.center
        color = WHITE
    }
}