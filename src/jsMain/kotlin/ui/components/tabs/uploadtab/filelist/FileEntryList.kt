package ui.components.tabs.uploadtab.filelist

import css.tabs.uploadtab.filelist.FileEntryListStyle
import model.ValidationOutcome
import react.*
import styled.css
import styled.styledDiv
import styled.styledUl

external interface FileEntryListProps : RProps {
    var validationOutcomes: List<ValidationOutcome>
    var viewFile: (ValidationOutcome) -> Unit
    var deleteFile: (ValidationOutcome) -> Unit
}

/**
 * Component that displays a list of validation outcomes.
 */
class FileEntryList : RComponent<FileEntryListProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileEntryListStyle.entryListContainer
            }
            styledUl {
                css {
                    +FileEntryListStyle.entryList
                }
                val filesIterator = props.validationOutcomes.iterator()
                while (filesIterator.hasNext()) {
                    fileEntry {
                        validationOutcome = filesIterator.next()
                        onView = {
                            props.viewFile(validationOutcome)
                        }
                        onDelete = {
                            props.deleteFile(validationOutcome)
                        }
                    }
                    if (filesIterator.hasNext()) {
                        styledDiv {
                            css {
                                +FileEntryListStyle.listSeparator
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.fileEntryList(handler: FileEntryListProps.() -> Unit): ReactElement {
    return child(FileEntryList::class) {
        this.attrs(handler)
    }
}
