package ui.components.tabs.uploadtab.filelist

import Polyglot
import context.LocalizationContext
import css.const.BORDER_GRAY
import kotlinx.css.*
import model.ValidationOutcome
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledUl

external interface FileEntryListProps : Props {
    var validationOutcomes: List<ValidationOutcome>
    var viewFile: (ValidationOutcome) -> Unit
    var deleteFile: (ValidationOutcome) -> Unit
}

/**
 * Component that displays a list of validation outcomes.
 */
class FileEntryList : RComponent<FileEntryListProps, State>() {
    override fun RBuilder.render() {
        LocalizationContext.Consumer { localizationContext ->
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
}

/**
 * React Component Builder
 */
fun RBuilder.fileEntryList(handler: FileEntryListProps.() -> Unit) {
    return child(FileEntryList::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object FileEntryListStyle : StyleSheet("FileEntryListStyle", isStatic = true) {
    val entryListContainer by FileEntryListStyle.css {
        display = Display.flex
        flex = Flex(flexBasis = 100.pct)
        width = 100.pct
        height = 100.pct
        backgroundColor = Color.white
        minHeight = 600.px
        border = Border(width = 1.px, color = BORDER_GRAY, style = BorderStyle.solid)
    }
    val entryList by FileEntryListStyle.css {
        flex = Flex(flexBasis = 100.pct)
        padding = Padding(0.px)
        margin = Margin(0.px)
        listStyleType = ListStyleType.none
    }
    val listSeparator by FileEntryListStyle.css {
        borderBottom = Border(width = 1.px, color = BORDER_GRAY, style = BorderStyle.solid)
    }
}