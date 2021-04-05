package ui.components.options

import css.text.TextStyle
import kotlinx.css.*
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan
import ui.components.options.menu.dropDownMultiChoice

external interface IgSelectorProps : RProps {
    var onUpdateIg: (String, Boolean) -> Unit
    var igList: MutableList<Pair<String, Boolean>>
}

class IgSelectorState : RState {
}

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
                buttonLabel = "Select IGs"
                onSelected = {
                    props.onUpdateIg(it, true)
                }
                multichoice = true
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
                        igUrl = igState.first
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