package ui.components.fileupload.filelist

import css.component.fileupload.filelist.FileEntryStyle
import css.text.TextStyle
import model.ValidationOutcome
import react.*
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

fun RBuilder.fileEntry(handler: FileEntryProps.() -> Unit): ReactElement {
    return child(FileEntry::class) {
        this.attrs(handler)
    }
}
