package ui.components.tabs

import css.const.*
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.*
import kotlinx.html.js.onClickFunction
import react.*
import reactredux.containers.fileUploadTab
import reactredux.containers.manualEntryTab
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.entity.TabState

external interface TabLayoutProps : RProps

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
                    manualEntryTab {}
                } else {
                    fileUploadTab {}
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.tabLayout(handler: TabLayoutProps.() -> Unit): ReactElement {
    return child(TabLayout::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object TabBarStyle : StyleSheet("TabBarStyle", isStatic = true) {
    val mainLayout by css {
        height = 600.px
        display = Display.inlineFlex
        flexDirection = FlexDirection.column
    }
    val tabBar by css {
        display = Display.flex
        justifyContent = JustifyContent.center
        flex(flexBasis = 64.px)
    }
    val tabButton by css {
        float = kotlinx.css.Float.left
        cursor = Cursor.pointer
        display = Display.inlineFlex
        alignItems = Align.center
        justifyContent = JustifyContent.center
        height = 64.px
        width = 256.px
        boxSizing = BoxSizing.borderBox
    }
    val tabButtonInactive by css {
        backgroundColor = INACTIVE_GRAY
        border(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        +tabButton
    }
    val tabButtonActive by css {
        backgroundColor = WHITE
        borderLeft(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        borderRight(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        borderTop(width = 4.px, style = BorderStyle.solid, color = HL7_RED)
        +tabButton
    }
    val tabButtonHover by css {
        backgroundColor = HIGHLIGHT_GRAY
        +tabButton
    }
    val tabBodyContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.column
    }
    val tabFill by css {
        height = 100.pct
        boxSizing = BoxSizing.borderBox
        borderBottom(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
    }
    val leftRightTabFill by css {
        flexGrow = 1.0
        +tabFill
    }
    val dividerTabFill by css {
        width = 16.px
        +tabFill
    }
}