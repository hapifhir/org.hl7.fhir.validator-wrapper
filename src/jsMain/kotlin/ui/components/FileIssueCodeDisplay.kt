package ui.components

import css.component.FileErrorDisplayStyle
import css.text.TextStyle
import model.ValidationOutcome
import react.*
import styled.css
import styled.styledDiv
import styled.styledSpan

external interface FileIssueCodeDisplayProps : RProps {
    var validationOutcome: ValidationOutcome
}

class FileIssueCodeDisplayState : RState {

}

class FileIssueCodeDisplayComponent : RComponent<FileIssueCodeDisplayProps, FileIssueCodeDisplayState>() {
    init {
        state = FileIssueCodeDisplayState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileErrorDisplayStyle.mainDivCode
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
                            +TextStyle.codeDark
                            +FileErrorDisplayStyle.lineStyle
                        }
                        +text
                    }
                }
            }
        }
    }
}

fun RBuilder.fileIssueCodeDisplayComponent(handler: FileIssueCodeDisplayProps.() -> Unit): ReactElement {
    return child(FileIssueCodeDisplayComponent::class) {
        this.attrs(handler)
    }
}

