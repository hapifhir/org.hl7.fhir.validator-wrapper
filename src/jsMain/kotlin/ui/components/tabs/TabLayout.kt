package ui.components.tabs

import Polyglot
import context.LocalizationContext
import css.const.*
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import kotlinx.css.properties.*
import react.*
import react.dom.attrs
import model.ValidationContext
import ui.components.tabs.entrytab.ManualEntryTab
import ui.components.tabs.uploadtab.FileUploadTab
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.entity.TabState

external interface TabLayoutProps : Props {}

class TabLayoutState : State {
    var tabStates: ArrayList<TabState> = arrayListOf(
        TabState(label = "", active = true),
        TabState(label = "", active = false)
    )
}

class TabLayout : RComponent<TabLayoutProps, TabLayoutState>() {
    init {
        state = TabLayoutState()
    }


    override fun RBuilder.render() {
        LocalizationContext.Consumer { localizationContext ->
            val polyglot = localizationContext?.polyglot ?: Polyglot()

            state.tabStates[0].label = polyglot.t("enter_resources_heading")
            state.tabStates[1].label = polyglot.t("upload_resources_heading")
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
                        child(ManualEntryTab::class) {}
                    } else {
                        child(FileUploadTab::class) {}
                    }
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.tabLayout(handler: TabLayoutProps.() -> Unit) {
    return child(TabLayout::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object TabBarStyle : StyleSheet("TabBarStyle", isStatic = true) {
    val mainLayout by css {
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
        height = 64.px
        alignSelf = Align.selfEnd
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
