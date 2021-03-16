package ui.components.main

import css.component.page.TabBarStyle
import css.const.GRAY_600
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.borderBottom
import kotlinx.css.properties.borderLeft
import kotlinx.html.js.onClickFunction
import react.*
import styled.css
import styled.styledDiv
import ui.components.fileUploadTab
import ui.components.manualEnterTab
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