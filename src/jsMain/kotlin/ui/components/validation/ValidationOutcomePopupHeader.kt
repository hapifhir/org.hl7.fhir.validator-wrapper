package ui.components.validation

import css.const.BORDER_GRAY
import css.const.INACTIVE_GRAY
import css.const.WHITE
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.borderBottom
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.attrs
import styled.*

external interface ValidationOutcomePopupHeaderProps : Props {
    var filename: String
    var onClose: () -> Unit
}

class ValidationOutcomePopupHeader : RComponent<ValidationOutcomePopupHeaderProps, State>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                +ValidationOutcomePopupHeaderStyle.container
            }
            styledSpan {
                css {
                    +TextStyle.tabSectionHeading
                    +ValidationOutcomePopupHeaderStyle.titleField
                }
                +props.filename
            }
            styledImg {
                css {
                    +ValidationOutcomePopupHeaderStyle.closeIcon
                    hover {
                        backgroundColor = INACTIVE_GRAY
                    }
                    active {
                        backgroundColor = BORDER_GRAY
                    }
                }
                attrs {
                    src = "images/close_black.png"
                    onClickFunction = {
                        props.onClose()
                    }
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.validationOutcomePopupHeader(handler: ValidationOutcomePopupHeaderProps.() -> Unit) {
    return child(ValidationOutcomePopupHeader::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object ValidationOutcomePopupHeaderStyle : StyleSheet("ValidationOutcomePopupHeaderStyle", isStatic = true) {
    val container by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.center
        alignItems = Align.center
        minHeight = 72.px
        backgroundColor = WHITE
        padding(horizontal = 32.px)
        borderBottom(width = 1.px, color = BORDER_GRAY, style = BorderStyle.solid)
    }
    val titleField by css {
        flex(flexBasis = 100.pct)
        alignSelf = Align.center
    }
    val closeIcon by css {
        alignSelf = Align.center
        height = 24.px
        width = 24.px
        borderRadius = 50.pct
    }
}