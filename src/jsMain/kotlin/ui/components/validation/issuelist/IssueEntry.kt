package ui.components.validation.issuelist

import css.const.*
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.borderBottom
import kotlinx.css.properties.borderLeft
import kotlinx.css.properties.borderRight
import kotlinx.css.properties.borderTop
import kotlinx.html.js.onMouseDownFunction
import kotlinx.html.js.onMouseOutFunction
import kotlinx.html.js.onMouseOverFunction
import model.IssueSeverity
import model.ValidationMessage
import react.*
import react.dom.attrs
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan

external interface IssueEntryProps : Props {
    var validationMessage: ValidationMessage
    var highlighted: Boolean

    var onMouseOver: (Boolean) -> Unit
    var onMouseDown: () -> Unit
}

/**
 * A single list entry for a validation issue. Displays the type, line number, and explanation.
 */
class IssueEntry : RComponent<IssueEntryProps, State>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +IssueEntryStyle.issueContainer
                val highlightColor = when (props.validationMessage.getLevel()) {
                    IssueSeverity.INFORMATION -> INFO_BLUE
                    IssueSeverity.WARNING -> WARNING_YELLOW
                    IssueSeverity.ERROR -> ERROR_ORANGE
                    IssueSeverity.FATAL -> FATAL_PINK
                    else -> BORDER_GRAY
                }
                if (props.highlighted) {
                    background = "repeating-linear-gradient(\n" +
                            "  45deg,\n" +
                            "  ${WHITE},\n" +
                            "  ${WHITE} 10px,\n" +
                            "  ${highlightColor.changeAlpha(0.2)} 10px,\n" +
                            "  ${highlightColor.changeAlpha(0.2)} 20px\n" +
                            ");"
                }
                borderLeft(width = 4.px, style = BorderStyle.solid, color = highlightColor)
            }
            attrs {
                onMouseOverFunction = {
                    props.onMouseOver(true)
                }
                onMouseOutFunction = {
                    props.onMouseOver(false)
                }
                onMouseDownFunction = {
                    props.onMouseDown()
                }
            }
            span {

                styledDiv {
                    css {
                        +IssueEntryStyle.levelAndLineNumber
                        +TextStyle.issueType
                    }
                    + "${props.validationMessage.getLevel().display} " }
                styledDiv {
                    css {
                        +IssueEntryStyle.levelAndLineNumber
                        +TextStyle.issueLineAndColumn
                    }
                    + "Line: ${props.validationMessage.getLine()}, Col:${props.validationMessage.getCol()}" }

            }
            styledSpan {
                css {
                    +IssueEntryStyle.messageDetails
                    +TextStyle.codeTextBase
                }
                +props.validationMessage.getMessage()
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.issueEntry(handler: IssueEntryProps.() -> Unit) {
    return child(IssueEntry::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object IssueEntryStyle : StyleSheet("IssueEntryStyle", isStatic = true) {
    val issueContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        minHeight = 64.px
        width = 100.pct
        backgroundColor = WHITE
        borderTop(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        borderRight(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        borderBottom(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        padding(16.px)
        boxSizing = BoxSizing.borderBox
    }
    val levelAndLineNumber by css {
        paddingRight = 16.px
        minWidth = 25.pct
        alignSelf = Align.center
    }
    val messageDetails by css {
        flex(flexBasis = 100.pct)
        alignSelf = Align.center
        overflowWrap = OverflowWrap.anywhere
    }
}