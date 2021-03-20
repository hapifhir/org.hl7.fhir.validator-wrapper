package ui.components.tabs.uploadtab.filelist

import css.text.TextStyle
import kotlinx.css.*
import model.ValidationOutcome
import react.*
import styled.StyleSheet
import styled.css
import styled.styledLi
import styled.styledP

external interface FileEntryProps : RProps {
    var validationOutcome: ValidationOutcome
    var onView: (ValidationOutcome) -> Unit
    var onDelete: (ValidationOutcome) -> Unit
}

class FileEntryState : RState {}

/**
 * File list entry, detailing the validation status, filename, and giving the user options to either delete or view
 * the file and validation details.
 */
class FileEntry : RComponent<FileEntryProps, FileEntryState>() {
    override fun RBuilder.render() {
        styledLi {
            css {
                +FileEntryStyle.fileEntryContainer
            }
            fileStatusIndicator {
                validationOutcome = props.validationOutcome
            }
            styledP {
                +props.validationOutcome.getFileInfo().fileName
                css {
                    +FileEntryStyle.titleField
                    +TextStyle.fileEntryLabel
                }
            }
            fileEntryOptions {
                viewOption = props.validationOutcome.isValidated()
                onViewClicked = {
                    props.onView(props.validationOutcome)
                }
                onDeleteClicked = {
                    props.onDelete(props.validationOutcome)
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.fileEntry(handler: FileEntryProps.() -> Unit): ReactElement {
    return child(FileEntry::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object FileEntryStyle : StyleSheet("FileEntryStyle") {
    val fileEntryContainer by css {
        display = Display.flex
        height = 96.px
        flex(flexBasis = 100.pct)
        padding(horizontal = 32.px)
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.flexStart
        alignItems = Align.center
    }
    val titleField by css {
        flexGrow = 1.0
        paddingLeft = 16.px
    }
}