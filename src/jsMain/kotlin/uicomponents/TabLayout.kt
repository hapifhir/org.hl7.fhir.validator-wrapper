package uicomponents

import css.TabBarStyle
import css.TextStyle
import io.ktor.http.cio.websocket.Frame
import kotlinx.css.Display
import kotlinx.css.display
import kotlinx.html.js.onClickFunction
import org.w3c.dom.asList
import react.*
import styled.*
import styles
import uistate.TabState
import kotlin.browser.document

/**
 * We need to store the OperationOutcome externally, and pass it as an attribute to the OperationOutcomeList component.
 * React calls these attributes props. If props change in React, the framework will take care of re-rendering of the
 * page for us.
 */
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

            styledDiv {
                css {
                    +TabBarStyle.tabContainer
                }
                for (tabState in state.tabStates) {
                    styledButton {
                        +tabState.label
                        css {
                            +TextStyle.tab
                            +TabBarStyle.tabButton
                            if (tabState.active) {
                                +TabBarStyle.tabButtonActive
                            } else {
                                +TabBarStyle.tabButtonInactive
                            }
                            hover {
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
                manualEnterTab {
                    active = state.tabStates[0].active
                }
            }
            styledDiv {
                fileUploadTab {
                    active = state.tabStates[1].active
                }
            }
        }
    }
}

fun processTabSelect() {
    var tabs = document.getElementsByClassName("Tabs-body")
    println("There are ${tabs.asList().size} tabs")
    tabs.asList().forEach { _ ->
        styles.display = Display.none
    }

    var tablinks = document.getElementsByClassName("Tabs-tabButton")
    println("There are ${tablinks.asList().size} tablinks")
    tablinks.asList().forEach { _ ->

    }


}

/**
 * We can use lambdas with receivers to make the component easier to work with.
 * To include this component in a layout, someone can simply add:
 *
 *              bottomMenu {
 *
 *              }
 */
fun RBuilder.tabLayout(handler: TabLayoutProps.() -> Unit): ReactElement {
    return child(TabLayout::class) {
        this.attrs(handler)
    }
}