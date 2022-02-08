package ui.components.validation.codeissuedisplay

import css.const.INACTIVE_GRAY
import css.text.TextStyle
import react.*
import styled.css
import styled.styledDiv
import styled.styledSpan

external interface CodeLineProps : RProps {
    var lineOfText: String
}

class CodeLine : RComponent<CodeLineProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +CodeIssueStyle.codeIssueContainer
            }
            styledSpan {
                css {
                    +TextStyle.codeTextBase
                    +CodeIssueDisplayStyle.lineStyle
                }
                +props.lineOfText
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.codeLine(handler: CodeLineProps.() -> Unit): ReactElement {
    return child(CodeLine::class) {
        this.attrs(handler)
    }
}

