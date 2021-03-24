package ui.components.tabs.entrytab

import css.const.BORDER_GRAY
import css.text.TextStyle
import kotlinx.browser.document
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.html.id
import kotlinx.html.js.onInputFunction
import org.w3c.dom.HTMLTextAreaElement
import react.*
import react.dom.value
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledTextArea

external interface ManualEntryTextAreaProps : RProps {
    var currentText: String
    var onTextUpdate: (String) -> Unit
}

class ManualEntryTextArea : RComponent<ManualEntryTextAreaProps, RState>() {
    private val textAreaId = "manual_entry_field"

    override fun RBuilder.render() {
        styledDiv {
            css {
                +ManualEntryTextAreaStyle.mainContainer
            }
            styledTextArea {
                css {
                    +TextStyle.codeTextBase
                    +ManualEntryTextAreaStyle.textArea
                }
                attrs {
                    id = textAreaId
                    placeholder = "Enter Resource Manually..."
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
fun RBuilder.manualEntryTextArea(handler: ManualEntryTextAreaProps.() -> Unit): ReactElement {
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