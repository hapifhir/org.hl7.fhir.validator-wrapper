package uicomponents

import css.FileSummaryStyle
import css.TextStyle
import kotlinx.css.*
import kotlinx.html.js.*
import react.*
import org.w3c.files.File
import org.w3c.files.FileReader
import react.RProps
import styled.*

/**
 * We need a way to provide a callback to the main page using this component. This can be done, through React props.
 * In this case, we define the callback for the Submit funtionality here.
 */
external interface FileSummaryProps : RProps {
    var file: File
    var active: Boolean
    var onClose: () -> Unit
}

class FileSummaryState : RState {
    var fileContent: String = ""
}

class FileSummaryComponent : RComponent<FileSummaryProps, FileSummaryState>() {

    init {
        state = FileSummaryState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileSummaryStyle.overlay
                display = if (props.active) Display.flex else Display.none
            }
            styledDiv {
                css {
                    +FileSummaryStyle.modalContent
                }
                styledDiv {
                    css {
                        +FileSummaryStyle.titleBar
                    }
                    styledP {
                        css {
                            +TextStyle.h2
                            +FileSummaryStyle.filename
                        }
                        +props.file?.name
                    }
                    styledImg {
                        css {
                            +FileSummaryStyle.closeButton
                            +FileSummaryStyle.closeButtonHover
                        }
                        attrs {
                            src = "highlight_off-24px.svg"
                            onClickFunction = {
                                props.onClose()
                            }
                        }
                    }
                }
                styledHr {
                    css {
                        +FileSummaryStyle.horizontalRule
                    }
                }
                styledDiv {
                    css {
                        +FileSummaryStyle.fileContent
                    }
                    readFile(props.file)
                    +state.fileContent
                }
            }
        }
    }

    fun readFile(file: File?) {
        val reader = FileReader()
        if (file != null) {
            println("reading file ${file.name}")
            reader.readAsText(file)
            reader.onloadend = {
                setState {
                    fileContent = reader.result
                }
            }
        } else {
            println("null file")
        }
    }
}

/**
 * We can use lambdas with receivers to make the component easier to work with.
 */
fun RBuilder.fileSummaryComponent(handler: FileSummaryProps.() -> Unit): ReactElement {
    return child(FileSummaryComponent::class) {
        this.attrs(handler)
    }
}
