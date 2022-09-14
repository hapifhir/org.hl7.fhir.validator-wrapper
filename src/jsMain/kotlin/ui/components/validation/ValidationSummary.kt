package ui.components.validation

import css.const.BORDER_GRAY
import css.const.WHITE
import kotlinx.css.*
import kotlinx.css.properties.border
import model.MessageFilter
import model.ValidationOutcome
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.validation.issuelist.issueFilterButtonBar

external interface ValidationSummaryProps : Props {
    var validationOutcome: ValidationOutcome
    var onClose: () -> Unit
}

class ValidationSummaryState : State {
    var messageFilter: MessageFilter = MessageFilter()
}

class ValidationSummary : RComponent<ValidationSummaryProps, ValidationSummaryState>() {

    init {
        state = ValidationSummaryState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +ValidationSummaryStyle.mainContainer
            }
            styledDiv {
                css {
                    +ValidationSummaryStyle.headerContainer
                }
                validationSummaryHeader {
                    filename = props.validationOutcome.getFileInfo().fileName
                    onClose = {
                        props.onClose()
                    }
                }
            }
            styledDiv {
                css {
                    +ValidationSummaryStyle.filterMenuContainer
                }
                issueFilterButtonBar {
                    messageFilter = state.messageFilter
                    onUpdated = { newMessageFilter ->
                        setState {
                            messageFilter = newMessageFilter
                        }
                    }
                }
            }
            styledDiv {
                css {
                    +ValidationSummaryStyle.resultsDiv
                }
                fileValidationResults {
                    validationOutcome = props.validationOutcome
                    messageFilter = state.messageFilter
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.validationSummary(handler: ValidationSummaryProps.() -> Unit) {
    return child(ValidationSummary::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object ValidationSummaryStyle : StyleSheet("ValidationSummaryStyle", isStatic = true) {
    val mainContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        height = 100.pct
        minHeight = 100.pct
        backgroundColor = WHITE
        border(width = 1.px, color = BORDER_GRAY, style = BorderStyle.solid, borderRadius = 2.px)
    }
    val headerContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        justifyContent = JustifyContent.center
    }
    val filterMenuContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        justifyContent = JustifyContent.center
        padding(horizontal = 16.px)
    }
    val resultsDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        justifyContent = JustifyContent.center
        overflowY = Overflow.auto
        flexGrow = 1.0
        margin(vertical = 8.px)
    }
}