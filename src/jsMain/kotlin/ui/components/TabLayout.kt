package ui.components

import css.component.TabBarStyle
import css.text.TextStyle
import css.const.GRAY_600
import kotlinx.css.*
import kotlinx.css.properties.borderBottom
import kotlinx.html.js.onClickFunction
import react.*
import styled.css
import styled.styledDiv
import ui.entity.TabState

external interface TabLayoutProps : RProps {

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
                styledDiv {
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
                styledDiv {
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
                }
                fileUploadTab {
                    active = state.tabStates[1].active
                }
            }
        }
    }
}

fun RBuilder.tabLayout(handler: TabLayoutProps.() -> Unit): ReactElement {
    return child(TabLayout::class) {
        this.attrs(handler)
    }
}