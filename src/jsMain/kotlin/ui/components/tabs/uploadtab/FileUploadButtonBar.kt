package ui.components.tabs.uploadtab

import css.const.HL7_RED
import css.const.WHITE
import kotlinx.css.*
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.buttons.genericButton

external interface FileUploadButtonBarProps : RProps {
    var onUploadRequested: () -> Unit
    var onValidateRequested: () -> Unit
}

/**
 * Component displaying the horizontal list of buttons for file upload and validation
 */
class FileUploadButtonBar : RComponent<FileUploadButtonBarProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileUploadButtonBarStyle.buttonBarContainer
            }

            genericButton {
                backgroundColor = WHITE
                borderColor = HL7_RED
                image = "images/upload_red.png"
                label = "Upload"
                onSelected = {
                    props.onUploadRequested()
                }
            }

            styledDiv {
                css {
                    +FileUploadButtonBarStyle.buttonBarDivider
                }
            }

            genericButton {
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
fun RBuilder.fileUploadButtonBar(handler: FileUploadButtonBarProps.() -> Unit): ReactElement {
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
}