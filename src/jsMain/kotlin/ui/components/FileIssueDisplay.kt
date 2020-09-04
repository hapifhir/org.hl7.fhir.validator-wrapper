package ui.components

import css.FileErrorDisplayStyle
import css.FileItemStyle
import css.TextStyle
import kotlinx.css.*
import kotlinx.html.js.onMouseOverFunction
import kotlinx.html.onMouseOver
import model.IssueSeverity
import model.ValidationOutcome
import react.*
import styled.*
import utils.getHighestIssueSeverity

external interface FileIssueDisplayProps : RProps {
    var validationOutcome: ValidationOutcome
}

class FileIssueDisplayState : RState {

}

class FileIssueDisplayComponent : RComponent<FileIssueDisplayProps, FileIssueDisplayState>() {
    init {
        state = FileIssueDisplayState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileErrorDisplayStyle.mainDiv
            }
            var fileAsLines = props.validationOutcome.getFileInfo().fileContent.lines()
            var lineMap = props.validationOutcome.getMessages().groupBy({ it.getLine() }, { it })
            fileAsLines.forEachIndexed { index, text ->
                if (lineMap.containsKey(index + 1)) {
                    fileIssueInstanceComponent {
                        validationMessages = lineMap[index + 1] ?: error("Bad validation message.")
                        lineOfText = text
                    }
                } else {
                    styledSpan {
                        css {
                            +TextStyle.code
                            +FileErrorDisplayStyle.lineStyle
                        }
                        +text
                    }
                }
            }
        }
    }
}

fun RBuilder.fileIssueDisplayComponent(handler: FileIssueDisplayProps.() -> Unit): ReactElement {
    return child(FileIssueDisplayComponent::class) {
        this.attrs(handler)
    }
}

