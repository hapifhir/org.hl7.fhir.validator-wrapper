package ui.components.tabs.entrytab

import css.animation.FadeIn.fadeIn
import kotlinx.css.*
import model.IssueSeverity
import model.ValidationMessage
import model.ValidationOutcome
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.tabs.tabHeading
import ui.components.validation.issuelist.filteredIssueEntryList

external interface ManualEntryTabProps : RProps {}

class ManualEntryTabState : RState {
    var currentEnteredText = ""
    var validationOutcome: ValidationOutcome? = null
}

class ManualEntryTab : RComponent<ManualEntryTabProps, ManualEntryTabState>() {
    init {
        state = ManualEntryTabState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                justifyContent = JustifyContent.start
                overflowY = Overflow.auto
                padding(horizontal = 32.px, vertical = 16.px)
                fadeIn()
            }
            tabHeading {
                text = "Code"
            }
            manualEntryTextArea {
                state.currentEnteredText
                onTextUpdate = { str ->
                    setState {
                        currentEnteredText = str
                    }
                }
            }
            fileEntryButtonBar {
                onValidateRequested = {

                }
            }
            state.validationOutcome?.let{
                filteredIssueEntryList {
                    validationOutcome = state.validationOutcome!!
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.manualEntryTab(handler: ManualEntryTabProps.() -> Unit): ReactElement {
    return child(ManualEntryTab::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object ManualEntryTabStyle : StyleSheet("ManualEntryTabStyle") {
    val mainContainer by css {
        display = Display.flex
        height = 96.px
        flex(flexBasis = 100.pct)
        padding(horizontal = 32.px)
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.flexStart
        alignItems = Align.center
    }
    val titleField by css {
        flexGrow = 1.0
        paddingLeft = 16.px
    }
}