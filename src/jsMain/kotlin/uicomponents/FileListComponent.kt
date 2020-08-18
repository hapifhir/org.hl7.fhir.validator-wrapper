package uicomponents

import css.FileListStyle
import css.HL7_RED
import kotlinx.css.*
import kotlinx.css.properties.borderBottom
import kotlinx.html.*
import kotlinx.html.attributes.enumEncode
import kotlinx.html.js.*
import react.*
import react.dom.*

import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.asList
import org.w3c.files.File
import org.w3c.files.FileReader
import react.RProps
import react.dom.textArea
import styled.*
import kotlin.browser.document

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
