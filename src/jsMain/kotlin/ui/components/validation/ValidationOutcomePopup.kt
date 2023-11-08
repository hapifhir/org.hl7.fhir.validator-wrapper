package ui.components.validation

import Polyglot
import css.animation.FadeIn.quickFadeIn
import css.const.POPUP_SHADOW
import css.const.WHITE
import kotlinx.css.*
import model.ValidationOutcome
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv

external interface ValidationOutcomePopupProps : Props {
    var validationOutcome: ValidationOutcome
    var polyglot: Polyglot
    var onClose: () -> Unit
}

class ValidationOutcomePopup : RComponent<ValidationOutcomePopupProps, State>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                +ValidationOutcomePopupStyle.overlay
            }
            styledDiv {
                css {
                    +ValidationOutcomePopupStyle.content
                }
                styledDiv {
                    css {
                        +ValidationOutcomeContainerStyle.mainContainer
                    }

                    styledDiv {
                        css {
                            +ValidationOutcomeContainerStyle.headerContainer
                        }
                        validationOutcomePopupHeader {
                            filename = props.validationOutcome.getFileInfo().fileName
                            onClose = {
                                props.onClose()
                            }
                        }
                    }
                    styledDiv {
                        css {
                           + ValidationOutcomePopupStyle.resultsContainer
                        }
                        validationOutcomeContainer {
                            polyglot = props.polyglot
                            validationOutcome = props.validationOutcome
                            onClose = {
                                props.onClose()
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.validationOutcomePopup(handler: ValidationOutcomePopupProps.() -> Unit) {
    return child(ValidationOutcomePopup::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object ValidationOutcomePopupStyle : StyleSheet("ValidationOutcomePopupStyle", isStatic = true) {
    val overlay by css {
        display = Display.flex
        position = Position.fixed
        zIndex = 8
        left = 0.px
        top = 0.px
        right = 0.px
        bottom = 0.px
        backgroundColor = POPUP_SHADOW
        justifyContent = JustifyContent.center
        alignContent = Align.center
        quickFadeIn()
    }
    val resultsContainer by css {
        paddingLeft = 16.px
        paddingRight = 16.px
        display = Display.flex
        flexDirection = FlexDirection.column
        height = 100.pct - (72.px + 8.px)
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