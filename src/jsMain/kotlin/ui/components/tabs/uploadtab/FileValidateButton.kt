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

external interface FileValidateButtonProps : Props {
    var onValidateRequested: () -> Unit
    var workInProgress: Boolean
}

/**
 * Component displaying the horizontal list of buttons for file upload and validation
 */
class FileValidateButton : RComponent<FileValidateButtonProps, State>() {

    override fun RBuilder.render() {
        context.LocalizationContext.Consumer { localizationContext ->
            val polyglot = localizationContext?.polyglot ?: Polyglot()
            if (props.workInProgress) {
                styledDiv {
                    css {
                        +FileValidateButtonStyle.spinner
                    }
                }
            } else {
                imageButton {
                    backgroundColor = WHITE
                    borderColor = HL7_RED
                    image = "images/validate_red.png"
                    label = polyglot.t("validate_button")
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