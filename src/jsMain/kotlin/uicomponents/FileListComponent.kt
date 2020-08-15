package uicomponents

import css.FileListStyle
import kotlinx.css.Display
import kotlinx.css.display
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
    var files: List<File>
}

class FileListComponent : RComponent<FileListProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileListStyle.listBackground
            }
            styledUl {
                css {

                }
                for (f in props.files) {
                    fileItemComponent {
                        file = f
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
