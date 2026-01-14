package ui.components.validation

import css.const.BORDER_GRAY
import kotlinx.css.*
import model.MessageFilter
import model.ValidationMessage
import model.ValidationOutcome
import web.window.window

import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.validation.codeissuedisplay.codeIssueDisplay
import ui.components.validation.issuelist.issueEntryList

external interface FileValidationOutcomeProps : Props {
    var validationOutcome: ValidationOutcome
    var messageFilter: MessageFilter
    var inPage: Boolean
}

class FileValidationOutcomeState : State {
    var highlightedMessages: List<ValidationMessage> = emptyList()
}

/**
 * A React component displaying the code with highlighted issues, alongside the list of issues with associated details
 * and line numbers. Layout will change depending on current horizontal space for display.
 */
class FileValidationOutcome : RComponent<FileValidationOutcomeProps, FileValidationOutcomeState>() {

    init {
        state = FileValidationOutcomeState()
    }

    var editorRefFV : RefObject<Nothing> = createRef<Nothing>()

    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileValidationOutcomeStyle.parentContainer
            }
            styledDiv {
                css {
                    +FileValidationOutcomeStyle.containerLeft
                    if (props.inPage) {
                        +FileValidationOutcomeStyle.pageMinHeight
                        +FileValidationOutcomeStyle.pageMaxHeight
                    }
                    else
                        +FileValidationOutcomeStyle.parentMaxHeight
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
                    editorRef = editorRefFV
                }
            }
            styledDiv {
                css {
                    +FileValidationOutcomeStyle.containerRight
                     if (props.inPage) {
                         +FileValidationOutcomeStyle.pageMaxHeight
                     }
                     else {
                         +FileValidationOutcomeStyle.parentMaxHeight
                         +FileValidationOutcomeStyle.parentMinHeight
                     }

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
                    editorRef = editorRefFV
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.fileValidationOutcome(handler: FileValidationOutcomeProps.() -> Unit) {
    return child(FileValidationOutcome::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object FileValidationOutcomeStyle : StyleSheet("FileValidationOutcomeStyle", isStatic = true) {
    val parentContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        justifyContent = JustifyContent.start

        overflowY = Overflow.auto
        flexGrow = 1.0
        media(query = "(min-width: 1200px) and (orientation:landscape)", block = ruleSet {
            flexDirection = FlexDirection.row
        })
    }
    val containerLeft by css {
        marginRight = 0.px
        marginBottom = 8.px
        borderColor = BORDER_GRAY
        borderStyle = BorderStyle.solid
        borderWidth = 1.px
        minHeight = 98.pct
        position = Position.relative

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

    val pageMaxHeight by css {
        maxHeight = window.innerHeight.px - 96.px
    }

    val pageMinHeight by css {
        minHeight = window.innerHeight.px - 96.px
    }

    val parentMinHeight by css {
        minHeight = 98.pct
    }

    val parentMaxHeight by css {
        maxHeight = 98.pct
    }

}