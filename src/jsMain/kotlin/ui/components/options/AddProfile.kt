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

import web.html.HTMLInputElement
import react.dom.attrs
import react.dom.defaultValue
import styled.*
import ui.components.options.menu.TextFieldEntryStyle

external interface AddProfileProps : Props {}

class AddProfileState : State {
}

class AddProfile : RComponent<AddProfileProps, AddProfileState>() {
    val textInputId = "profile_entry"
    init {
        state = AddProfileState()
    }

    override fun RBuilder.render() {
        LocalizationContext.Consumer { localizationContext ->
            ValidationContext.Consumer { validationContext ->
                val polyglot = localizationContext?.polyglot ?: Polyglot()
                val profileSet = validationContext?.profileSet ?: emptySet()

                styledDiv {
                    css {
                        +AddExtensionStyle.mainDiv
                    }
                    styledSpan {
                        css {
                            +TextStyle.optionsDetailText
                            +IgSelectorStyle.title
                        }
                        +polyglot.t("options_profiles_description")
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
                                label = polyglot.t("options_ig_add")
                                onSelected = {
                                    val profile = (document.getElementById(textInputId) as HTMLInputElement).value
                                    validationContext?.updateProfileSet?.invoke(
                                        (profileSet + profile).toMutableSet(),
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
                            +if (profileSet.isEmpty()) TextStyle.optionsDetailText else TextStyle.optionName
                        }
                        val polyglotKey = if (profileSet.isEmpty()) {
                            "options_profiles_not_added"
                        } else {
                            "options_profiles_added"
                        }
                        +polyglot.t(
                            polyglotKey,
                            getJS(arrayOf(Pair("addedProfiles", profileSet.size.toString())))
                        )
                    }
                    styledDiv {
                        css {
                            +IgSelectorStyle.selectedIgsDiv
                            if (!profileSet.isEmpty()) {
                                padding(top = 16.px)
                            }
                        }
                        profileSet.forEach { _url ->
                            urlDisplay {
                                url = _url
                                onDelete = {
                                    validationContext?.updateProfileSet?.invoke(
                                        (profileSet - _url).toMutableSet(),
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














