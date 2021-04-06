package ui.components.options.menu

import css.animation.LoadingSpinner
import css.const.BORDER_GRAY
import css.const.HL7_RED
import css.const.WHITE
import css.text.TextStyle
import kotlinx.browser.document
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.onChange
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import react.*
import react.dom.defaultValue
import styled.*
import ui.components.buttons.imageButton
import ui.components.options.IgSelectorStyle
import ui.components.tabs.entrytab.ManualEntryButtonBarStyle

external interface TextFieldEntryProps : RProps {
    var onSubmitEntry: (String) -> Boolean
    var currentEntry: String
    var heading: String
    var explanation: String
    var buttonLabel: String
    var errorMessage: String
    var successMessage: String
}

class TextFieldEntryState : RState {
    var displayingError = false
    var displayingSuccess = true
    var validating = false
}

class TextFieldEntry : RComponent<TextFieldEntryProps, TextFieldEntryState>() {
    private val textInputId = "text_entry_field"

    override fun RBuilder.render() {
        styledDiv {
            css {
                +IgSelectorStyle.mainDiv
            }
            styledSpan {
                css {
                    +TextStyle.optionName
                }
                +props.heading
            }
            styledSpan {
                css {
                    +TextStyle.optionsDetailText
                    +TextFieldEntryStyle.detailsText
                }
                +props.explanation
            }
            styledDiv {
                css {
                    +TextFieldEntryStyle.textFieldAndGButtonDiv
                }
                styledInput {
                    css {
                        +TextFieldEntryStyle.entryTextArea
                    }
                    attrs {
                        type = InputType.text
                        defaultValue = props.currentEntry
                        id = textInputId
                        onChangeFunction = {
                            setState {
                                displayingError = false
                                displayingSuccess = false
                            }
                        }
                    }
                }
                if (state.validating) {
                    styledDiv {
                        css {
                            +ManualEntryButtonBarStyle.spinner
                        }
                    }
                } else {
                    imageButton {
                        backgroundColor = WHITE
                        borderColor = HL7_RED
                        image = "images/validate_red.png"
                        label = props.buttonLabel
                        onSelected = {
                            setState {
                                validating = true
                                if (props.onSubmitEntry((document.getElementById(textInputId) as HTMLInputElement).value)) {
                                    displayingError = false
                                    displayingSuccess = true
                                    validating = false
                                } else {
                                    displayingError = true
                                    displayingSuccess = false
                                    validating = false
                                }
                            }
                        }
                    }
                }
            }
            if (state.displayingError || state.displayingSuccess) {
                styledSpan {
                    css {
                        if (state.displayingSuccess) {
                            +TextStyle.optionStatusSuccess
                        } else {
                            +TextStyle.optionStatusFail
                        }
                    }
                    if (state.displayingSuccess) {
                        +props.successMessage
                    } else {
                        +props.errorMessage
                    }
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.textEntryField(handler: TextFieldEntryProps.() -> Unit): ReactElement {
    return child(TextFieldEntry::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object TextFieldEntryStyle : StyleSheet("TextFieldEntryStyle", isStatic = true) {
    val mainDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        padding(horizontal = 8.px)
    }
    val detailsText by css {
        padding(top = 8.px, bottom = 16.px)
    }
    val textFieldAndGButtonDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        alignItems = Align.center
    }
    val entryTextArea by css {
        display = Display.inlineBlock
        verticalAlign = VerticalAlign.middle
        resize = Resize.none
        width = 33.pct
        height = 42.px
        marginRight = 16.px
        outline = Outline.none
        padding(horizontal = 16.px)
        border(width = 1.px, color = BORDER_GRAY, style = BorderStyle.solid)
        backgroundColor = Color.transparent
        justifyContent = JustifyContent.center
        +TextStyle.optionsDetailText
    }
    val spinner by css {
        height = 32.px
        width = 32.px
        margin(horizontal = 32.px, vertical = 8.px)
        alignSelf = Align.center
        +LoadingSpinner.loadingIndicator
    }
    val errorMessage by css {

    }
}