package ui.components.validation

import Polyglot
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
import ui.components.tabs.heading
import ui.components.validation.issuelist.issueFilterButtonBar

external interface ValidationOutcomeContainerProps : Props {
    var validationOutcome: ValidationOutcome
    var polyglot: Polyglot
    var onClose: () -> Unit
    var inPage: Boolean
}

class ValidationOutcomeContainerState : State {
    var messageFilter: MessageFilter = MessageFilter()
}

class ValidationOutcomeContainer : RComponent<ValidationOutcomeContainerProps, ValidationOutcomeContainerState>() {

    init {
        state = ValidationOutcomeContainerState()
    }

    override fun RBuilder.render() {

            styledDiv {
                css {
                    +ValidationOutcomeContainerStyle.filterMenuContainer
                }
                heading {
                    text = props.polyglot.t("validation_results") + " (${state.messageFilter.determineNumberDisplayedIssues(props.validationOutcome.getMessages())})"
                }
                styledDiv {
                    css {
                        width = 32.px
                    }
                }
                issueFilterButtonBar {
                    polyglot = props.polyglot
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
                    +ValidationOutcomeContainerStyle.resultsDiv
                }
                fileValidationOutcome {
                    validationOutcome = props.validationOutcome
                    messageFilter = state.messageFilter
                    inPage = props.inPage
                }
            }
        }

}

/**
 * React Component Builder
 */
fun RBuilder.validationOutcomeContainer(handler: ValidationOutcomeContainerProps.() -> Unit) {
    return child(ValidationOutcomeContainer::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object ValidationOutcomeContainerStyle : StyleSheet("ValidationOutcomeContainerStyle", isStatic = true) {
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
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.left
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