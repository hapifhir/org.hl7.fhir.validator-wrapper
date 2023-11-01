package ui.components.tabs.uploadtab

import Polyglot
import css.const.HL7_RED
import css.const.WHITE

import react.*
import styled.StyleSheet

import ui.components.buttons.imageButton


external interface FileUploadButtonProps : Props {
    var onUploadRequested: () -> Unit
    var onValidateRequested: () -> Unit

    var polyglot: Polyglot

}

/**
 * Component displaying the horizontal list of buttons for file upload and validation
 */
class FileUploadButton : RComponent<FileUploadButtonProps, State>() {

    override fun RBuilder.render() {
            imageButton {
                backgroundColor = WHITE
                borderColor = HL7_RED
                image = "images/upload_red.png"
                label = props.polyglot.t("upload_button")
                onSelected = {
                    props.onUploadRequested()
                }
            }
        }
}

/**
 * React Component Builder
 */
fun RBuilder.fileUploadButton(handler: FileUploadButtonProps.() -> Unit) {
    return child(FileUploadButton::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object FileUploadButtonStyle : StyleSheet("FileUploadButtonStyle", isStatic = true) {
   
}