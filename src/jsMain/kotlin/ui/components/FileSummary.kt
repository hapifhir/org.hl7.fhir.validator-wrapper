package ui.components

import css.FileSummaryStyle
import css.TextStyle
import kotlinx.css.Display
import kotlinx.css.display
import kotlinx.html.js.onClickFunction
import react.*
import styled.*

external interface FileSummaryProps : RProps {
    var fileName: String
    var fileContent: String
    var active: Boolean
    var onClose: () -> Unit
}

class FileSummaryComponent : RComponent<FileSummaryProps, RState>() {
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
                        +props.fileName
                    }
                    styledImg {
                        css {
                            +FileSummaryStyle.closeButton
                            +FileSummaryStyle.closeButtonHover
                        }
                        attrs {
                            src = "images/close.svg"
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
                styledP {
                    css {
                        +FileSummaryStyle.fileContent
                    }
                    +props.fileContent
                }
            }
        }
    }
}

fun RBuilder.fileSummaryComponent(handler: FileSummaryProps.() -> Unit): ReactElement {
    return child(FileSummaryComponent::class) {
        this.attrs(handler)
    }
}
