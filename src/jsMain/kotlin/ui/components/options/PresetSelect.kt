package ui.components.options

import Polyglot
import api.getValidationEngines
import api.getValidationPresets
import mui.material.*
import react.*
import csstype.px
import kotlinx.coroutines.launch
import kotlinx.css.*
import model.CliContext
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


external interface PresetSelectProps : Props {
    var cliContext: CliContext
    var updateCliContext: (CliContext) -> Unit
    var updateIgPackageInfoSet: (Set<PackageInfo>) -> Unit
    var updateExtensionSet: (Set<String>) -> Unit
    var updateProfileSet: (Set<String>) -> Unit
    var updateBundleValidationRuleSet: (Set<BundleValidationRule>) -> Unit
    var setSessionId: (String) -> Unit
    var language: Language
    var polyglot: Polyglot
}

class PresetSelectState : State {
    var snackbarOpen : String? = null
    var validationEngines: Set<String> = emptySet()
    var validationPresets: List<Preset> = emptyList()
}

class PresetSelect : RComponent<PresetSelectProps, PresetSelectState>() {

    init {
        state = PresetSelectState()
        mainScope.launch {
            val loadedValidationEngines = getValidationEngines()
            val loadedValidationPresets = getValidationPresets()

            setState {
                validationEngines = loadedValidationEngines
                validationPresets = loadedValidationPresets
            }

        }
    }

    fun handleSnackBarClose() {
        mainScope.launch {
            setState {
                snackbarOpen = null
            }
        }
    }
    override fun RBuilder.render() {
        if (state.validationPresets.isEmpty()) {
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
                                value =  props.cliContext.getBaseEngine().unsafeCast<Nothing?>()
                                onChange = { event, _ ->
                                    val selectedPreset = Preset.getSelectedPreset(event.target.value, state.validationPresets)
                                    if (selectedPreset != null) {

                                        val cliContext = CliContext(selectedPreset.cliContext).setLocale(props.language.getLanguageCode())
                                        props.updateCliContext(cliContext)
                                        props.updateIgPackageInfoSet(selectedPreset.igPackageInfo)
                                        props.updateExtensionSet(selectedPreset.extensionSet)
                                        props.updateProfileSet(selectedPreset.profileSet)
                                        props.updateBundleValidationRuleSet(
                                            selectedPreset.cliContext.getBundleValidationRules().toMutableSet()
                                        )
                                        mainScope.launch {
                                            setState {
                                                snackbarOpen = selectedPreset.polyglotKey
                                            }
                                        }
                                        props.setSessionId("")
                                    }
                                }
                            }

                            state.validationPresets.forEach {
                                if (state.validationEngines.contains(it.key)) {
                                    MenuItem {
                                        attrs {
                                            value = it.key
                                        }
                                        +props.polyglot.t(it.polyglotKey)
                                        console.log(
                                            it.key + " " + props.cliContext.getBaseEngine() + ":" + it.key.equals(
                                                props.cliContext.getBaseEngine()
                                            )
                                        )
                                    }
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

