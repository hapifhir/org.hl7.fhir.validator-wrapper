package ui.components.tabs.uploadtab

import Polyglot
import css.animation.LoadingSpinner
import css.const.HL7_RED
import css.const.WHITE
import kotlinx.css.*
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.buttons.imageButton
import ui.components.tabs.entrytab.ManualEntryButtonBarStyle

external interface FileValidateButtonProps : Props {
    var onValidateRequested: () -> Unit
    var workInProgress: Boolean
    var polyglot: Polyglot
}

/**
 * Component displaying the horizontal list of buttons for file upload and validation
 */
class FileValidateButton : RComponent<FileValidateButtonProps, State>() {

    override fun RBuilder.render() {


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
                    label = props.polyglot.t("validate_button")
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
fun RBuilder.fileValidateButton(handler: FileValidateButtonProps.() -> Unit) {
    return child(FileValidateButton::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object FileValidateButtonStyle : StyleSheet("FileValidateButtonStyle", isStatic = true) {

    val spinner by css {
        height = 32.px
        width = 32.px
        margin(horizontal = 32.px, vertical = 8.px)
        alignSelf = Align.center
        +LoadingSpinner.loadingIndicator
    }
}