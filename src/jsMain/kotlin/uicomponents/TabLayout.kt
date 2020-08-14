package uicomponents

import css.TabBarStyle
import css.TextStyle
import io.ktor.http.cio.websocket.Frame
import kotlinx.css.Display
import kotlinx.css.display
import kotlinx.html.js.onClickFunction
import model.CliContext
import org.w3c.dom.asList
import react.*
import styled.*
import styles
import uistate.TabState
import kotlin.browser.document

/**
 *
 */
external interface TabLayoutProps : RProps {
    var cliContext: CliContext
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
                styledDiv {  }
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
                manualEnterTab {
                    active = state.tabStates[0].active
                    cliContext = props.cliContext
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

/**
 *
 */
fun RBuilder.tabLayout(handler: TabLayoutProps.() -> Unit): ReactElement {
    return child(TabLayout::class) {
        this.attrs(handler)
    }
}