package uicomponents

import css.FileSummaryStyle
import css.TextStyle
import kotlinx.css.*
import kotlinx.html.js.*
import react.*
import react.RProps
import styled.*

/**
 * We need a way to provide a callback to the main page using this component. This can be done, through React props.
 * In this case, we define the callback for the Submit funtionality here.
 */
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
                styledDiv {
                    css {
                        +FileSummaryStyle.fileContent
                    }
                    +props.fileContent
                }
            }
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
