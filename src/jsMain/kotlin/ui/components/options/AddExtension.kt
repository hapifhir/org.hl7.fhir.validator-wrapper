package ui.components.options

import Polyglot
import css.const.HL7_RED
import css.const.WHITE
import css.const.SWITCH_GRAY
import css.text.TextStyle
import kotlinx.css.*
import model.PackageInfo
import react.*
import ui.components.buttons.imageButton
import ui.components.options.menu.dropDownMultiChoice
import api.sendIGVersionsRequest
import kotlinx.browser.document
import kotlinx.coroutines.launch
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import utils.getJS

import mainScope
import model.CliContext
import org.w3c.dom.HTMLInputElement
import react.dom.attrs
import react.dom.defaultValue
import styled.*
import ui.components.options.menu.TextFieldEntryStyle
import ui.components.options.menu.checkboxWithDetails

external interface AddExtensionProps : Props {
    var addedExtensionSet : MutableSet<String>
    var onUpdateExtension : (String, Boolean) -> Unit
    var updateCliContext : (CliContext) -> Unit
    var cliContext : CliContext
    var onUpdateAnyExtension : (Boolean) -> Unit
    var polyglot: Polyglot
}

class AddExtensionState : State {
}

class AddExtension : RComponent<AddExtensionProps, AddExtensionState>() {
    private val textInputId = "text_entry_field"
    init {
        state = AddExtensionState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +AddExtensionStyle.mainDiv
            }
            styledSpan {
                css {
                    +TextStyle.optionsDetailText
                    +IgSelectorStyle.title
                }
                +props.polyglot.t("options_extensions_description")
            }
            styledSpan {
                checkboxWithDetails {
                    name = props.polyglot.t("options_extensions_checkbox")
                    selected = anyChecked()
                    hasDescription = false
                    onChange = {
                        props.onUpdateAnyExtension(it)
                    }
                }
            }
            if (!anyChecked()) {
                styledSpan {
                    css {
                        +TextFieldEntryStyle.textFieldAndGButtonDiv
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
                            label = props.polyglot.t("options_ig_add")
                            onSelected = {
                                props.onUpdateExtension((document.getElementById(textInputId) as HTMLInputElement).value, false)
                            }
                        }
                    }
                }
                styledDiv {
                    css {
                        padding(top = 24.px)
                        + if (props.addedExtensionSet.isEmpty()) TextStyle.optionsDetailText else TextStyle.optionName
                    }
                    val polyglotKey = if (props.addedExtensionSet.isEmpty()) { "options_extensions_not_added"} else { "options_extensions_added"}
                    +props.polyglot.t(polyglotKey, getJS(arrayOf(Pair("addedExtensions", props.addedExtensionSet.size.toString()))))
                }
                styledDiv {
                    css {
                        +IgSelectorStyle.selectedIgsDiv
                        if (!props.addedExtensionSet.isEmpty()) {
                            padding(top = 16.px)
                        }
                    }
                    props.addedExtensionSet.forEach { _url ->
                        extensionDisplay {
                            polyglot = props.polyglot
                            url = _url
                            onDelete = {
                                props.onUpdateExtension(_url, true)
                            }
                        }
                    }
                }
            }
        }
    }
    private fun anyChecked() : Boolean {
        return props.addedExtensionSet.contains("any")
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














