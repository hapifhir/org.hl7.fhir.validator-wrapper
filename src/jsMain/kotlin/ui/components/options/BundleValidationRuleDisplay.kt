package ui.components.options

import css.const.*
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.html.js.onClickFunction
import model.BundleValidationRule
import react.*
import react.dom.attrs
import styled.*

external interface BundleValidationRuleDisplayProps : Props {
    var rule: BundleValidationRule
    var onDelete: () -> Unit
}

class BundleValidationRuleDisplay : RComponent<BundleValidationRuleDisplayProps, State>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +BundleValidationRuleDisplayStyle.mainDiv
            }

            styledSpan {
                css {
                    +TextStyle.dropDownLabel
                    +BundleValidationRuleDisplayStyle.igName
                }
                +BundleValidationRule.toDisplayString(props.rule)
            }
            styledImg {
                css {
                    +BundleValidationRuleDisplayStyle.closeButton
                }
                attrs {
                    src = "images/close_black.png"
                    onClickFunction = {
                        props.onDelete()
                    }
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.bundleValidationRuleDisplay(handler: BundleValidationRuleDisplayProps.() -> Unit) {
    return child(BundleValidationRuleDisplay::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object BundleValidationRuleDisplayStyle : StyleSheet("BundleValidationRuleDisplayStyle", isStatic = true) {
    val mainDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        border(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        margin(right = 16.px, top = 4.px, bottom = 4.px)
        padding(horizontal = 16.px, vertical = 8.px)
        backgroundColor = WHITE
    }
    val igName by css {
        padding(right = 16.px)
    }
    val closeButton by css {
        width = 16.px
        height = 16.px
        alignSelf = Align.center
        borderRadius = 50.pct
        hover {
            backgroundColor = HIGHLIGHT_GRAY
        }
        active {
            backgroundColor = INACTIVE_GRAY
        }
    }
}
