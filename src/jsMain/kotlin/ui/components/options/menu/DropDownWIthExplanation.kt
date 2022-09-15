package ui.components.options.menu

import css.text.TextStyle
import kotlinx.css.*
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan

external interface DropdownWithExplanationProps : Props {
    var onItemSelected: (String) -> Unit
    var itemList: MutableList<Pair<String, Boolean>>
    var heading: String
    var explanation: String
    var defaultLabel: String
}

class DropdownWithExplanation : RComponent<DropdownWithExplanationProps, State>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +DropdownWithExplanationStyle.mainDiv
            }
            styledSpan {
                css {
                    +TextStyle.optionName
                }
                +props.heading
            }
            styledSpan {
                css {
                    +TextStyle.optionsDetailText
                    +DropdownWithExplanationStyle.detailsText
                }
                +props.explanation
            }
            dropDownMultiChoice {
                choices = props.itemList
                buttonLabel = props.defaultLabel
                onSelected = {
                    props.onItemSelected(it)
                }
                multichoice = false
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.dropdownWithExplanation(handler: DropdownWithExplanationProps.() -> Unit) {
    return child(DropdownWithExplanation::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object DropdownWithExplanationStyle : StyleSheet("DropdownWithExplanationStyle", isStatic = true) {
    val mainDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        padding(horizontal = 8.px)
    }
    val detailsText by css {
        padding(top = 8.px, bottom = 16.px)
    }
}