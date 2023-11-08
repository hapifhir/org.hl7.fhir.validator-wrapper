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

external interface ValidationResultDialogHeaderProps : Props {
    var filename: String
    var onClose: () -> Unit
}

class ValidationResultDialogHeader : RComponent<ValidationResultDialogHeaderProps, State>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                +ValidationSummaryHeaderStyle.container
            }
            styledSpan {
                css {
                    +TextStyle.tabSectionHeading
                    +ValidationSummaryHeaderStyle.titleField
                }
                +props.filename
            }
            styledImg {
                css {
                    +ValidationSummaryHeaderStyle.closeIcon
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
fun RBuilder.validationResultDialogHeader(handler: ValidationResultDialogHeaderProps.() -> Unit) {
    return child(ValidationResultDialogHeader::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object ValidationSummaryHeaderStyle : StyleSheet("ValidationSummaryHeaderStyle", isStatic = true) {
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