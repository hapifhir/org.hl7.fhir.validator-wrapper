package uicomponents

import css.FileListStyle
import react.*

import org.w3c.files.File
import react.RProps
import styled.*

/**
 * We need a way to provide a callback to the main page using this component. This can be done, through React props.
 * In this case, we define the callback for the Submit funtionality here.
 */
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

/**
 * We can use lambdas with receivers to make the component easier to work with.
 */
fun RBuilder.fileListComponent(handler: FileListProps.() -> Unit): ReactElement {
    return child(FileListComponent::class) {
        this.attrs(handler)
    }
}
