package ui.components.options

import Polyglot
import context.LocalizationContext
import context.ValidationContext
import mui.material.*
import react.*
import csstype.*
import web.cssom.px
import kotlinx.coroutines.launch
import kotlinx.css.*
import mainScope
import model.BundleValidationRule
import model.PackageInfo
import mui.system.sx
import react.Props
import react.ReactNode

import styled.css
import styled.styledDiv

import model.Preset
import utils.Language
import utils.getJS


external interface PresetSelectProps : Props {}

class PresetSelectState : State {
    var snackbarOpen : String? = null
}

class PresetSelect : RComponent<PresetSelectProps, PresetSelectState>() {

    init {
        state = PresetSelectState()
    }

    fun handleSnackBarClose() {
        mainScope.launch {
            setState {
                snackbarOpen = null
            }
        }
    }
    override fun RBuilder.render() {
        LocalizationContext.Consumer { localizationContext ->
            ValidationContext.Consumer { ctx ->
                val polyglot = localizationContext?.polyglot ?: Polyglot()
                val language = localizationContext?.selectedLanguage ?: Language.ENGLISH
                val validationContext = ctx?.validationContext
                    ?: model.ValidationContext().setBaseEngine("DEFAULT")
                val presets = ctx?.presets ?: emptyList()

                if (presets.isEmpty()) {
                    return@Consumer
                }

                styledDiv {
                    css {
                        display = Display.inlineFlex
                        flexDirection = FlexDirection.column
                        alignSelf = Align.center
                    }
                    Tooltip {
                        attrs {
                            title = ReactNode(
                                polyglot.t("preset_description")
                            )
                            placement = TooltipPlacement.left
                        }
                        Box {
                            attrs {
                                sx {
                                    minWidth = 270.px
                                }
                            }
                            FormControl {
                                attrs {
                                    fullWidth = true
                                    size = Size.medium
                                }
                                InputLabel {
                                    +polyglot.t("preset_label")
                                }
                                Select {

                                    attrs {
                                        label = ReactNode("Preset")
                                        value =  validationContext.getBaseEngine().unsafeCast<Nothing?>()
                                        onChange = { event, _ ->
                                            val selectedPreset = Preset.getSelectedPreset(event.target.value, presets)
                                            if (selectedPreset != null) {

                                                val newValidationContext = model.ValidationContext(selectedPreset.validationContext).setLocale(language.getLanguageCode())
                                                ctx?.updateValidationContext?.invoke(newValidationContext, false)
                                                ctx?.updateIgPackageInfoSet?.invoke(selectedPreset.igPackageInfo, false)
                                                ctx?.updateExtensionSet?.invoke(selectedPreset.extensionSet, false)
                                                ctx?.updateProfileSet?.invoke(selectedPreset.profileSet, false)
                                                ctx?.updateBundleValidationRuleSet?.invoke(
                                                    selectedPreset.validationContext.getBundleValidationRules().toMutableSet(),
                                                    false
                                                )
                                                mainScope.launch {
                                                    setState {
                                                        snackbarOpen = selectedPreset.localizedLabels.get(language.code)
                                                    }
                                                }
                                                ctx?.setSessionId?.invoke("")
                                            }
                                        }
                                    }

                                    presets.forEach {

                                            MenuItem {
                                                attrs {
                                                    value = it.key
                                                }
                                                console.log(
                                                    it.localizedLabels.toString() + " " + language.code
                                                )
                                                +(if (it.localizedLabels.containsKey(language.code)) it.localizedLabels.get(language.code) else "No localized entry for " + it.key )!!
                                                console.log(
                                                    it.key + " " + validationContext.getBaseEngine() + ":" + it.key.equals(
                                                        validationContext.getBaseEngine()
                                                    )
                                                )

                                        }
                                    }
                                }
                            }
                        }
                    }
                    Snackbar {
                        attrs {
                            open = state.snackbarOpen != null
                            message = ReactNode(
                                polyglot.t("preset_notification",
                                    getJS(arrayOf(Pair("selectedPreset",
                                        if (state.snackbarOpen != null)
                                            polyglot.t(state.snackbarOpen.toString())
                                        else
                                            "---"
                                    ))))
                            )
                            autoHideDuration=6000
                            onClose = { event, _ -> handleSnackBarClose() }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.presetSelect(handler: PresetSelectProps.() -> Unit) {
    return child(PresetSelect::class) {
        this.attrs(handler)
    }
}

