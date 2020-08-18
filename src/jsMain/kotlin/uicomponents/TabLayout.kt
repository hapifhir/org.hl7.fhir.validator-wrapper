package uicomponents

import api.sendValidationRequest
import constants.FhirFormat
import css.TabBarStyle
import css.TextStyle
import css.const.BLACK
import css.const.GRAY_600
import css.const.TRULY_RED
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.css.properties.borderBottom
import kotlinx.css.properties.borderLeft
import kotlinx.css.properties.borderTop
import kotlinx.html.js.onClickFunction
import model.CliContext
import model.FileInfo
import react.*
import styled.*
import uistate.TabState
import utils.assembleRequest

/**
 *
 */
external interface TabLayoutProps : RProps {
    var cliContext: CliContext
    var onValidate: (List<FileInfo>) -> Unit
}

class TabLayoutState : RState {
    var tabStates: ArrayList<TabState> = arrayListOf(
        TabState(label = "Enter Resource", active = true),
        TabState(label = "Upload Resources", active = false)
    )
}

class TabLayout : RComponent<TabLayoutProps, TabLayoutState>() {
    init {
        state = TabLayoutState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +TabBarStyle.mainLayout
            }
            styledDiv {
                css {
                    +TabBarStyle.tabBar
                }
                styledDiv{
                    css {
                        flexGrow = 1.0
                        height = 100.pct
                        boxSizing = BoxSizing.borderBox
                        borderBottom(width = 1.px, style = BorderStyle.solid, color = GRAY_600)
                    }
                }
                for (tabState in state.tabStates) {
                    styledDiv {
                        +tabState.label
                        css {
                            if (tabState.active) {
                                +TextStyle.tabActive
                                +TabBarStyle.tabButtonActive
                            } else {
                                +TextStyle.tabInactive
                                +TabBarStyle.tabButtonInactive
                                hover {
                                    +TextStyle.tabHover
                                    +TabBarStyle.tabButtonHover
                                }
                            }
                        }
                        attrs {
                            onClickFunction = {
                                println(tabState.label)
                                setState {
                                    state.tabStates.forEach {
                                        it.active = (it.label == tabState.label)
                                    }
                                }
                            }
                        }
                    }
                }
                styledDiv{
                    css {
                        flexGrow = 1.0
                        height = 100.pct
                        boxSizing = BoxSizing.borderBox
                        borderBottom(width = 1.px, style = BorderStyle.solid, color = GRAY_600)
                    }
                }
            }
            styledDiv {
                css {
                    +TabBarStyle.tabBodyContainer
                }
                manualEnterTab {
                    active = state.tabStates[0].active
                    onValidate = {
                        props.onValidate(it)
                    }
                }
                fileUploadTab {
                    active = state.tabStates[1].active
                    onValidate = {
                        props.onValidate(it)
                    }
                }
            }
        }
    }
}

/**
 *
 */
fun RBuilder.tabLayout(handler: TabLayoutProps.() -> Unit): ReactElement {
    return child(TabLayout::class) {
        this.attrs(handler)
    }
}