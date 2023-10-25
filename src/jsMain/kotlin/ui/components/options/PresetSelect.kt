package ui.components.options

import api.sendIGsRequest
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
    var updateSelectedIgPackageInfo: (Set<PackageInfo>) -> Unit
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
                                val selectedCliContext = Preset.getSelectedCliContext(event.target.value)
                                if (selectedCliContext != null) {
                                    console.log("updating cli context for preset: " + event.target.value)
                                    props.updateCliContext(selectedCliContext)
                                }
                                val selectedIgPackageInfo = Preset.getSelectedIgPackageInfo(event.target.value)
                                if (selectedIgPackageInfo != null) {
                                    console.log("updating selected igs for preset: " + event.target.value)
                                    props.updateSelectedIgPackageInfo(selectedIgPackageInfo)
                                }
                                mainScope.launch {
                                    setState {
                                        snackbarOpen = Preset.getSelectedLabel(event.target.value)
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

