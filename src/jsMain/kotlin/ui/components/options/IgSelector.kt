package ui.components.options

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

external interface IgSelectorProps : RProps {
    var fhirVersion: String
    var onUpdateIg: (PackageInfo, Boolean) -> Unit
    var igList: MutableList<Pair<PackageInfo, Boolean>>
}

class IgSelectorState : RState

class IgSelector : RComponent<IgSelectorProps, IgSelectorState>() {

    override fun RBuilder.render() {
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
                    choices = props.igList
                        .filter { it.first.fhirVersionMatches(props.fhirVersion) }
                        .map { Pair(it.first.url ?: "", it.second) }
                        .toMutableList()
                    buttonLabel = "Select IG"
                    onSelected = { url ->
                        props.onUpdateIg(props.igList.first { it.first.url == url }.first, true)
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
                            println("Button go click")
                        }
                    }
                }
            }
            styledDiv {
                css {
                    +IgSelectorStyle.selectedIgsDiv
                    if (props.igList.any { it.second }) {
                        padding(top = 8.px)
                    }
                }
                props.igList.filter { it.second }.forEach { igState ->
                    igUrlDisplay {
                        fhirVersion = props.fhirVersion
                        packageInfo = igState.first

                        onDelete = {
                            props.onUpdateIg(igState.first, false)
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