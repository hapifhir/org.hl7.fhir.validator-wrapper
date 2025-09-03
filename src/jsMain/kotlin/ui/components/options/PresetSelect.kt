package ui.components.options

import Polyglot
import mui.material.*
import react.*
import csstype.px
import kotlinx.coroutines.launch
import kotlinx.css.*
import mainScope
import model.*
import mui.system.sx
import react.Props
import react.ReactNode

import styled.css
import styled.styledDiv

import utils.Language
import utils.getJS


external interface PresetSelectProps : Props {
    var validationContext: ValidationContext
    var validationEngineSettings: ValidationEngineSettings
    var updateValidationContext: (ValidationContext) -> Unit
    var updateValidationEngineSettings: (ValidationEngineSettings) -> Unit
    var updateIgPackageInfoSet: (Set<PackageInfo>) -> Unit
    var updateExtensionSet: (Set<String>) -> Unit
    var updateProfileSet: (Set<String>) -> Unit
    var updateBundleValidationRuleSet: (Set<BundleValidationRule>) -> Unit
    var setSessionId: (String) -> Unit
    var language: Language
    var polyglot: Polyglot
    var presets: List<Preset>
}

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
        if (props.presets.isEmpty()) {
            return
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
                        props.polyglot.t("preset_description")
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
                            +props.polyglot.t("preset_label")
                        }
                        Select {

                            attrs {
                                label = ReactNode("Preset")
                                value =  props.validationEngineSettings.getBaseEngine().unsafeCast<Nothing?>()
                                onChange = { event, _ ->
                                    val selectedPreset = Preset.getSelectedPreset(event.target.value, props.presets)
                                    if (selectedPreset != null) {

                                        val validationContext = ValidationContext(selectedPreset.validationContext).setLocale(props.language.getLanguageCode())
                                        val validationEngineSettings = ValidationEngineSettings(selectedPreset.validationEngineSettings)
                                            //FIXME set locale
                                            //.setLocale(props.language.getLanguageCode())
                                        props.updateValidationContext(validationContext)
                                        props.updateValidationEngineSettings(validationEngineSettings)
                                        props.updateIgPackageInfoSet(selectedPreset.igPackageInfo)
                                        props.updateExtensionSet(selectedPreset.extensionSet)
                                        props.updateProfileSet(selectedPreset.profileSet)
                                        props.updateBundleValidationRuleSet(
                                            selectedPreset.validationContext.getBundleValidationRules().toMutableSet()
                                        )
                                        mainScope.launch {
                                            setState {
                                                snackbarOpen = selectedPreset.localizedLabels.get(props.language.code)
                                            }
                                        }
                                        props.setSessionId("")
                                    }
                                }
                            }

                            props.presets.forEach {

                                    MenuItem {
                                        attrs {
                                            value = it.key
                                        }
                                        console.log(
                                            it.localizedLabels.toString() + " " +props.language.code
                                        )
                                        +(if (it.localizedLabels.containsKey(props.language.code)) it.localizedLabels.get(props.language.code) else "No localized entry for " + it.key )!!
                                        console.log(
                                            it.key + " " + props.validationContext.getBaseEngine() + ":" + it.key.equals(
                                                props.validationContext.getBaseEngine()
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
                        props.polyglot.t("preset_notification",
                            getJS(arrayOf(Pair("selectedPreset",
                                if (state.snackbarOpen != null)
                                    props.polyglot.t(state.snackbarOpen.toString())
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

fun RBuilder.presetSelect(handler: PresetSelectProps.() -> Unit) {
    return child(PresetSelect::class) {
        this.attrs(handler)
    }
}

