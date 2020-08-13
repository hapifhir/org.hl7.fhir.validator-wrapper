package uicomponents

import css.TabBarStyle
import kotlinx.css.Display
import kotlinx.css.display
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
                +TabBarStyle.body
                display = if (props.active) Display.block else Display.none
            }
            fileUploadComponent {  }

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