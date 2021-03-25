package ui.components.validation

import css.animation.FadeIn.quickFadeIn
import css.const.POPUP_SHADOW
import css.const.WHITE
import kotlinx.css.*
import model.ValidationOutcome
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv

external interface ValidationSummaryPopupProps : RProps {
    var validationOutcome: ValidationOutcome
    var onClose: () -> Unit
}

class ValidationSummaryPopup : RComponent<ValidationSummaryPopupProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                +ValidationSummaryPopupStyle.overlay
            }
            styledDiv {
                css {
                    +ValidationSummaryPopupStyle.content
                }
                validationSummary {
                    validationOutcome = props.validationOutcome
                    onClose = {
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
fun RBuilder.validationSummaryPopup(handler: ValidationSummaryPopupProps.() -> Unit): ReactElement {
    return child(ValidationSummaryPopup::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object ValidationSummaryPopupStyle : StyleSheet("ValidationSummaryPopupStyle", isStatic = true) {
    val overlay by css {
        display = Display.flex
        position = Position.fixed
        zIndex = 2
        left = 0.px
        top = 0.px
        right = 0.px
        bottom = 0.px
        backgroundColor = POPUP_SHADOW
        justifyContent = JustifyContent.center
        alignContent = Align.center
        quickFadeIn()
    }
    val content by css {
        display = Display.flex
        alignSelf = Align.center
        flexDirection = FlexDirection.column
        backgroundColor = WHITE
        height = 85.pct
        width = 90.pct
    }
}