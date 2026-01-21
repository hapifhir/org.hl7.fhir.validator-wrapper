package ui.components.options

import Polyglot
import context.LocalizationContext
import context.ValidationContext
import css.const.WHITE
import css.const.SWITCH_GRAY
import css.text.TextStyle
import kotlinx.css.*
import react.*
import ui.components.buttons.imageButton
import web.dom.document
import kotlinx.html.InputType
import kotlinx.html.id
import utils.getJS

import constants.ANY_EXTENSION

import web.html.HTMLInputElement
import react.dom.attrs
import react.dom.defaultValue
import styled.*
import ui.components.options.menu.TextFieldEntryStyle
import ui.components.options.menu.checkboxWithDetails

external interface AddExtensionProps : Props {}

class AddExtensionState : State {
}



class AddExtension : RComponent<AddExtensionProps, AddExtensionState>() {
    val textInputId = "extension_entry"
    init {
        state = AddExtensionState()
    }

    override fun RBuilder.render() {
        LocalizationContext.Consumer { localizationContext ->
            ValidationContext.Consumer { validationContext ->
                val polyglot = localizationContext?.polyglot ?: Polyglot()
                val extensionSet = validationContext?.extensionSet ?: emptySet()

                styledDiv {
                    css {
                        +AddExtensionStyle.mainDiv
                    }
                    styledSpan {
                        css {
                            +TextStyle.optionsDetailText
                            +IgSelectorStyle.title
                        }
                        +polyglot.t("options_extensions_description")
                    }
                    styledSpan {
                        checkboxWithDetails {
                            name = polyglot.t("options_extensions_checkbox")
                            selected = extensionSet.contains(ANY_EXTENSION)
                            hasDescription = false
                            onChange = { checked ->
                                val newExtensionSet = if (checked) {
                                    setOf(ANY_EXTENSION)
                                } else {
                                    emptySet()
                                }
                                validationContext?.updateExtensionSet?.invoke(newExtensionSet.toMutableSet(), false)
                                validationContext?.setSessionId?.invoke("")
                            }
                        }
                    }
                    if (!extensionSet.contains(ANY_EXTENSION)) {
                        styledSpan {
                            css {
                                +TextFieldEntryStyle.textFieldAndAddButtonDiv
                            }
                            styledInput {
                                css {
                                    +TextFieldEntryStyle.entryTextArea
                                }
                                attrs {
                                    type = InputType.text
                                    defaultValue = "http://"
                                    id = textInputId
                                }
                            }
                            styledSpan {
                                imageButton {
                                    backgroundColor = WHITE
                                    borderColor = SWITCH_GRAY
                                    image = "images/add_circle_black_24dp.svg"
                                    label = polyglot.t("options_ig_add")
                                    onSelected = {
                                        val extension = (document.getElementById(textInputId) as HTMLInputElement).value
                                        validationContext?.updateExtensionSet?.invoke(
                                            (extensionSet + extension).toMutableSet(),
                                            false
                                        )
                                        validationContext?.setSessionId?.invoke("")
                                    }
                                }
                            }
                        }
                        styledDiv {
                            css {
                                padding(top = 24.px)
                                + if (extensionSet.isEmpty()) TextStyle.optionsDetailText else TextStyle.optionName
                            }
                            val polyglotKey = if (extensionSet.isEmpty()) { "options_extensions_not_added"} else { "options_extensions_added"}
                            +polyglot.t(polyglotKey, getJS(arrayOf(Pair("addedExtensions", extensionSet.size.toString()))))
                        }
                        styledDiv {
                            css {
                                +IgSelectorStyle.selectedIgsDiv
                                if (!extensionSet.isEmpty()) {
                                    padding(top = 16.px)
                                }
                            }
                            extensionSet.forEach { _url ->
                                urlDisplay {
                                    url = _url
                                    onDelete = {
                                        validationContext?.updateExtensionSet?.invoke(
                                            (extensionSet - _url).toMutableSet(),
                                            false
                                        )
                                        validationContext?.setSessionId?.invoke("")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.addExtension(handler: AddExtensionProps.() -> Unit) {
    return child(AddExtension::class) {
        this.attrs(handler)
    }
}

object AddExtensionStyle : StyleSheet("AddExtensionStyle", isStatic = true) {
    val mainDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        padding(horizontal = 8.px)
    }
    val title by css {
        paddingBottom = 16.px
    }
    val selectedIgsDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        flexWrap = FlexWrap.wrap
    }
}














