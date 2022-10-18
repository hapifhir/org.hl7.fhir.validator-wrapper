package ui.components.tabs.entrytab

import Polyglot
import css.const.BORDER_GRAY
import css.text.TextStyle
import kotlinx.browser.document
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.html.id
import kotlinx.html.js.onInputFunction
import org.w3c.dom.HTMLTextAreaElement
import react.*
import react.dom.attrs
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledTextarea

external interface ManualEntryTextAreaProps : Props {
    var currentText: String
    var onTextUpdate: (String) -> Unit
    var placeholderText: String
}

class ManualEntryTextArea : RComponent<ManualEntryTextAreaProps, State>() {
    private val textAreaId = "manual_entry_field"
    override fun RBuilder.render() {
        styledDiv {
            css {
                +ManualEntryTextAreaStyle.mainContainer
            }
            styledTextarea {
                css {
                    +TextStyle.codeTextBase
                    +ManualEntryTextAreaStyle.textArea
                }
                attrs {
                    id = textAreaId
                    placeholder = props.placeholderText
                    onInputFunction = {
                        props.onTextUpdate((document.getElementById(textAreaId) as HTMLTextAreaElement).value)
                    }
                }
                if (props.currentText.isNotBlank()) {
                    +props.currentText
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.manualEntryTextArea(handler: ManualEntryTextAreaProps.() -> Unit) {
    return child(ManualEntryTextArea::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object ManualEntryTextAreaStyle : StyleSheet("ManualEntryTextAreaStyle") {
    val mainContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        justifyContent = JustifyContent.stretch
        alignContent = Align.stretch
        overflowY = Overflow.auto
        minHeight = 600.px
        border(width = 1.px, color = BORDER_GRAY, style = BorderStyle.solid)
    }
    val textArea by css {
        alignSelf = Align.stretch
        flexGrow = 1.0
        border = "none"
        resize = Resize.none
        outline = Outline.none
        overflowY = Overflow.auto
        padding(24.px)
    }
}