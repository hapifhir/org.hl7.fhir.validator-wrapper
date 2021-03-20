package ui.components

import css.component.tabs.uploadtab.filelist.FileEntryListStyle
import model.FileInfo
import model.ValidationOutcome
import react.*
import styled.css
import styled.styledDiv
import styled.styledUl

external interface FileListProps : RProps {
    var uploadedFiles: List<ValidationOutcome>
    var removeFile: (FileInfo) -> Unit
}

class FileListComponent : RComponent<FileListProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileEntryListStyle.entryListContainer
            }
            styledUl {
                css {
//                    +FileEntryListStyle.listContainer
                }
                val filesIterator = props.uploadedFiles.iterator()
                while (filesIterator.hasNext()) {
                    fileItemComponent {
                        validationOutcome = filesIterator.next()
                        onDelete = {
                            props.removeFile(it)
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

fun RBuilder.fileListComponent(handler: FileListProps.() -> Unit): ReactElement {
    return child(FileListComponent::class) {
        this.attrs(handler)
    }
}
