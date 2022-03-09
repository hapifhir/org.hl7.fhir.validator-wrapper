package ui.components.options

import api.sendIGsRequest
import api.sendVersionsRequest
import css.const.HL7_RED
import css.const.WHITE
import css.text.TextStyle
import kotlinx.browser.document
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import model.PackageInfo
import org.w3c.dom.HTMLInputElement
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan
import ui.components.buttons.imageButton
import ui.components.options.menu.dropDownMultiChoice

import mainScope

external interface IgSelectorProps : RProps {
    var fhirVersion: String
    var onUpdateIg: (PackageInfo, Boolean) -> Unit
    var igList: MutableList<PackageInfo>
}

class IgSelectorState : RState {
    var packageNames = mutableListOf<Pair<String, Boolean>>()
    var packageVersions = mutableListOf<Pair<String, Boolean>>()
}

class IgSelector (props: IgSelectorProps ): RComponent<IgSelectorProps, IgSelectorState>() {

    /*
    companion object : RStatics<IgSelectorProps, IgSelectorState, IgSelector, Nothing>(IgSelector::class) {
        init {
            getDerivedStateFromProps = { props, state ->
                state
            }
        }
    }
*/
   init {
       state = IgSelectorState()
   }

    override fun componentWillReceiveProps (nextProps: IgSelectorProps) {
        println("componentWillReceiveProps ${nextProps.igList.size}")
            setState {
                packageNames = nextProps.igList.map {  Pair(it.id!!, false) }.toMutableList()
                //packageNames = mutableListOf<Pair<String, Boolean>>(Pair("nah", false))
                packageVersions = mutableListOf<Pair<String, Boolean>>()
            }
    }

    override fun RBuilder.render() {
        println("render ${props.igList.size}")
        styledDiv {
            css {
                +IgSelectorStyle.mainDiv
            }
            styledSpan {
                css {
                    +TextStyle.optionsDetailText
                    +IgSelectorStyle.title
                }
                +"You can validate against one or more published implementation guides. Select IGs using the dropdown menus below and click the "
                styledSpan {
                    css {
                        fontStyle = FontStyle.italic
                    }
                    + "Add"
                }
                +" button to include them in your validation."
            }
            styledSpan {
                dropDownMultiChoice {
                    choices = state.packageNames
                    buttonLabel = "Select IGs"
                    onSelected = { igLookupString ->

                    }
                    multichoice = false
                    searchEnabled = true
                    searchHint = "Search IGs..."
                }

                styledSpan {
                    css {
                        margin(left = 8.px)
                    }
                    dropDownMultiChoice {
                        choices = mutableListOf(Pair("1", false), Pair("4.1.0", true), Pair("3", false))
                        buttonLabel = "Select version"
                        onSelected = { url ->

                        }
                        multichoice = false
                        searchEnabled = false
                        searchHint = "Select version"
                    }
                }
                styledSpan {
                    css {
                        margin(left = 8.px)
                    }
                    imageButton {
                        backgroundColor = WHITE
                        borderColor = HL7_RED
                        image = "images/add_circle_black_24dp.svg"
                        label = "Add"
                        onSelected = {
                            props.onUpdateIg(PackageInfo("dummy.ig", "1.2.3", "4.0.1", "https://dummy.url"), true)
                        }
                    }
                }
            }
            styledDiv {
                css {
                    +IgSelectorStyle.selectedIgsDiv
                    if (state.packageNames.any { it.second }) {
                        padding(top = 8.px)
                    }
                }
                state.packageNames.filter { it.second }.forEach { igState ->
                    igDisplay {
                        fhirVersion = props.fhirVersion
                        packageInfo = PackageInfo(igState.first, null, null, null)

                        onDelete = {
                            props.onUpdateIg(PackageInfo(igState.first, null, null, null), false)
                        }
                    }
                }
            }
        }
    }

}


/**
 * React Component Builder
 */
fun RBuilder.igSelector(handler: IgSelectorProps.() -> Unit): ReactElement {
    return child(IgSelector::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object IgSelectorStyle : StyleSheet("IgSelectorStyle", isStatic = true) {
    val mainDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        padding(horizontal = 8.px)
    }
    val title by css {
        paddingBottom = 16.px
    }
    val selectedIgsDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        flexWrap = FlexWrap.wrap
    }
}
