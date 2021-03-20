package ui.components.tabs

import css.component.tabs.TabBarStyle
import css.text.TextStyle
import kotlinx.html.js.onClickFunction
import react.*
import reactredux.containers.fileUploadTab
import styled.css
import styled.styledDiv
import ui.components.tabs.entrytab.manualEnterTab
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
                        +TabBarStyle.leftRightTabFill
                    }
                }
                val tabsIterator = state.tabStates.iterator()
                while (tabsIterator.hasNext()) {
                    val tabState = tabsIterator.next()
                    styledDiv {
                        +tabState.label
                        css {
                            if (tabState.active) {
                                +TextStyle.tabLabelActive
                                +TabBarStyle.tabButtonActive
                            } else {
                                +TextStyle.tabLabelInactive
                                +TabBarStyle.tabButtonInactive
                                hover {
                                    +TextStyle.tabLabelHover
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
                    if (tabsIterator.hasNext()) {
                        styledDiv {
                            css {
                                +TabBarStyle.dividerTabFill
                            }
                        }
                    }
                }
                styledDiv {
                    css {
                        +TabBarStyle.leftRightTabFill
                    }
                }
            }
            styledDiv {
                css {
                    +TabBarStyle.tabBodyContainer
                }
                if (state.tabStates[0].active) {
                    manualEnterTab {}
                } else {
                    fileUploadTab {}
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