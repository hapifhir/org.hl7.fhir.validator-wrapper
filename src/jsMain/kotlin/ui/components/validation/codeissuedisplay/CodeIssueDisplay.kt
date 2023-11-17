package ui.components.validation.codeissuedisplay

import css.*
import emotion.css.css
import ui.components.ace.aceEditor
import kotlinx.css.*

import model.IssueSeverity

import model.MessageFilter
import model.ValidationMessage
import model.ValidationOutcome
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.ace.AceAnnotation
import ui.components.ace.AceMarker
import ui.components.ace.AceOptions
import ui.components.ace.setAnnotations
import ui.components.ace.getCurson

external interface CodeIssueDisplayProps : Props {
    var validationOutcome: ValidationOutcome
    var messageFilter: MessageFilter
    var highlightedMessages: List<ValidationMessage>
    var onHighlight: (Boolean, List<ValidationMessage>) -> Unit
    var editorRef:RefObject<Nothing>
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

fun issueSeverityToAceCSSClasses(issueSeverity: IssueSeverity, isSelected: Boolean) : String {
    return if (isSelected) {"$ACE_EDITOR_SELECTED "} else {""} + "$ACE_EDITOR_HIGHLIGHT " + when (issueSeverity) {
        IssueSeverity.INFORMATION -> ACE_EDITOR_INFO
        IssueSeverity.WARNING -> ACE_EDITOR_WARNING
        IssueSeverity.ERROR -> ACE_EDITOR_ERROR
        IssueSeverity.FATAL -> ACE_EDITOR_FATAL
        else -> ACE_EDITOR_DEFAULT
    }
}

fun fileTypeToAceMode(fileType : String?) : String {
    if (fileType == null) {
        return "json"
    }
    return fileType
}

class CodeIssueDisplay : RComponent<CodeIssueDisplayProps, State>() {

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

        setAnnotations(props.editorRef, aceAnnotations)
    }

    override fun RBuilder.render() {

        val aceMarkers = props.validationOutcome.getMessages().map{
            message ->
            AceMarker (
                message.getLine() - 1,
                0,
                message.getLine(),
                0,
                issueSeverityToAceCSSClasses(message.getLevel(), props.highlightedMessages?.contains(message)),
                "line",
                true
            )
        }.toTypedArray()

        aceEditor {
            attrs {
                ref = props.editorRef
                mode = fileTypeToAceMode(props.validationOutcome.getFileInfo().fileType)
                theme = "github"
                height = "100%"
                width = "100%"
                showPrintMargin = false
                readOnly = true

                value = props.validationOutcome.getFileInfo().fileContent
                setOptions = AceOptions(false)
                markers = aceMarkers
                onCursorChange = {  getCurson(props.editorRef) }
            }
        }

        styledDiv {
            css {
                position = Position.absolute
                zIndex = 1;
                right = 16.px
                bottom = 16.px
            }
            + "0:0"
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.codeIssueDisplay(handler: CodeIssueDisplayProps.() -> Unit) {
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