package ui.components.tabs.entrytab

import css.animation.LoadingSpinner
import css.const.HL7_RED
import css.const.WHITE
import kotlinx.css.*
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.buttons.imageButton

external interface ManualEntryButtonBarProps : Props {
    var onValidateRequested: () -> Unit
    var workInProgress: Boolean
    var validateText: String
}

/**
 * Component displaying the horizontal list of buttons for file upload and validation
 */
class ManualEntryButtonBar : RComponent<ManualEntryButtonBarProps, State>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                +ManualValidateButtonStyle.buttonBarContainer
            }
            if (props.workInProgress) {
                styledDiv {
                    css {
                        +ManualValidateButtonStyle.spinner
                    }
                }
            } else {
                imageButton {
                    backgroundColor = WHITE
                    borderColor = HL7_RED
                    image = "images/validate_red.png"
                    label = props.validateText
                    onSelected = {
                        props.onValidateRequested()
                    }
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.manualEntryValidateButton(handler: ManualEntryButtonBarProps.() -> Unit) {
    return child(ManualEntryButtonBar::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object ManualValidateButtonStyle : StyleSheet("ManualEntryButtonBarStyle", isStatic = true) {
    val buttonBarContainer by css {
        display = Display.inlineFlex
        flexDirection = FlexDirection.row
        alignItems = Align.center
        padding(vertical = 16.px)
    }
    val spinner by css {
        height = 32.px
        width = 32.px
        margin(horizontal = 32.px, vertical = 8.px)
        alignSelf = Align.center
        +LoadingSpinner.loadingIndicator
    }
}