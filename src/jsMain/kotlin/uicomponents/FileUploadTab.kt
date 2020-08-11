package uicomponents

import css.TabStyle
import kotlinx.css.Display
import kotlinx.css.display
import kotlinx.html.classes
import react.*
import styled.StyleSheet
import styled.*

/**
 * 
 */
external interface FileUploadTabProps: RProps {
    var active: Boolean
}

object FileUploadTabStyle : StyleSheet("FooterColumnStyle", isStatic = true) {
    val tabStyle by css {

    }
}

class FileUploadTab: RComponent<FileUploadTabProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +TabStyle.body
                display = if (props.active) Display.block else Display.none
            }
            styledH3 {
                +"Tab 2"
            }
            styledP {
                +"This is the second tab!"
            }
            attrs {

            }
        }
    }
}

/**
 * We can use lambdas with receivers to make the component easier to work with.
 * To include this component in a layout, someone can simply add:
 *
 *              bottomMenu {
 *
 *              }
 */
fun RBuilder.fileUploadTab(handler: FileUploadTabProps.() -> Unit): ReactElement {
    return child(FileUploadTab::class) {
        this.attrs(handler)
    }
}