package ui.components.options

import Polyglot
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
import popper.core.Placement
import react.Props
import react.ReactNode

import styled.css
import styled.styledDiv

import utils.Preset
import utils.getJS


external interface PresetSelectProps : Props {
    var cliContext: CliContext
    var updateCliContext: (CliContext) -> Unit
    var updateIgPackageInfoSet: (Set<PackageInfo>) -> Unit
    var updateExtensionSet: (Set<String>) -> Unit
    var updateProfileSet: (Set<String>) -> Unit
    var updateBundleValidationRuleSet: (Set<BundleValidationRule>) -> Unit
    var setSessionId: (String) -> Unit
    var polyglot: Polyglot
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
                                label = ReactNode("preset")
                                onChange = { event, _ ->
                                    val selectedPreset = Preset.getSelectedPreset(event.target.value)
                                    if (selectedPreset != null) {
                                        console.log("updating cli context for preset: " + event.target.value)
                                        props.updateCliContext(selectedPreset.cliContext)
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

                            Preset.values().forEach {
                                MenuItem {
                                    attrs {
                                        value = it.key
                                    }
                                    +props.polyglot.t(it.polyglotKey)
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
                            getJS(arrayOf(Pair("selectedPreset", props.polyglot.t(state.snackbarOpen.toString())))))
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

