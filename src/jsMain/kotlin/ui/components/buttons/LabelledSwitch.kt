package ui.components.buttons

import css.const.HL7_RED
import css.const.SWITCH_GRAY
import css.const.SWITCH_SHADOW
import css.const.WHITE
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.*
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import react.*
import styled.*
import ui.components.options.menu.CHECKBOX_CHANGE

external interface LabelledSwitchProps : RProps {
    var label: String
    var active: Boolean

    // Callback function when clicked
    var onSelected: (Boolean) -> Unit
}

/**
 * A text only button with the option to customize, color, label, and if it is currently active
 */
class LabelledSwitch : RComponent<LabelledSwitchProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.row
                alignItems = Align.center
                padding(vertical = 8.px, horizontal = 16.px)
            }
            toggleSwitch {
                selected = props.active
                onChange = {
                    props.onSelected(it)
                }
            }
            styledSpan {
                css {
                    +TextStyle.optionButtonLabel
                    paddingLeft = 8.px
                }
                +props.label
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.labelledSwitch(handler: LabelledSwitchProps.() -> Unit): ReactElement {
    return child(LabelledSwitch::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object LabelledSwitchStyle : StyleSheet("LabelledSwitchStyle", isStatic = true) {
    val mainDiv by LabelledSwitchStyle.css {
        position = Position.relative
        display = Display.inlineBlock
    }
}