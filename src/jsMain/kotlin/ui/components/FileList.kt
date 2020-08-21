package ui.components

import css.FileListStyle
import org.w3c.files.File
import react.*
import styled.css
import styled.styledDiv
import styled.styledUl

external interface FileListProps : RProps {
    var files: MutableList<File>
}

class FileListComponent : RComponent<FileListProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileListStyle.listBackground
            }
            styledUl {
                css {
                    +FileListStyle.listContainer
                }
                val filesIterator = props.files.iterator()
                while (filesIterator.hasNext()) {
                    fileItemComponent {
                        file = filesIterator.next()
                        onDelete = {
                            setState {
                                props.files.remove(it)
                            }
                        }
                    }
                    if (filesIterator.hasNext()) {
                        styledDiv {
                            css {
                                +FileListStyle.listSeparator
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
