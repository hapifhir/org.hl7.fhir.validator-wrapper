package uicomponents

import css.TabBarStyle
import css.TextStyle
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import model.CliContext
import model.FileInfo
import react.*
import styled.*
import uistate.TabState

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
                for (tabState in state.tabStates) {
                    styledButton {
                        +tabState.label
                        css {
                            if (tabState.active) {
                                +TextStyle.tabActive
                                +TabBarStyle.tabButtonActive
                            } else {
                                +TextStyle.tabInactive
                                +TabBarStyle.tabButtonInactive
                            }
                            hover {
                                +TextStyle.tabHover
                                +TabBarStyle.tabButtonHover
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
            }
            styledDiv {
                css {
                    +TabBarStyle.tabBodyContainer
                }
                manualEnterTab {
                    active = state.tabStates[0].active
                    cliContext = props.cliContext
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