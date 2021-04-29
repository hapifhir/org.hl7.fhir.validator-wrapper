package ui.components.options

import css.text.TextStyle
import kotlinx.css.*
import model.PackageInfo
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan
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
                +"You can validate against one or more published implementation guides. Select from the dropdown menu below."
            }
            dropDownMultiChoice {
                choices = props.igList
                    .filter{ it.first.fhirVersionMatches(props.fhirVersion) }
                    .map{Pair(it.first.url ?: "", it.second)}
                    .toMutableList()
                buttonLabel = "Select IGs"
                onSelected = { url ->
                    props.onUpdateIg(props.igList.first { it.first.url == url }.first, true)
                }
                multichoice = true
                searchEnabled = true
                searchHint = "Search igs..."
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