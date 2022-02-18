package ui.components.validation.codeissuedisplay

import kotlinext.js.Object
import ui.components.ace.aceEditor
import kotlinx.css.*
import model.IssueSeverity

import model.MessageFilter
import model.ValidationMessage
import model.ValidationOutcome
import react.*
import styled.StyleSheet
import styled.styledDiv
import ui.components.ace.AceAnnotation
import ui.components.ace.AceMarker
import ui.components.ace.AceOptions

external interface CodeIssueDisplayProps : RProps {
    var validationOutcome: ValidationOutcome
    var messageFilter: MessageFilter
    var highlightedMessages: List<ValidationMessage>
    var onHighlight: (Boolean, List<ValidationMessage>) -> Unit
}

fun issueSeverityToAceAnnotation(issueSeverity: IssueSeverity): String {
    return when (issueSeverity) {
        IssueSeverity.FATAL -> "error"
        IssueSeverity.ERROR -> "error"
        IssueSeverity.WARNING -> "warning"
        IssueSeverity.INFORMATION -> "info"
        IssueSeverity.NULL -> "info"
    }
}

class CodeIssueDisplay : RComponent<CodeIssueDisplayProps, RState>() {

    var editorRef = createRef<Nothing>()

    /*
    This is here because the annotations set in the aceEditor props
    will get blown away when the text content changes. So, with every
    component mount, we need to reset annotations.
     */
    override fun componentDidMount() {
        val aceAnnotations = props.validationOutcome.getMessages().map { message ->
            AceAnnotation(
                message.getLine() - 1,
                message.getCol(),
                message.getMessage(),
                issueSeverityToAceAnnotation(message.getLevel())
            )
        }.toTypedArray()

        editorRef.asDynamic().current.editor.getSession().setAnnotations(aceAnnotations)
    }

    override fun RBuilder.render() {

        val aceMarkers = props.validationOutcome.getMessages().map{
            message ->
            AceMarker (
                message.getLine() - 1,
                0,
                message.getLine(),
                0,
                "editor-focus-error",
                "line", true
            )
        }.toTypedArray()

        aceEditor {
            attrs {
                ref = editorRef
                mode = "json"
                theme = "github"
                height = "100%"
                width = "100%"
                showPrintMargin = false
                readOnly = true

                value = props.validationOutcome.getFileInfo().fileContent
                setOptions = AceOptions(false)
                markers = aceMarkers
            }

        }
        /*
        styledDiv {
            css {
                +CodeIssueDisplayStyle.codeIssueDisplayContainer
            }
            val fileAsLines = props.validationOutcome.getFileInfo().fileContent.lines()
            val lineMap = props.validationOutcome.getMessages().groupBy({ it.getLine() }, { it })
            fileAsLines.forEachIndexed { index, text ->
                styledSpan {
                    css {
                        +TextStyle.codeTextLineNumber
                        +CodeIssueDisplayStyle.lineStyle
                        marginRight = 10.px;
                    }
                    +"${index + 1}"
                }
                if (lineMap.containsKey(index + 1) && (props.messageFilter.filter(lineMap[index + 1]).isNotEmpty())) {
                    codeIssue {
                        validationMessages = props.messageFilter.filter(lineMap[index + 1])
                        lineOfText = text
                        highlighted =
                            props.highlightedMessages.intersect(props.messageFilter.filter(lineMap[index + 1]))
                                .isNotEmpty()
                        onMouseOver = { highlighted ->
                            props.onHighlight(highlighted, props.messageFilter.filter(lineMap[index + 1]))
                        }
                    }
                } else {
                    codeLine {
                        lineOfText = text
                    }
                }
            }
        }
        */
        
    }
}

/**
 * React Component Builder
 */
fun RBuilder.codeIssueDisplay(handler: CodeIssueDisplayProps.() -> Unit): ReactElement {
    return child(CodeIssueDisplay::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object CodeIssueDisplayStyle : StyleSheet("CodeIssueDisplayStyle", isStatic = true) {
    val codeIssueDisplayContainer by css {
        display = Display.grid
        gridTemplateColumns = GridTemplateColumns("min-content auto")
        height = 100.pct
        width = 100.pct
    }
    val lineStyle by css {
        display = Display.flowRoot
        whiteSpace = WhiteSpace.preWrap
        overflowWrap = OverflowWrap.breakWord
        wordWrap = WordWrap.breakWord
    }
}