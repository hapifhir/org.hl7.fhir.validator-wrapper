package ui.components.options

import Polyglot
import css.const.BORDER_GRAY
import css.const.WHITE
import css.const.SWITCH_GRAY
import css.text.TextStyle
import kotlinx.css.*
import react.*
import ui.components.buttons.imageButton
import kotlinx.browser.document
import kotlinx.css.properties.border
import kotlinx.html.InputType
import kotlinx.html.id
import model.BundleValidationRule
import utils.getJS

import model.CliContext
import mui.icons.material.Add
import org.w3c.dom.HTMLInputElement
import react.dom.attrs
import react.dom.defaultValue
import styled.*
import ui.components.options.menu.TextFieldEntryStyle

external interface AddBundleValidationRuleProps : Props {
    var bundleValidationRuleSet : MutableSet<BundleValidationRule>
    var onUpdateBundleValidationRuleSet : (BundleValidationRule, Boolean) -> Unit
    var updateCliContext : (CliContext) -> Unit
    var cliContext : CliContext
    var polyglot: Polyglot
}

class AddBundleValidationRuleState : State {
}

class AddBundleValidationRule : RComponent<AddBundleValidationRuleProps, AddProfileState>() {
    val ruleInputId = "bundle_validation_rule_entry"
    val profileInputId = "bundle_validation_profile_entry"
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
                +props.polyglot.t("options_bundle_validation_rules_description")
            }


            styledSpan {
                css {
                    display = Display.grid
                    gridTemplateColumns = GridTemplateColumns(LinearDimension.minContent, LinearDimension("1fr"))
                    gap = LinearDimension("10px")
                    alignItems = Align.start
                }
                styledSpan {
                    css {
                        gridColumn = GridColumn("1")
                        +AddBundleValidationRuleStyle.ruleEntryDetailText
                    }
                    +props.polyglot.t("options_bundle_validation_rules_rule_description")
                }
                styledInput {
                    css {
                        gridColumn = GridColumn("2")
                        +AddBundleValidationRuleStyle.ruleEntryTextArea
                    }
                    attrs {
                        type = InputType.text
                        defaultValue = ""
                        id = ruleInputId
                    }
                }
                styledSpan {
                    css {
                        gridColumn = GridColumn("1")
                        +AddBundleValidationRuleStyle.ruleEntryDetailText

                    }
                    +props.polyglot.t("options_bundle_validation_rules_profile_description")
                }
                styledInput {
                    css {
                        gridColumn = GridColumn("2")
                        +AddBundleValidationRuleStyle.ruleEntryTextArea
                    }
                    attrs {
                        type = InputType.text
                        defaultValue = ""
                        id = profileInputId
                    }
                }
                styledSpan {
                    css {
                        gridColumn = GridColumn("2")
                        width = 60.pct
                        textAlign = TextAlign.end
                    }
                    imageButton {
                        backgroundColor = WHITE
                        borderColor = SWITCH_GRAY
                        image = "images/add_circle_black_24dp.svg"
                        label = props.polyglot.t("options_ig_add")
                        onSelected = {
                            val rule = (document.getElementById(ruleInputId) as HTMLInputElement).value
                            val profile = (document.getElementById(profileInputId) as HTMLInputElement).value

                            val bundleValidationRule = BundleValidationRule().setRule(rule).setProfile(profile)

                            props.onUpdateBundleValidationRuleSet(
                                bundleValidationRule,
                                false
                            )
                        }
                    }
                }
            }
            styledDiv {
                css {
                    padding(top = 24.px)
                    +if (props.bundleValidationRuleSet.isEmpty()) TextStyle.optionsDetailText else TextStyle.optionName
                }
                val polyglotKey = if (props.bundleValidationRuleSet.isEmpty()) {
                    "options_bundle_validation_rules_not_added"
                } else {
                    "options_bundle_validation_rules_added"
                }
                +props.polyglot.t(
                    polyglotKey,
                    getJS(arrayOf(Pair("addedProfiles", props.bundleValidationRuleSet.size.toString())))
                )
            }
            styledDiv {
                css {
                    +IgSelectorStyle.selectedIgsDiv
                    if (!props.bundleValidationRuleSet.isEmpty()) {
                        padding(top = 16.px)
                    }
                }
                props.bundleValidationRuleSet.forEach { _rule ->
                    bundleValidationRuleDisplay {
                        rule = _rule
                        onDelete = {
                            props.onUpdateBundleValidationRuleSet(_rule, true)
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.addBundleValidationRule(handler: AddBundleValidationRuleProps.() -> Unit) {
    return child(AddBundleValidationRule::class) {
        this.attrs(handler)
    }
}

object AddBundleValidationRuleStyle : StyleSheet("AddBundleValidationRuleStyle", isStatic = true) {
    val mainDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        padding(horizontal = 8.px)
    }
    val ruleEntryDetailText by css {
        display = Display.inlineBlock
        verticalAlign = VerticalAlign.middle
        resize = Resize.none
        height = 42.px
        paddingTop = 16.px
    }
    val ruleEntryTextArea by css {
        display = Display.inlineBlock
        verticalAlign = VerticalAlign.middle
        resize = Resize.none
        width = 60.pct
        height = 42.px
        marginRight = 16.px
        outline = Outline.none
        //padding(horizontal = 16.px)
        border(width = 1.px, color = BORDER_GRAY, style = BorderStyle.solid)
        backgroundColor = Color.transparent
        justifyContent = JustifyContent.center

    }
    val title by css {
        paddingBottom = 16.px
    }
    val selectedRulesDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        flexWrap = FlexWrap.wrap
    }
}














