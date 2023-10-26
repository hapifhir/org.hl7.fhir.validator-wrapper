package ui.components.options

import mui.material.*
import react.*
import csstype.px
import kotlinx.coroutines.launch
import kotlinx.css.*
import model.CliContext
import mainScope
import model.PackageInfo
import mui.system.sx
import react.Props
import react.ReactNode

import styled.css
import styled.styledDiv

import utils.Preset


external interface PresetSelectProps : Props {
    var cliContext: CliContext
    var updateCliContext: (CliContext) -> Unit
    var updateIgPackageInfoSet: (Set<PackageInfo>) -> Unit
    var updateExtensionSet: (Set<String>) -> Unit
    var updateProfileSet: (Set<String>) -> Unit
    var setSessionId: (String) -> Unit
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
            Box {
                attrs {
                    sx {
                        minWidth = 120.px
                    }
                }
                FormControl {
                    attrs {
                        fullWidth = true
                        size = Size.small
                    }
                    InputLabel {
                        +"Preset"
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
                                    mainScope.launch {
                                        setState {
                                            snackbarOpen = selectedPreset.label
                                        }
                                    }
                                }
                            }
                        }

                        Preset.values().forEach {
                            MenuItem {
                                attrs {
                                    value = it.key
                                }
                                +it.label
                            }
                        }
                    }
                }
            }
            Snackbar {
                attrs {
                    open = state.snackbarOpen != null
                    message = ReactNode( "Set to validate using " + state.snackbarOpen + ". Select the Options tab for more settings.")
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

