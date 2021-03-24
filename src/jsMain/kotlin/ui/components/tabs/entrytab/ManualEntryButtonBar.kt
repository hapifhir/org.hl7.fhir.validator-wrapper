package ui.components.tabs.entrytab

import css.animation.LoadingSpinner
import css.animation.LoadingSpinner.scaleIntro
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
    var workInProgress: Boolean
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
            if (props.workInProgress) {
                styledDiv {
                    css {
                        +ManualEntryButtonBarStyle.spinner
                    }
                }
            } else {
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
    val spinner by css {
        height = 32.px
        width = 32.px
        margin(horizontal = 32.px, vertical = 8.px)
        alignSelf = Align.center
        +LoadingSpinner.loadingIndicator
    }
}