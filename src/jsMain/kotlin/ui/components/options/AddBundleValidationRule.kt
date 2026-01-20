package ui.components.options

import Polyglot
import context.LocalizationContext
import context.ValidationContext
import css.const.BORDER_GRAY
import css.const.WHITE
import css.const.SWITCH_GRAY
import css.text.TextStyle
import kotlinx.css.*
import react.*
import ui.components.buttons.imageButton
import web.dom.document
import kotlinx.css.properties.border
import kotlinx.html.InputType
import kotlinx.html.id
import model.BundleValidationRule
import utils.getJS

import mui.icons.material.InfoOutlined
import mui.material.Tooltip

import web.html.HTMLInputElement
import react.dom.attrs
import react.dom.defaultValue
import react.dom.li
import react.dom.ul
import styled.*

external interface AddBundleValidationRuleProps : Props {}

class AddBundleValidationRuleState : State {}

class AddBundleValidationRule : RComponent<AddBundleValidationRuleProps, AddProfileState>() {
    val ruleInputId = "bundle_validation_rule_entry"
    val profileInputId = "bundle_validation_profile_entry"
    init {
        state = AddProfileState()
    }

    override fun RBuilder.render() {
        LocalizationContext.Consumer { localizationContext ->
            ValidationContext.Consumer { validationContext ->
                val polyglot = localizationContext?.polyglot ?: Polyglot()
                val bundleValidationRuleSet = validationContext?.bundleValidationRuleSet ?: emptySet()

                styledDiv {
                    css {
                        +AddExtensionStyle.mainDiv
                    }
                    styledSpan {
                        css {
                            +TextStyle.optionsDetailText
                            +IgSelectorStyle.title
                        }
                        +polyglot.t("options_bundle_validation_rules_description")
                        ul {
                            li {
                                +polyglot.t("options_bundle_validation_rules_example_1")
                            }
                            li {
                                +polyglot.t("options_bundle_validation_rules_example_2")
                            }
                            li {
                                +polyglot.t("options_bundle_validation_rules_example_3")
                            }
                        }
                    }

                    styledSpan {
                        css {
                            display = Display.grid
                            gridTemplateColumns = GridTemplateColumns(LinearDimension.maxContent, LinearDimension("0.4fr"), LinearDimension.minContent)
                            gap = LinearDimension("10px")
                            alignItems = Align.start
                        }

                        styledSpan {
                            css {
                                gridColumn = GridColumn("1")
                                +AddBundleValidationRuleStyle.ruleEntryDetailText
                            }
                            +polyglot.t("options_bundle_validation_rules_rule_label")
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
                        styledDiv {
                            css {
                                gridColumn = GridColumn("3")
                                + AddBundleValidationRuleStyle.ruleEntryTooltip
                            }
                            Tooltip {
                                attrs {
                                    title = ReactNode(
                                        polyglot.t("options_bundle_validation_rules_rule_description")
                                    )
                                }
                                InfoOutlined {}
                            }
                        }
                        styledSpan {

                            css {
                                gridColumn = GridColumn("1")
                                +AddBundleValidationRuleStyle.ruleEntryDetailText

                            }
                            + polyglot.t("options_bundle_validation_rules_profile_label")

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
                        styledDiv {
                            css {
                                gridColumn = GridColumn("3")
                               + AddBundleValidationRuleStyle.ruleEntryTooltip
                            }
                            Tooltip {
                                attrs {
                                    title = ReactNode(polyglot.t("options_bundle_validation_rules_profile_description"))
                                }
                                InfoOutlined {}
                            }
                        }
                        styledSpan {
                            css {
                                gridColumn = GridColumn("2 / span 2")
                                textAlign = TextAlign.end
                            }
                            imageButton {
                                backgroundColor = WHITE
                                borderColor = SWITCH_GRAY
                                image = "images/add_circle_black_24dp.svg"
                                label = polyglot.t("options_ig_add")
                                onSelected = {
                                    val rule = (document.getElementById(ruleInputId) as HTMLInputElement).value
                                    val profile = (document.getElementById(profileInputId) as HTMLInputElement).value

                                    val bundleValidationRule = BundleValidationRule().setRule(rule).setProfile(profile)

                                    validationContext?.updateBundleValidationRuleSet?.invoke(
                                        (bundleValidationRuleSet + bundleValidationRule).toMutableSet(),
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
                            +if (bundleValidationRuleSet.isEmpty()) TextStyle.optionsDetailText else TextStyle.optionName
                        }
                        val polyglotKey = if (bundleValidationRuleSet.isEmpty()) {
                            "options_bundle_validation_rules_not_added"
                        } else {
                            "options_bundle_validation_rules_added"
                        }
                        +polyglot.t(
                            polyglotKey,
                            getJS(arrayOf(Pair("addedProfiles", bundleValidationRuleSet.size.toString())))
                        )
                    }
                    styledDiv {
                        css {
                            +IgSelectorStyle.selectedIgsDiv
                            if (!bundleValidationRuleSet.isEmpty()) {
                                padding(top = 16.px)
                            }
                        }
                        bundleValidationRuleSet.forEach { _rule ->
                            bundleValidationRuleDisplay {
                                rule = _rule
                                onDelete = {
                                    validationContext?.updateBundleValidationRuleSet?.invoke(
                                        (bundleValidationRuleSet - _rule).toMutableSet(),
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
        fontFamily = TextStyle.FONT_FAMILY_MAIN
        fontSize = 11.pt
        fontWeight = FontWeight.w200
        display = Display.inlineBlock
        verticalAlign = VerticalAlign.middle
        resize = Resize.none
        paddingTop = 16.px
    }
    val ruleEntryTextArea by css {
        display = Display.inlineBlock
        verticalAlign = VerticalAlign.middle
        resize = Resize.none
        width = 100.pct
        height = 42.px
        marginRight = 16.px
        outline = Outline.none
        border(width = 1.px, color = BORDER_GRAY, style = BorderStyle.solid)
        backgroundColor = Color.transparent
        justifyContent = JustifyContent.center
        +TextStyle.optionsDetailText
    }
    val ruleEntryTooltip by css {
        paddingTop = 8.px
        paddingLeft = 8.px
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














