package ui.components.options

import Polyglot
import css.const.WHITE
import css.const.SWITCH_GRAY
import css.text.TextStyle
import kotlinx.css.*
import react.*
import ui.components.buttons.imageButton
import kotlinx.browser.document
import kotlinx.html.InputType
import kotlinx.html.id
import utils.getJS

import model.ValidationContext
import org.w3c.dom.HTMLInputElement
import react.dom.attrs
import react.dom.defaultValue
import styled.*
import ui.components.options.menu.TextFieldEntryStyle

external interface AddProfileProps : Props {
    var profileSet : MutableSet<String>
    var onUpdateProfileSet : (String, Boolean) -> Unit
    var updateValidationContext : (ValidationContext) -> Unit
    var validationContext : ValidationContext
    var polyglot: Polyglot
}

class AddProfileState : State {
}

class AddProfile : RComponent<AddProfileProps, AddProfileState>() {
    val textInputId = "profile_entry"
    init {
        state = AddProfileState()
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
                +props.polyglot.t("options_profiles_description")
            }


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
                        label = props.polyglot.t("options_ig_add")
                        onSelected = {
                            props.onUpdateProfileSet(
                                (document.getElementById(textInputId) as HTMLInputElement).value,
                                false
                            )
                        }
                    }
                }
            }
            styledDiv {
                css {
                    padding(top = 24.px)
                    +if (props.profileSet.isEmpty()) TextStyle.optionsDetailText else TextStyle.optionName
                }
                val polyglotKey = if (props.profileSet.isEmpty()) {
                    "options_profiles_not_added"
                } else {
                    "options_profiles_added"
                }
                +props.polyglot.t(
                    polyglotKey,
                    getJS(arrayOf(Pair("addedProfiles", props.profileSet.size.toString())))
                )
            }
            styledDiv {
                css {
                    +IgSelectorStyle.selectedIgsDiv
                    if (!props.profileSet.isEmpty()) {
                        padding(top = 16.px)
                    }
                }
                props.profileSet.forEach { _url ->
                    urlDisplay {
                        polyglot = props.polyglot
                        url = _url
                        onDelete = {
                            props.onUpdateProfileSet(_url, true)
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.addProfile(handler: AddProfileProps.() -> Unit) {
    return child(AddProfile::class) {
        this.attrs(handler)
    }
}

object AddProfileStyle : StyleSheet("AddProfileStyle", isStatic = true) {
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














