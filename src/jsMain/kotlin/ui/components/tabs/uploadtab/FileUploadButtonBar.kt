package ui.components.tabs.uploadtab

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

external interface FileUploadButtonBarProps : Props {
    var onUploadRequested: () -> Unit
    var onValidateRequested: () -> Unit
    var workInProgress: Boolean
    var uploadText: String
    var validateText: String
}

/**
 * Component displaying the horizontal list of buttons for file upload and validation
 */
class FileUploadButtonBar : RComponent<FileUploadButtonBarProps, State>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileUploadButtonBarStyle.buttonBarContainer
            }

            imageButton {
                backgroundColor = WHITE
                borderColor = HL7_RED
                image = "images/upload_red.png"
                label = props.uploadText
                onSelected = {
                    props.onUploadRequested()
                }
            }

            styledDiv {
                css {
                    +FileUploadButtonBarStyle.buttonBarDivider
                }
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
fun RBuilder.fileUploadButtonBar(handler: FileUploadButtonBarProps.() -> Unit) {
    return child(FileUploadButtonBar::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object FileUploadButtonBarStyle : StyleSheet("FileUploadButtonBarStyle", isStatic = true) {
    val buttonBarContainer by css {
        display = Display.inlineFlex
        flexDirection = FlexDirection.row
        alignItems = Align.center
        padding(vertical = 16.px)
    }
    val buttonBarDivider by css {
        width = 16.px
    }
    val spinner by css {
        height = 32.px
        width = 32.px
        margin(horizontal = 32.px, vertical = 8.px)
        alignSelf = Align.center
        +LoadingSpinner.loadingIndicator
    }
}