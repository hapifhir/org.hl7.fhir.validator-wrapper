package ui.components.validation

import kotlinx.css.*
import model.MessageFilter
import model.ValidationMessage
import model.ValidationOutcome
import react.RReadableRef
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.validation.codeissuedisplay.codeIssueDisplay
import ui.components.validation.issuelist.issueEntryList

external interface FileValidationResultsProps : RProps {
    var validationOutcome: ValidationOutcome
    var messageFilter: MessageFilter
}

class FileValidationResultsState : RState {
    var highlightedMessages: List<ValidationMessage> = emptyList()
}

/**
 * A React component displaying the code with highlighted issues, alongside the list of issues with associated details
 * and line numbers. Layout will change depending on current horizontal space for display.
 */
class FileValidationResults : RComponent<FileValidationResultsProps, FileValidationResultsState>() {

    init {
        state = FileValidationResultsState()
    }

    //var editorRefFV : RReadableRef<Nothing> = createRef<Nothing>()

    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileValidationResultsStyle.parentContainer
            }
            styledDiv {
                css {
                    +FileValidationResultsStyle.containerLeft
                }
                codeIssueDisplay {
                    validationOutcome = props.validationOutcome
                    messageFilter = props.messageFilter
                    highlightedMessages = state.highlightedMessages
                    onHighlight = { highlighted, list ->
                        setState {
                            highlightedMessages = if (highlighted) list else emptyList()
                        }
                    }
                    //editorRef = editorRefFV
                }
            }
            styledDiv {
                css {
                    +FileValidationResultsStyle.containerRight
                }
                issueEntryList {
                    validationOutcome = props.validationOutcome
                    messageFilter = props.messageFilter
                    highlightedMessages = state.highlightedMessages
                    onHighlight = { highlighted, list ->
                        setState {
                            highlightedMessages = if (highlighted) list else emptyList()
                        }
                    }
                   // editorRef = editorRefFV
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.fileValidationResults(handler: FileValidationResultsProps.() -> Unit): ReactElement {
    return child(FileValidationResults::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object FileValidationResultsStyle : StyleSheet("FileValidationSummaryStyle", isStatic = true) {
    val parentContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        justifyContent = JustifyContent.start
        overflowY = Overflow.auto
        flexGrow = 1.0
        padding(horizontal = 16.px)
        media(query = "(min-width: 1200px) and (orientation:landscape)", block = ruleSet {
            flexDirection = FlexDirection.row
        })
    }
    val containerLeft by css {
        marginRight = 0.px
        marginBottom = 8.px
        width = 100.pct
        media(query = "(min-width: 1200px) and (orientation:landscape)", block = ruleSet {
            marginBottom = 0.px
            marginRight = 8.px
            width = 50.pct
        })
    }
    val containerRight by css {
        marginLeft = 0.px
        marginTop = 8.px
        width = 100.pct
        overflowY = Overflow.auto
        media(query = "(min-width: 1200px) and (orientation:landscape)", block = ruleSet {
            marginTop = 0.px
            marginLeft = 8.px
            width = 50.pct
        })
    }
}