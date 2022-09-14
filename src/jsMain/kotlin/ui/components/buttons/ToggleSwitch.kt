package ui.components.buttons

import css.const.HL7_RED
import css.const.SWITCH_GRAY
import css.const.SWITCH_SHADOW
import css.const.WHITE
import kotlinx.css.*
import kotlinx.css.properties.*
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.attrs
import styled.*
import ui.components.options.menu.CHECKBOX_CHANGE

external interface ToggleSwitchProps : Props {
    var selected: Boolean
    var onChange: (Boolean) -> Unit
}

/**
 * A text only button with the option to customize, color, label, and if it is currently active
 */
class ToggleSwitch : RComponent<ToggleSwitchProps, State>() {

    override fun RBuilder.render() {
        styledLabel {
            css {
                +ToggleSwitchStyle.switch
            }
            styledInput {
                css {
                    +ToggleSwitchStyle.input
                }
                attrs {
                    type = InputType.checkBox
                    defaultChecked = props.selected
                    onChangeFunction = { event ->
                        if (event.type == CHECKBOX_CHANGE) {
                            props.onChange((event.target as HTMLInputElement).checked)
                        }
                    }
                }
            }
            styledSpan {
                css {
                    +ToggleSwitchStyle.slider
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.toggleSwitch(handler: ToggleSwitchProps.() -> Unit) {
    return child(ToggleSwitch::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object ToggleSwitchStyle : StyleSheet("ToggleSwitchStyle", isStatic = true) {
    val INNER_SWITCH_DIAMETER = 20.px
    val SWITCH_PADDING = 3.px

    val switch by ToggleSwitchStyle.css {
        position = Position.relative
        display = Display.inlineBlock
        width = (INNER_SWITCH_DIAMETER * 2) + (SWITCH_PADDING * 2)
        height = INNER_SWITCH_DIAMETER + (SWITCH_PADDING * 2)
    }
    val input by ToggleSwitchStyle.css {
        opacity = 0
        width = 0.px
        height = 0.px
        checked {
            transform {
                translateX(INNER_SWITCH_DIAMETER)
            }
            sibling(".${ToggleSwitchStyle.name}-${ToggleSwitchStyle::slider.name}:before") {
                transform {
                    translateX(INNER_SWITCH_DIAMETER)
                }
            }
            sibling(".${ToggleSwitchStyle.name}-${ToggleSwitchStyle::slider.name}") {
                backgroundColor = HL7_RED
            }
        }
        focus {
            sibling(".${ToggleSwitchStyle.name}-${ToggleSwitchStyle::slider.name}") {
                boxShadow(color = SWITCH_SHADOW, offsetX = 0.px, offsetY = 0.px, blurRadius = 2.px)
            }
        }
    }
    val slider by ToggleSwitchStyle.css {
        position = Position.absolute
        cursor = Cursor.pointer
        top = 0.px
        left = 0.px
        right = 0.px
        bottom = 0.px
        borderRadius = INNER_SWITCH_DIAMETER + (SWITCH_PADDING * 2)
        backgroundColor = SWITCH_GRAY
        transition(duration = 0.4.s)
        before {
            position = Position.absolute
            content = QuotedString("")
            cursor = Cursor.pointer
            width = INNER_SWITCH_DIAMETER
            height = INNER_SWITCH_DIAMETER
            left = SWITCH_PADDING
            bottom = SWITCH_PADDING
            backgroundColor = WHITE
            borderRadius = 50.pct
            transition(duration = 0.4.s)
        }
    }
}