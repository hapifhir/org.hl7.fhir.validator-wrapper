package ui.components.tabs.entrytab

import css.const.HL7_RED
import css.const.WHITE
import kotlinx.css.*
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.buttons.imageButton

external interface ManualEntryButtonBarProps : RProps {
    var onValidateRequested: () -> Unit
}

/**
 * Component displaying the horizontal list of buttons for file upload and validation
 */
class ManualEntryButtonBar : RComponent<ManualEntryButtonBarProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                +ManualEntryButtonBarStyle.buttonBarContainer
            }
            imageButton {
                backgroundColor = WHITE
                borderColor = HL7_RED
                image = "images/validate_red.png"
                label = "Validate"
                onSelected = {
                    props.onValidateRequested()
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.fileEntryButtonBar(handler: ManualEntryButtonBarProps.() -> Unit): ReactElement {
    return child(ManualEntryButtonBar::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object ManualEntryButtonBarStyle : StyleSheet("ManualEntryButtonBarStyle", isStatic = true) {
    val buttonBarContainer by css {
        display = Display.inlineFlex
        flexDirection = FlexDirection.row
        alignItems = Align.center
        padding(vertical = 16.px)
    }
}